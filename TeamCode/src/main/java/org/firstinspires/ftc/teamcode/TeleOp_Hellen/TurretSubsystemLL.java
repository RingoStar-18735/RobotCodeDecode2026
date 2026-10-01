package org.firstinspires.ftc.teamcode.TeleOp_Hellen;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.RobotMap;
import org.firstinspires.ftc.teamcode.pedroPathing.PIDController;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.impl.MotorEx;


public class TurretSubsystemLL implements Subsystem {

    public final static TurretSubsystemLL INSTANCE = new TurretSubsystemLL();
    private final TelemetryManager panels = PanelsTelemetry.INSTANCE.getTelemetry();

    private TelemetryManager telemetryManager;

    double offset = 0;

    public MotorEx turretLL = new MotorEx("1C");


    private PIDController PID;

    @Override
    public void initialize() {
        PID = new PIDController(RobotMap.TURRET_P , RobotMap.TURRET_I, RobotMap.TURRET_D);
        telemetryManager = PanelsTelemetry.INSTANCE.getTelemetry();
//        telemetryManager.update(ActiveOpMode.telemetry());
        turretLL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        telemetryManager = PanelsTelemetry.INSTANCE.getTelemetry();
        telemetryManager.update(ActiveOpMode.telemetry());
    }

    public double getAngle() {
        return (turretLL.getCurrentPosition() * RobotMap.TURRET_GEAR_RATIO) - offset;
    }

    @Override
    public void periodic() {
        double PIDPower = PID.calculateOutput(getAngle(), ActiveOpMode.getRuntime());

        telemetryManager.debug(getAngle());
        telemetryManager.debug(PID.getTarget());
        telemetryManager.addData("Target Angle LL: ", PID.getTarget());
        telemetryManager.addData("Current Angle LL: ", getAngle());
        telemetryManager.update();
        if(!ActiveOpMode.opModeInInit()){
            turretLL.setPower(PIDPower);
        }

//        telemetryManager.update(ActiveOpMode.telemetry());
    }
    public double getTarget(){
        return PID.getTarget();
    }

    public void ResetEncoder() {
        offset = (turretLL.getCurrentPosition() * RobotMap.TURRET_GEAR_RATIO );
    }

    public double getOffset() {
        return offset;
    }

    public void setOffset(double offset) {
        this.offset = offset;
    }

    public Command FollowPointLL() {
        double tx = LLSubsystem.INSTANCE.getTx();
        telemetryManager.addData("tx", tx);
        telemetryManager.update();
        return new InstantCommand(()-> PID.setTarget(tx));
    }

    public void moveToSetDegree(double deg) {
         PID.setTarget(deg);
    }

    public void moveDegrees(double deg){

        moveToSetDegree(getAngle() + deg);
    }
}
