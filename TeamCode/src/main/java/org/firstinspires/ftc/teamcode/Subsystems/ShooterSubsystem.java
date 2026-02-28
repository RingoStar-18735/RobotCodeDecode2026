package org.firstinspires.ftc.teamcode.Subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;

import org.firstinspires.ftc.teamcode.RobotMap;
import org.firstinspires.ftc.teamcode.pedroPathing.PIDController;

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
    public boolean ShooterStopped = false;
    private boolean CommandStarted = true;
    private TelemetryManager panels = PanelsTelemetry.INSTANCE.getTelemetry();




    public ShooterSubsystem() {
    }

    @Override
    public void initialize() {
        if(Shooter.getDirection() != 1)
            Shooter.reverse();
    }

    public final MotorEx Shooter = new MotorEx("3E");
    public final PIDController PID = new PIDController(RobotMap.SHOOTER_P, RobotMap.SHOOTER_I, RobotMap.SHOOTER_D);
    public final ServoEx left_aim = new ServoEx("00E");
    public final ServoEx right_aim = new ServoEx("03E");



   // public Command RunVelocity(double v){
 //       return new RunToVelocity(PID, v).requires(this);
  //  }

    public Command RunVelocity(double v){
        return new InstantCommand(
                ()->  PID.setTarget(v)
        );
    }


    public Command RunFullSpeed () {
        return new LambdaCommand()
                .setStart(() -> {
                    PID.setTarget(-RobotMap.SHOOTER_SPEED);
                    CommandStarted = true;
                    ShooterStopped = false;
                })
                .setUpdate(() -> {

                })
                .setStop(interrupted -> {
                    CommandStarted = false;

                })
                .setIsDone(()-> Math.abs(RobotMap.SHOOTER_SPEED + getShooterVelocity()) < RobotMap.SHOOTER_SPEED_RANGE) // Returns if the command has finished
                .requires(this)
                .setInterruptible(true)
                .named("RunFullSpeed"); // sets the name of the command; optional
    }


    public Command StopSpeed () {
        return new LambdaCommand()
                .setStart(() -> {
                    ShooterStopped = true;
                })
                .setUpdate(() -> {

                })
                .setStop(interrupted -> {
                })
                .setIsDone(()-> CommandStarted) // Returns if the command has finished
                .requires(this)
                .setInterruptible(true)
                .named("RunFullSpeed"); // sets the name of the command; optional
    }

    public Command move(double pow){
        return new SetPower(Shooter, pow);
    }

    public Command ServoAim(double pos) {
        return new SetPositions(left_aim.to(1 - pos), right_aim.to(pos));
    }


    public double getShooterVelocity(){
        return Shooter.getVelocity();
    }


    @Override
    public void periodic() {



//        ActiveOpMode.telemetry().addData("Started: ", false);
//        ActiveOpMode.telemetry().addData("Started: ", false);
//
        panels.addData("Robot Velocity: ", Shooter.getState().getVelocity());
        panels.addData("Robot Target: ", PID.getTarget());
        panels.addData("SHootActive: ",CommandStarted);
        CommandStarted = Math.abs(RobotMap.SHOOTER_SPEED + getShooterVelocity()) < RobotMap.SHOOTER_SPEED_RANGE;

//        ActiveOpMode.telemetry().addData("ShooterStatus: ", CommandStarted);
//        ActiveOpMode.telemetry().addData("CommandsRunning: ", CommandManager.INSTANCE.snapshot());
//        ActiveOpMode.telemetry().addData("ShooterSpeed: ", getShooterVelocity());
//        ActiveOpMode.telemetry().addData("ShooterTarget: ", RobotMap.SHOOTER_SPEED);
//        ActiveOpMode.telemetry().addData("ShooterStopped: ", ShooterStopped);
//        ActiveOpMode.telemetry().addData("ShooterCalculate: ", PID.calculateOutput(-Shooter.getVelocity(), ActiveOpMode.getRuntime()));
//
//        ActiveOpMode.telemetry().addData("Shooter Velocity: ", -Shooter.getState().getVelocity());
//        ActiveOpMode.telemetry().addData("Shooter Target: ", PID.getTarget());
        double PIDPower = PID.calculateOutput(-Shooter.getVelocity(), ActiveOpMode.getRuntime());



//        double proportional = SHOOTER_P * (PID.getTarget() - Shooter.getVelocity());


        if (ReverseWheel){
            Shooter.setPower(-0.2);
        } else if (ShooterStopped){
            Shooter.setPower(0);
        } else {
            Shooter.setPower(PIDPower);
        }


        panels.update();

    }
}
