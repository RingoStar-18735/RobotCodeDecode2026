package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
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

    public LLResult CurrentResult;
    public boolean isReset = false;


    private Limelight3A limelight;
    private DigitalChannel magnet;
    private MotorEx turretmotor = new MotorEx ("2E");
    private PIDController PID;



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
                    turretmotor.setCurrentPosition(0.0);
                    isReset = true;
                })
                .setIsDone(()-> !isMagnetPressed()) // Returns if the command has finished
                .requires(this)
                .setInterruptible(true)
                .named("ResetTurret"); // sets the name of the command; optional
    }


    public Command LimelightMove(){
        return MoveAngle(getTX());
    }
    public double getAngle(){
        return (turretmotor.getCurrentPosition() * RobotMap.TURRET_GEAR_RATIO * 360);
    }
    @Override
    public void periodic() {
        CurrentResult = limelight.getLatestResult();
//        ActiveOpMode.telemetry().addData("target area:" , getTa());
//        ActiveOpMode.telemetry().addData("target X:" , getTX());
//        ActiveOpMode.telemetry().addData("target Y:" , getTY());
//        ActiveOpMode.telemetry().addData("PTurret:" , PID.getKp());
//        ActiveOpMode.telemetry().addData("ITurret:" , PID.getKi());
//        ActiveOpMode.telemetry().addData("DTurret:" , PID.getKd());
//
//        ActiveOpMode.telemetry().addData("magnet state:" , !magnet.getState());
//        ActiveOpMode.telemetry().addData("turret position:" , turretmotor.getCurrentPosition());
//        ActiveOpMode.telemetry().addData("Turret angle:" , getAngle());
//        ActiveOpMode.telemetry().addData("Turret Target:" , PID.getTarget());
//        ActiveOpMode.telemetry().addData("Is Reset:" , isReset);
        magnet.setMode(DigitalChannel.Mode.INPUT);

        double PIDPower = -PID.calculateOutput(getAngle(), ActiveOpMode.getRuntime());
        ActiveOpMode.telemetry().update();
//
//        ActiveOpMode.telemetry().addData("PIDPower:" , PIDPower);
//        ActiveOpMode.telemetry().addData("PIDPower:" , PIDPower);

        if(isReset){
            turretmotor.setPower(PIDPower);
        }
    }
    public Command MoveToAngle(double ang){
        return new InstantCommand(
                ()-> PID.setTarget(Math.max(RobotMap.MIN_TURRET_ANGLE, Math.min(RobotMap.MAX_TURRET_ANGLE, ang)))
        );
    }

    public Command MoveAngle(double ang){
        return MoveToAngle(getAngle() + ang);
    }
    public double getTX(){
        if (CurrentResult != null && CurrentResult.isValid()){
            double tx =CurrentResult.getTx();
            return tx;
        }
        else return 0.0;
    }

    @Override
    public void initialize() {
        isReset = false;

        PID = new PIDController(RobotMap.TURRET_P , RobotMap.TURRET_I, RobotMap.TURRET_D);
        magnet = ActiveOpMode.hardwareMap().get(DigitalChannel.class , "magnet");
        magnet.setMode(DigitalChannel.Mode.INPUT);
        limelight = ActiveOpMode.hardwareMap().get(Limelight3A.class, "limelightapril");
        limelight.setPollRateHz(RobotMap.LIMELIGHT_HZ);
        limelight.start();
        CurrentResult = limelight.getLatestResult();

        turretmotor.setCurrentPosition(0.0);

        turretmotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setPipeline(int num){
        limelight.pipelineSwitch(num);
    }

    public double getTa() {
        if (CurrentResult != null && CurrentResult.isValid()) {
            double ta = CurrentResult.getTa();
            return ta;
        }
        else return -1;
    }

    public double getTY(){
        if (CurrentResult != null && CurrentResult.isValid()){
            double ty =CurrentResult.getTy();
            return ty;
        }
        else return 360;
    }



}
