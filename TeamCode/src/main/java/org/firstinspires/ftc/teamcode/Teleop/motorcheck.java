package org.firstinspires.ftc.teamcode.Teleop;

import dev.nextftc.core.commands.Command;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.powerable.SetPower;

public class motorcheck {
    public MotorEx Motor1 = new MotorEx("Motor1");
    public Command motormove(double pow){
        return new SetPower(Motor1, pow);
    }
}
