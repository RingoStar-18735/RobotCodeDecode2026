package org.firstinspires.ftc.teamcode.Subsystems;

import org.firstinspires.ftc.teamcode.RobotMap;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.CRServoEx;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.powerable.SetPower;

public class IntakeSubSystem implements Subsystem {
    public final static IntakeSubSystem INSTANCE = new IntakeSubSystem();

    public IntakeSubSystem() {}

    public MotorEx IntakeMotor = new MotorEx("1E");

    public CRServoEx CR_left = new CRServoEx("02E");
    public CRServoEx CR_right = new CRServoEx("03E");

    private void TransferPower(double power){
        CR_left.setPower(power);
        CR_right.setPower(-power);
    }

    public Command Transfer(double pow) {
        return new InstantCommand(
                ()-> TransferPower(pow)
        );
    }

    public Command IntakePower(double pow) {
        return new SetPower(IntakeMotor, pow);
    }

    public Command IntakeFullyTransfer(){
        return new ParallelGroup(
                Transfer(RobotMap.TRANSFER_SERVO_POWER),
                IntakePower(RobotMap.INTAKE_MOTOR_POWER)
        );
    }
    public Command IntakeFullyNotTransfer(){
        return new ParallelGroup(
                Transfer(-RobotMap.TRANSFER_SERVO_POWER),
                IntakePower(RobotMap.INTAKE_MOTOR_POWER)
        );
    }

    public Command IntakeStop(){
        return new ParallelGroup(
                Transfer(0.0),
                IntakePower(0.0)
        );
    }
}
