package org.firstinspires.ftc.teamcode.Subsystems;

import Tests.DCTest;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.powerable.SetPower;

public class DCTestSubsystem implements Subsystem {
    public static DCTestSubsystem INSTANCE = new DCTestSubsystem();
    public DCTestSubsystem(){}

    public MotorEx DCmotor = new MotorEx("0C");

    public Command DCmovement(double power) {
        return new SetPower(DCmotor, power);
    }
}
