package org.firstinspires.ftc.teamcode;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.CRServoEx;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.powerable.SetPower;

public class IntakeSubsystem implements Subsystem {
    public final static IntakeSubsystem INSTANCE = new IntakeSubsystem();
    public IntakeSubsystem() {}

    public MotorEx intake = new MotorEx("0C");
    public CRServoEx ServoR = new CRServoEx("1C");
    public CRServoEx ServoL = new CRServoEx("2C");

    public Command IntakePowerBB(double pow) {
        return new SetPower(intake, pow);
    }

    public Command ServoRPower(double pow) {
        return new SetPower(ServoR, pow);
    }

    public Command ServoLPower(double pow) {
        return new SetPower(ServoL, pow);
    }

    public Command IntakeFullPower(double IntakePow, double ServoPow){
        return new ParallelGroup(
                IntakePowerBB(IntakePow),
                ServoRPower(ServoPow),
                ServoLPower(ServoPow)
        );
    }

    public Command Intake_stop() {
        return IntakeFullPower(0, 0);
    }
    public Command Intake_Reversed(double IntakePow, double ServoPow) {
        return IntakeFullPower(-IntakePow, -ServoPow);
    }

}