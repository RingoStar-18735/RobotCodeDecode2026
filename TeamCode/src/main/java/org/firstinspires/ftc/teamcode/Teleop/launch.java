package org.firstinspires.ftc.teamcode.Teleop;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;
import dev.nextftc.hardware.powerable.SetPower;

public class launch implements Subsystem {
    public final static launch INSTANCE = new launch();
    public launch(){}

    public MotorEx launchMotor = new MotorEx("3E");
    public ServoEx leftServo = new ServoEx("00E");
    public ServoEx rightServo = new ServoEx("01E");



    public Command setAngleLeft(double angle) {
        return new SetPosition(leftServo, 1 - angle);
    }

    public Command setAngleRight(double angle) {
        return new SetPosition(rightServo, angle);
    }

    public Command launchPower(double pow){
        return new SetPower(launchMotor, -pow);
    }


}