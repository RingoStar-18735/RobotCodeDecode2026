package org.firstinspires.ftc.teamcode.Subsystems;

import android.view.MotionPredictor;

import com.qualcomm.robotcore.hardware.CRServo;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.RunToPosition;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.powerable.SetPower;

public class Intake implements Subsystem {
    public final static Intake INSTANCE = new Intake();
    public Intake(){}

    public final MotorEx intake = new MotorEx("0C");

    public Command Intakepow (double pow){
        return new SetPower(intake, pow);
    }





}
