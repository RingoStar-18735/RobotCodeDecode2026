package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import com.sun.tools.javac.util.Pair;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;
import dev.nextftc.hardware.positionable.SetPositions;
import dev.nextftc.hardware.powerable.SetPower;

public class launch implements Subsystem {
    public final static launch INSTANCE = new launch();
    public launch(){}

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
