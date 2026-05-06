package org.firstinspires.ftc.teamcode.TeleOp_Hellen;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.powerable.SetPower;

public class IntakeTransportSubsystem implements Subsystem {
    public static IntakeTransportSubsystem INSTANCE = new IntakeTransportSubsystem();
    public IntakeTransportSubsystem(){}

    public MotorEx DCmotor = new MotorEx("0C");

    public Command DCmovement(double power) {
        return new SetPower(DCmotor, power);
    }
}