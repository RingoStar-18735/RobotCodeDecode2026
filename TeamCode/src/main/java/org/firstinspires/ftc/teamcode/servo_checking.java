package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.CRServoImpl;
import com.qualcomm.robotcore.hardware.ServoController;

import org.firstinspires.ftc.teamcode.Subsystems.TurretSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.launch;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.hardware.controllable.RunToPosition;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;
import dev.nextftc.hardware.positionable.SetPositions;
import dev.nextftc.hardware.powerable.Powerable;
import dev.nextftc.hardware.powerable.SetPower;

public class servo_checking implements Subsystem {
    public final static servo_checking INSTANCE = new servo_checking();
    public servo_checking(){}
    /*public ServoEx Ser = new ServoEx("00C");


    public Command pos(double pos) {
        return new SetPosition(Ser, pos);
    }*/

}
