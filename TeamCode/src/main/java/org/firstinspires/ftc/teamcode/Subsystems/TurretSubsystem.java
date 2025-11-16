package org.firstinspires.ftc.teamcode.Subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.controllable.RunToPosition;
import dev.nextftc.hardware.impl.MotorEx;
@Configurable

public class TurretSubsystem implements Subsystem {
    public final static TurretSubsystem INSTANCE = new TurretSubsystem();
    public TurretSubsystem() {}
    public double RelativeAngle = 0;
    public double RealAngle = 0;
    private final MotorEx TurretMotor = new MotorEx("turretMotor");

    private final ControlSystem controlSystem = ControlSystem.builder()
            .posPid(0.005, 0, 0)
            .armFF(0)
            .build();

    @Override
    public void periodic() {
        double TURRET_GEAR_RATIO = 50.9;
        RealAngle = (TurretMotor.getCurrentPosition() / TURRET_GEAR_RATIO *360  / 27.82)%360 ;
        RelativeAngle = ((TurretMotor.getCurrentPosition() / TURRET_GEAR_RATIO ) / 27.82)*360;

        ActiveOpMode.telemetry().addData("Real Angle: ", RealAngle);
        ActiveOpMode.telemetry().addData("Relative Angle: ", RelativeAngle);
        ActiveOpMode.telemetry().addData("Target Angle: ", controlSystem.getGoal());
        ActiveOpMode.telemetry().update();

        TurretMotor.setPower(controlSystem.calculate(TurretMotor.getState()));
    }

    @Override
    public void initialize() {
        ActiveOpMode.telemetry().addLine("STarted");
    }

    public Command MoveAngle(double angle) {
        return new RunToPosition(controlSystem, angle + RelativeAngle, 0.1).requires(this);
    }
}