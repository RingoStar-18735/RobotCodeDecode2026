package org.firstinspires.ftc.teamcode.Subsystems;

import org.firstinspires.ftc.teamcode.RobotMap;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.impl.CRServoEx;
import dev.nextftc.hardware.impl.MotorEx;

public class IntakeSubSystem implements Subsystem {
    public final static IntakeSubSystem INSTANCE = new IntakeSubSystem();
    private  double Motorpower = 0.0;
    private double Servopower = 0.0;
    public IntakeSubSystem() {}

    public MotorEx IntakeMotor = new MotorEx("1E");

    public CRServoEx CR_left = new CRServoEx("02E");
    public CRServoEx CR_right = new CRServoEx("03E");

    private void TransferPower(double power){
        CR_left.setPower(power);
        CR_right.setPower(-power);
    }

    @Override
    public void periodic() {
        ActiveOpMode.telemetry().addData("IntakeMotor Power:" , Motorpower);
        ActiveOpMode.telemetry().addData("IntakeServo Power:" , Servopower);
        TransferPower(Servopower);
        IntakeMotor.setPower(Motorpower);

    }

    public Command IntakePower(double pow) {
        return new InstantCommand(
                ()-> Motorpower = pow
        );
    }


    public Command Transfer(double pow) {
        return new InstantCommand(
                ()-> Servopower = pow
        );
    }

    public Command IntakeFullyTransfer(){
        return new ParallelGroup(
                Transfer(RobotMap.TRANSFER_SERVO_POWER),
                IntakePower(RobotMap.INTAKE_MOTOR_POWER)
        ).endAfter(RobotMap.INTAKE_FULLY_TIME);
    }
    public Command IntakeFullyNotTransfer(){
        return new ParallelGroup(
                Transfer(-RobotMap.TRANSFER_SERVO_POWER),
                IntakePower(RobotMap.INTAKE_MOTOR_POWER)
        ).endAfter(RobotMap.INTAKE_FULLY_TIME);
    }

    public Command IntakeStop(){
        return new ParallelGroup(
                Transfer(0.0),
                IntakePower(0.0)
        );
    }

    public Command ReversedIntake(){
        return new SequentialGroup(
                Transfer(-RobotMap.TRANSFER_SERVO_POWER),
                IntakePower(RobotMap.INTAKE_REVERSED_POWER)

        ).endAfter(RobotMap.INTAKE_REVERSED_TIME);
    }
}
