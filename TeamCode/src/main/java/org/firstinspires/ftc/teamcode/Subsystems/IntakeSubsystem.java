package org.firstinspires.ftc.teamcode.Subsystems;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.CRServoEx;
import dev.nextftc.hardware.powerable.SetPower;

public class IntakeSubsystem implements Subsystem {
    public static IntakeSubsystem INSTANCE = new IntakeSubsystem();
    public IntakeSubsystem() {}

    public CRServoEx intakeLeft = new CRServoEx("01C");
    public CRServoEx intakeRight = new CRServoEx("02C");


    public Command intakePow(double pow) {
        return new SetPower(intakeLeft, pow).requires(this);
        return new SetPower(intakeRight, -pow).requires(this);

        //CRServo myServo = hardwareMap.get(CRServo.class, "myCRServo");
    }

}