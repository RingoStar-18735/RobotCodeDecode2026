package org.firstinspires.ftc.teamcode.Teleop;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.CRServoEx;
import dev.nextftc.hardware.powerable.SetPower;

public class CRTest implements Subsystem {

    public final static CRTest INSTANCE = new CRTest();
    public CRTest(){}

    public CRServoEx CR_left = new CRServoEx("02E");
    public CRServoEx CR_right = new CRServoEx("03E");



    public Command CR_move_left(double pow) {
        return new SetPower(CR_left, -pow);
    }

    public Command CR_move_right(double pow) {
        return new SetPower(CR_right, pow);
    }
}
