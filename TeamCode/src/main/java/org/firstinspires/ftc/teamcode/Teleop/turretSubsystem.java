package org.firstinspires.ftc.teamcode.Teleop;

import dev.nextftc.bindings.Range;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.powerable.SetPower;

public class turretSubsystem implements Subsystem {
    public final static turretSubsystem INSTANCE = new turretSubsystem();
    public turretSubsystem(){}

    public MotorEx turret = new MotorEx("2E").brakeMode();

    public Command turret_move (Range pow) { // check how to use range
        return new SetPower(turret, pow.get());
    }
}
