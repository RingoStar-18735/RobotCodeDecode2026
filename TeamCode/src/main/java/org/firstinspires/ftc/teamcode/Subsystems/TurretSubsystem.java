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

    double offset = 0;


    private DigitalChannel magnet;
    private MotorEx turretmotor = new MotorEx ("2E");
    private PIDController PID;
    private Pose RobotPose;

    @Override
    public void initialize() {
        isReset = false;
        RobotPose = new Pose(0,56,90);
        PID = new PIDController(RobotMap.TURRET_P , RobotMap.TURRET_I, RobotMap.TURRET_D);
        magnet = ActiveOpMode.hardwareMap().get(DigitalChannel.class , "magnet");
        magnet.setMode(DigitalChannel.Mode.INPUT);


        turretmotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }


    public TurretSubsystem(){}


    public boolean isMagnetPressed(){
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
                    isReset = true;
                    ResetEncoder();
                })
                .setIsDone(()-> !isMagnetPressed()) // Returns if the command has finished
                .requires(this)
                .setInterruptible(true)
                .named("ResetTurret"); // sets the name of the command; optional
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

   //     if(!isMagnetPressed()) ResetEncoder();

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

//        if(isReset){
//            turretmotor.setPower(PIDPower);
//        }
    }
    public Command MoveToAngle(double ang){
        return new InstantCommand(
                ()-> PID.setTarget(Math.max(RobotMap.MIN_TURRET_ANGLE, Math.min(RobotMap.MAX_TURRET_ANGLE, ang)))
        );
    }

    public void setRobotPose(Pose pos){RobotPose = pos;}


    public Command MoveAngle(double ang){
        return MoveToAngle(getAngle() + ang);
    }




}
