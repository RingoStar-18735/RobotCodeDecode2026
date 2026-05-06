package org.firstinspires.ftc.teamcode.TeleOp_Hellen;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPositions;

public class ServoSubsystem implements Subsystem {
    public static ServoSubsystem INSTANCE = new ServoSubsystem();
    public ServoSubsystem(){}

    public ServoEx rightServo = new ServoEx("01C");
    public ServoEx leftServo = new ServoEx("02C");

    public Command ServoMovement(double angle){
        return new SetPositions(rightServo.to(angle), leftServo.to(1 -angle));
    }

}
