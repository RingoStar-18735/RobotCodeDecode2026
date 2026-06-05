package org.firstinspires.ftc.teamcode.Subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;

import org.firstinspires.ftc.teamcode.RobotMap;

import java.util.function.Supplier;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPositions;
import dev.nextftc.hardware.powerable.SetPower;


@Configurable
public class ShooterSubsystem implements Subsystem {
    public final static ShooterSubsystem INSTANCE = new ShooterSubsystem();

    public boolean ReverseWheel = false;
    public double SpinUpStartTime = 0;
    public double Distance = 0;
    public Double ServoPos = 0.0;
    public double ShooterSpeed = 0;
    public boolean ShooterStopped = false;
    private boolean CommandStarted = true;
    private TelemetryManager panels = PanelsTelemetry.INSTANCE.getTelemetry();
    private TelemetryManager telemetryManager;


    public ShooterSubsystem() {
    }

    @Override
    public void initialize() {
        if(Shooter.getDirection() != 1)
            Shooter.reverse();
        telemetryManager = PanelsTelemetry.INSTANCE.getTelemetry();
        telemetryManager.update(ActiveOpMode.telemetry());
        PIDF = ControlSystem.builder()
                .velPid(RobotMap.SHOOTER_P, RobotMap.SHOOTER_I, RobotMap.SHOOTER_D)
                .basicFF(RobotMap.F_Active, RobotMap.F_Passive, 0.0)
                .build();

        PIDF.setGoal(new KineticState(0.0, 0.0));

    }

    public final MotorEx Shooter = new MotorEx("0C");
//    public final PIDController PID = new PIDController(RobotMap.SHOOTER_P, RobotMap.SHOOTER_I, RobotMap.SHOOTER_D);
    public final ServoEx left_aim = new ServoEx("00E");
    private ControlSystem PIDF;
    public final ServoEx right_aim = new ServoEx("01E");



   // public Command RunVelocity(double v){
 //       return new RunToVelocity(PID, v).requires(this);
  //  }


    public Command RunVelocity(double v){
        return new InstantCommand(
                ()->  PIDF.setGoal(new KineticState(0.0, v))
        );
    }

    public Command ServoMoveByField(Supplier<Double> distanceSupplier) {
        return new LambdaCommand()
                .setUpdate(() -> {
                    Distance = distanceSupplier.get();

                    if (Distance < 51) {
                        ServoPos = RobotMap.SERVO_MOVE_CLOSE;
                    } else if (Distance > 115) {
                        ServoPos = RobotMap.SERVO_MOVE_FAR;
                    } else {
                        ServoPos = RobotMap.SERVO_MOVE_MID;
                    }

                    left_aim.setPosition(ServoPos);
                    right_aim.setPosition(1 - ServoPos);
                })
                .setIsDone(() -> false); // מריץ עד שמבוטל ידנית
    }


    public Command RunFullSpeed(Supplier<Double> distanceSupplier) {
        return new LambdaCommand()
                .setStart(() -> {
                    Distance = distanceSupplier.get();
                    if (Distance < 51) {
                        ShooterSpeed = RobotMap.SHOOTER_SPEED_CLOSE;
                    } else if (Distance > 115) {
                        ShooterSpeed = RobotMap.SHOOTER_SPEED_FAR;
                    }else {
                        ShooterSpeed = RobotMap.SHOOTER_SPEED_MID;
                    }
                    PIDF.setGoal(new KineticState(0.0, ShooterSpeed));
                    SpinUpStartTime = ActiveOpMode.getRuntime();
                    CommandStarted = true;
                    ShooterStopped = false;
                })
                .setUpdate(() -> {

                })
                .setStop(interrupted -> {
                    CommandStarted = false;

                })
                .setIsDone(()-> Math.abs(ShooterSpeed + getShooterVelocity()) < RobotMap.SHOOTER_SPEED_RANGE) // Returns if the command has finished
                .requires(this)
                .setInterruptible(true)
                .named("RunFullSpeed"); // sets the name of the command; optional
    }


    public Command StopSpeed () {
        return new InstantCommand(()-> ShooterStopped=true);
    }

    public Command move(double pow){
        return new SetPower(Shooter, pow);
    }

    public Command ServoAimCommand(double pos) {            
        return new SetPositions(left_aim.to(1 - pos), right_aim.to(pos));
    }

    public void ServoAim(double pos) {
        left_aim.setPosition(1 - pos);
        right_aim.setPosition(pos);
    }

//    public Command ServoAutoAim(Pose RobotPose, Pose TargetPose) {
//        return new LambdaCommand()
//                .setUpdate(() -> {
//                    distance = Math.sqrt(Math.pow(TargetPose.getX() - RobotPose.getX(), 2) + Math.pow(TargetPose.getY() - RobotPose.getY(), 2));
//                    if (distance < 51) {
//                        ShooterSubsystem.INSTANCE.ServoAim(RobotMap.SERVO_MOVE_CLOSE);
//                    } else if (distance > 120) {
//                        ShooterSubsystem.INSTANCE.ServoAim(RobotMap.SERVO_MOVE_FAR);
//                    }else {
//                        ShooterSubsystem.INSTANCE.ServoAim(RobotMap.SERVO_MOVE_MID);
//                    }
//                })
//                .setIsDone(()-> false);
//    }


    public double getShooterVelocity(){
        return Shooter.getVelocity();
    }


    @Override
    public void periodic() {
        double PIDPower = Math.abs(PIDF.calculate(new KineticState(Shooter.getCurrentPosition(), Shooter.getVelocity())));

//        ActiveOpMode.telemetry().addData("Started: ", false);
//        ActiveOpMode.telemetry().addData("Started: ", false);
//
        telemetryManager.debug(getShooterVelocity());
//        telemetryManager.debug(PID.getTarget());

        telemetryManager.addData("Angle" , getShooterVelocity());
//        telemetryManager.addData("Target" , PID.getTarget());
        telemetryManager.addData("PIDPower" , PIDPower);


//        ActiveOpMode.telemetry().addData("target(-): ", PID.getTarget());
        ActiveOpMode.telemetry().addData("target(+): ", ShooterSpeed);
        ActiveOpMode.telemetry().addData("PIDpower: ", PIDPower); //
        ActiveOpMode.telemetry().addData("ShooterDistance: ", Distance);
        CommandStarted = Math.abs(ShooterSpeed + getShooterVelocity()) < RobotMap.SHOOTER_SPEED_RANGE;

//        ActiveOpMode.telemetry().addData("ShooterStatus: ", CommandStarted);
//        ActiveOpMode.telemetry().addData("CommandsRunning: ", CommandManager.INSTANCE.snapshot());
        ActiveOpMode.telemetry().addData("ShooterSpeed: ", getShooterVelocity());
//        ActiveOpMode.telemetry().addData("ShooterTarget: ", RobotMap.SHOOTER_SPEED);
//        ActiveOpMode.telemetry().addData("ShooterStopped: ", ShooterStopped);
//        ActiveOpMode.telemetry().addData("ShooterCalculate: ", PID.calculateOutput(-Shooter.getVelocity(), ActiveOpMode.getRuntime()));

        ActiveOpMode.telemetry().addData("Shooter Velocity: ", -Shooter.getState().getVelocity());
//        ActiveOpMode.telemetry().addData("Shooter Target: ", PID.getTarget());



//        double proportional = SHOOTER_P * (PID.getTarget() - Shooter.getVelocity());



        if (ReverseWheel){
            Shooter.setPower(0.2);
        } else if (ShooterStopped){
            Shooter.setPower(0.2);
        }
         else {
            Shooter.setPower(PIDPower);
        }
        panels.update();
    }
}
