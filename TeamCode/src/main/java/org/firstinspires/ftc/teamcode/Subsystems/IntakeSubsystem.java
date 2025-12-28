package org.firstinspires.ftc.teamcode.Subsystems;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.CRServoEx;
import dev.nextftc.hardware.powerable.SetPower;

public class IntakeSubsystem implements Subsystem {
    public static IntakeSubsystem INSTANCE = new IntakeSubsystem();
    public IntakeSubsystem() {}


    public double pow;
    public CRServoEx intakeLeft = new CRServoEx ("01C");
    public CRServoEx intakeRight = new CRServoEx ("02C");

    public void setIntakeLeft(CRServoEx intakeLeft) {
        this.intakeLeft = intakeLeft;
        new SetPower(intakeLeft, 1-pow);
    }

    public void setIntakeRight(CRServoEx intakeRight) {
        this.intakeRight = intakeRight;
        new SetPower(intakeRight, pow);
    }

    public Command intakePow(double pow){
        return new
    }

    /*public Command intakePowLeft(double pow) {
        return new SetPower(intakeLeft, pow).requires(this);
    }

    public Command intakePowRight(double pow) {
        return new SetPower(intakeRight, pow).requires(this);
    }

    public Command intakePow(double pow){
        return new ParallelGroup(
                IntakeSubsystem.INSTANCE.intakePowLeft(pow),
                IntakeSubsystem.INSTANCE.intakePowRight(1-pow)
        );
    }*/

//    public Command intakePowOFF(){
//        return new ParallelGroup(
//                IntakeSubsystem.INSTANCE.intakePowLeft(0),
//                IntakeSubsystem.INSTANCE.intakePowRight(0)
//        );
//    }

    crServoEx.setPower(0.0); // To turn off
crServoEx.setPower(-1.0); // To spin in reverse fully
crServoEx.setPower(0.5); // To spin forward partially


}