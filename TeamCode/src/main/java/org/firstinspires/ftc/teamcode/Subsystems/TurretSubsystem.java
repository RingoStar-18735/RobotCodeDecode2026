package org.firstinspires.ftc.teamcode.Subsystems;


import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.impl.MotorEx;

public class TurretSubsystem implements Subsystem {
    public final static TurretSubsystem INSTANCE = new TurretSubsystem();
    public TurretSubsystem(){}



    @Override
    public void periodic() {

        ActiveOpMode.telemetry().update();

    }

    @Override
    public void initialize() {
    }



}