package org.firstinspires.ftc.teamcode.Subsystems;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.teamcode.SequentialGroupFixed;
import org.firstinspires.ftc.teamcode.RobotMap;
import org.firstinspires.ftc.teamcode.pedroPathing.PIDController;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.impl.MotorEx;

public class TurretSubsystem implements Subsystem {

    public final static TurretSubsystem INSTANCE = new TurretSubsystem();

    double Test2;
    double Test1;
    private boolean matchStarted = false;
    private Pose newStartingPosition = new Pose();
    public boolean isReset = false;
    double offset = 0;
    private DigitalChannel magnet;
    private MotorEx turretmotor = new MotorEx ("2E");
    private PIDController PID;

    public void startMatch() {
        matchStarted = true;
    }

    public Pose getNewStartingPosition() {
        return newStartingPosition;
    }

    @Override
    public void initialize() {
        isReset = false;
        PID = new PIDController(RobotMap.TURRET_P , RobotMap.TURRET_I, RobotMap.TURRET_D);
        magnet = ActiveOpMode.hardwareMap().get(DigitalChannel.class , "magnet");
        magnet.setMode(DigitalChannel.Mode.INPUT);
    }

    public boolean isMagnetPressed() {
        return magnet.getState();
    }

    public Command ResetAngle(){
        return new LambdaCommand()
                .setStart(() -> {
                    turretmotor.setPower(-RobotMap.RESET_TURRET_POWER);
                })
                .setUpdate(() -> {
                    turretmotor.setPower(-RobotMap.RESET_TURRET_POWER);
                })
                .setStop(interrupted -> {
                    turretmotor.setPower(0.0);
                    sleep(1000);
                    ResetEncoder();
                    isReset = true;
                })
                .setIsDone(()-> !isMagnetPressed()) // Returns if the command has finished
                .requires(this)
                .setInterruptible(true)
                .named("ResetTurret"); // sets the name of the command; optional
    }

     public double ResetEncoder(){
        offset = getAngle();
        return offset;
     }

    public double getAngle() {
        return (turretmotor.getCurrentPosition() * RobotMap.TURRET_GEAR_RATIO ) - offset;
    }

    @Override
    public void periodic() {
        ActiveOpMode.telemetry().addData("Turret Angle:" , getAngle());
        ActiveOpMode.telemetry().addData("Turret Target:" , PID.getTarget());
        ActiveOpMode.telemetry().addData("Is Reset:" , isReset);

        double PIDPower = -PID.calculateOutput(getAngle(), ActiveOpMode.getRuntime());

        ActiveOpMode.telemetry().addData("Offset:" , offset);



        ActiveOpMode.telemetry().update();

        if(isReset) {
            turretmotor.setPower(PIDPower);
        }
    }

    public Command MoveToAngle(double Normalang){
        ActiveOpMode.telemetry().addData("Normalang" , Normalang);
        ActiveOpMode.telemetry().addData("NormalangConverted" , AngleConverter(Normalang));
        return new InstantCommand(
                ()-> PID.setTarget(-15));
    }

    public Command MoveToSetAngle(double ang){
        return new InstantCommand(
                ()-> PID.setTarget(ang));
    }

    public Command FollowPoint(Pose targetpose, Follower follower){
        // max -> 5*PI / 4
        //min -> -PI / 2
        double addedAngleMinMax = Math.PI / 8;
        // double dist = Math.sqrt(Math.pow(targetpose.getX() - follower.getPose().getX(), 2) + Math.pow(targetpose.getY() - follower.getPose().getY(), 2));
        // we assume that the robot starts at 90 degrees, relative to positive x (pedro coordinate system, https://pedropathing.com/docs/fieldcoordinates-dark.png)
        double beta = follower.getHeading(); // robot angle in relation to field [-PI, PI] relative to positive x (pedro coordinate system, https://pedropathing.com/docs/fieldcoordinates-dark.png)
        double alpha = Math.atan2(
                targetpose.getY() - follower.getPose().getY(),
                targetpose.getX() - follower.getPose().getX()
        ); // robot angle in relation to target (based on position) [-PI, PI] relative to positive x (pedro coordinate system, https://pedropathing.com/docs/fieldcoordinates-dark.png)

        if (alpha < 0) {
            alpha += 2 * Math.PI;
        } // Converts alpha to [0, 2PI]

        if (beta < 0) {
            beta += 2 * Math.PI;
            beta -= 3 * Math.PI / 4;
            beta %= 2 * Math.PI;
        } // Converts beta to [0, 2PI]

        double gamma = beta - alpha; // robot angle in relation to target [-2PI, 2PI]

        if (gamma < -Math.PI / 2) {
            gamma += 2 * Math.PI;

            if (gamma > (5 * Math.PI) / 4) {
                gamma =  0; // impossible angle, return to 0
            }
        } else if (gamma > (5 * Math.PI) / 4) {
            gamma -= 2 * Math.PI;

            if (gamma < -Math.PI / 2) {
                gamma = 0; // impossible angle, return to 0
            }
        } // Converts gamma to [-PI / 2 , (5 * PI) / 4]

        double finalGamma = gamma;
        return new InstantCommand(
                () -> PID.setTarget(Math.toDegrees(finalGamma)));
    }

    public Command LimelightScan() {
        return new SequentialGroupFixed(
                ResetAngle(),
                new LambdaCommand()
                        .setStart(() -> {
                            turretmotor.setPower(-RobotMap.RESET_TURRET_POWER);
                        })
                        .setUpdate(() -> {
                            if (getAngle() >= RobotMap.MAX_TURRET_ANGLE) {
                                turretmotor.setPower(RobotMap.RESET_TURRET_POWER);
                            }
                        })
                        .setStop(interrupted -> {
                            turretmotor.setPower(0);
                        })
                        .setIsDone(() -> LimelightApril.INSTANCE.getIdList().contains(20) || LimelightApril.INSTANCE.getIdList().contains(24)) // Returns if the command has finished
                                .requires(this, LimelightApril.INSTANCE)
                                .setInterruptible(true)
                                .named("LimelightScan"), // sets the name of the command; optional
                new Delay(5),
                new InstantCommand(() -> {
                    Pose LimelightPose = LimelightApril.INSTANCE.getPoseLimelight();

                    Pose newPose = new Pose(
                            LimelightPose.getX(),
                            LimelightPose.getY(),
                            ((LimelightApril.INSTANCE.getPoseLimelight().getHeading() - (Math.PI / 2)) % (2 * Math.PI) + (2 * Math.PI)) % (2 * Math.PI) + Math.toRadians(getAngle()));
                    newStartingPosition = newPose;

                    Test1 = Math.toDegrees(((LimelightApril.INSTANCE.getPoseLimelight().getHeading() - (Math.PI / 2)) % (2 * Math.PI) + (2 * Math.PI)) % (2 * Math.PI));
                    Test2 = getAngle();
                    isReset = true;
                })
        );
    }


    public double AngleConverter(double ang){
        double midAngle =(( RobotMap.MAX_TURRET_ANGLE + RobotMap.MIN_TURRET_ANGLE) /2) - 360;
        double angle =  ang - midAngle;
        return  Math.max(RobotMap.MIN_TURRET_ANGLE, Math.min(RobotMap.MAX_TURRET_ANGLE, angle));
    }




    public Command MoveAngle(double ang){
        return MoveToAngle(getAngle() + ang);
    }

    public final void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
