package org.firstinspires.ftc.teamcode.Subsystems;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.powerable.SetPower;

public class HDhexMotorsSubsystem implements Subsystem {
    public static HDhexMotorsSubsystem INSTANCE = new HDhexMotorsSubsystem();

    public MotorEx HHmotor = new MotorEx("0C");

    public Command HHMmovment (double pow){
        return new SetPower(HHmotor, pow);
    }

}
