package org.firstinspires.ftc.teamcode.Subsystems;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.teamcode.RobotMap;
import org.firstinspires.ftc.teamcode.pedroPathing.PIDController;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.impl.MotorEx;

public class TurretSubsystem implements Subsystem {

    public final static TurretSubsystem INSTANCE = new TurretSubsystem();

    public boolean isReset = false;
    boolean toFollow = true;
    double offset = 0;


    private DigitalChannel magnet;
    private MotorEx turretmotor = new MotorEx ("2E");
    private PIDController PID;

    @Override
    public void initialize() {
        toFollow = true;
        isReset = false;
        PID = new PIDController(RobotMap.TURRET_P , RobotMap.TURRET_I, RobotMap.TURRET_D);
        magnet = ActiveOpMode.hardwareMap().get(DigitalChannel.class , "magnet");
        magnet.setMode(DigitalChannel.Mode.INPUT);


        turretmotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }



    public boolean isMagnetPressed(){
        return magnet.getState();
    }

    public Command ResetAngleRight(){
        return new LambdaCommand()
                .setStart(() -> {
                    turretmotor.setPower(-RobotMap.RESET_TURRET_POWER);
                })
                .setUpdate(() -> {
                    turretmotor.setPower(-RobotMap.RESET_TURRET_POWER);
                })
                .setStop(interrupted -> {
                    turretmotor.setPower(0.0);
                    ResetEncoder();
                    isReset = true;
                })
                .setIsDone(()-> !isMagnetPressed()) // Returns if the command has finished
                .requires(this)
                .setInterruptible(true)
                .named("ResetTurret"); // sets the name of the command; optional
    }


    public void ResetAngleLeft(){
        isReset = false;
        turretmotor.setPower(0.3);
        if (isMagnetPressed()) {
            turretmotor.setPower(0);
            ResetEncoder();
            isReset = true;
        }
    }


    public void setReset(boolean reset) {
        isReset = reset;
    }

    public void ResetEncoder(){
        offset = getAngle();
    }


    //    public Command LimelightMove(){
//        return ;
//    }
    public double getAngle(){
        return (turretmotor.getCurrentPosition() * RobotMap.TURRET_GEAR_RATIO ) - offset;
    }
    @Override
    public void periodic() {
//        ActiveOpMode.telemetry().addData("target area:" , getTa());
//        ActiveOpMode.telemetry().addData("target X:" , getTX());
//        ActiveOpMode.telemetry().addData("target Y:" , getTY());



        ActiveOpMode.telemetry().addData("magnet state:" , !magnet.getState());
        ActiveOpMode.telemetry().addData("turret position:" , getAngle());
//        ActiveOpMode.telemetry().addData("Turret angle:" , getAngle());
        ActiveOpMode.telemetry().addData("Turret Target:" , PID.getTarget());
        ActiveOpMode.telemetry().addData("Is Reset:" , isReset);
        magnet.setMode(DigitalChannel.Mode.INPUT);



        double PIDPower = -PID.calculateOutput(getAngle(), ActiveOpMode.getRuntime());
        ActiveOpMode.telemetry().update();
//
        ActiveOpMode.telemetry().addData("MotorPow:" , turretmotor.getPower());
        ActiveOpMode.telemetry().addData("Offset:" , offset);

        if(isReset){
            turretmotor.setPower(PIDPower);
        }
    }

    public double getOffset() {
        return offset;
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

    public Command FollowPoint(Pose targetpose, Pose pose) {
        // Robot pose in field coordinates
        if(!toFollow) return new InstantCommand(() -> PID.setTarget(180));

        final double rx = pose.getX();
        final double ry = pose.getY();
        double heading = pose.getHeading();// Pedro heading is radians

        ActiveOpMode.telemetry().addData("headingBefore" , Math.toDegrees(heading));
        if (Math.toDegrees(heading) < -180) {
           heading = heading + Math.toRadians(360);
        }
        ActiveOpMode.telemetry().addData("headingAfter" , Math.toDegrees(heading));

//        else if (Math.toDegrees(heading) > 180) {
//            heading = heading - 360;
//        }
        final double finalHeading = heading;

        // Vector from robot to target in field coordinates
        final double dx = targetpose.getX() - rx;
        final double dy = targetpose.getY() - ry;

        // World angle from robot to target
        final double alpha = Math.atan2(dy, dx); // [-pi, pi]

        // Turret angle relative to robot forward:
        // gamma = (world angle to target) - (robot world heading)
        double gamma = finalHeading - alpha; //

        // Wrap to [-pi, pi]
        gamma = Math.atan2(Math.sin(gamma), Math.cos(gamma));

        // Convert to degrees
        double gammaDeg = Math.toDegrees(gamma);

        // Clamp to turret mechanical limits
        gammaDeg = Math.max(RobotMap.MIN_TURRET_ANGLE, Math.min(RobotMap.MAX_TURRET_ANGLE, gammaDeg));

        // Telemetry to verify
        ActiveOpMode.telemetry().addData("Turret rx,ry", "%.2f, %.2f", rx, ry);
        ActiveOpMode.telemetry().addData("Turret tx,ty", "%.2f, %.2f", targetpose.getX(), targetpose.getY());
        ActiveOpMode.telemetry().addData("Turret alpha(deg)", Math.toDegrees(alpha));
        ActiveOpMode.telemetry().addData("Turret heading(deg)", Math.toDegrees(heading));
        ActiveOpMode.telemetry().addData("Turret gamma(deg)", gammaDeg);

//        gammaDeg -= 90;
        final double finalGammaDeg = gammaDeg;
        double deg = finalGammaDeg;

        if (RobotMap.TURRET_ROBOT_DIFRANCE){
            deg = finalGammaDeg - 90;
        }
        final double finaldeg = deg;

            return new InstantCommand(() -> PID.setTarget(finalGammaDeg));

    }

//    public Command TurretAngle(double ang) {
//        return
//    }

    public void setToFollow(boolean toFollow) {
        this.toFollow = toFollow;
    }

    public double AngleConverter(double ang){
        double midAngle =(( RobotMap.MAX_TURRET_ANGLE + RobotMap.MIN_TURRET_ANGLE) /2) - 360;
        double angle =  ang - midAngle;
        return  Math.max(RobotMap.MIN_TURRET_ANGLE, Math.min(RobotMap.MAX_TURRET_ANGLE, angle));
    }

    public void setOffset(double offset) {
        this.offset = offset;
    }

    public Command MoveAngle(double ang){
        return MoveToAngle(getAngle() + ang);
    }




}
