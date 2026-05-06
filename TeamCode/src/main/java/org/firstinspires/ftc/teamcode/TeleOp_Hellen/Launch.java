package org.firstinspires.ftc.teamcode.TeleOp_Hellen;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;
import dev.nextftc.hardware.powerable.SetPower;

public class Launch implements Subsystem {
    public final static Launch INSTANCE = new Launch();
    public Launch(){}

    public MotorEx launchMotor = new MotorEx("1C");
    public ServoEx leftServo = new ServoEx("00C");
    public ServoEx rightServo = new ServoEx("01C");



    public Command setAngleLeft(double angle) {
        return new SetPosition(leftServo, 1 - angle);
    }

    public Command setAngleRight(double angle) {
        return new SetPosition(rightServo, angle);
    }

    public Command launchPower(double pow){
        return new SetPower(launchMotor, pow);
    }


}