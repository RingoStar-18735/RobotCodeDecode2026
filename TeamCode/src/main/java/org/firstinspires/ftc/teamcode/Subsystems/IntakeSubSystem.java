package org.firstinspires.ftc.teamcode.Subsystems;

import org.firstinspires.ftc.teamcode.RobotMap;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.impl.CRServoEx;
import dev.nextftc.hardware.impl.MotorEx;

public class IntakeSubSystem implements Subsystem {
    public final static IntakeSubSystem INSTANCE = new IntakeSubSystem();
    public IntakeSubSystem() {}

    public MotorEx IntakeMotor = new MotorEx("1E").reversed();

    public CRServoEx CR_left = new CRServoEx("02E");
    public CRServoEx CR_right = new CRServoEx("03E");

    private void TransferPower(double power){
        CR_left.setPower(power);
        CR_right.setPower(-power);
    }

    @Override
    public void periodic() {
        ActiveOpMode.telemetry().addData("IntakeMotor Power:" , IntakeMotor.getPower());
        ActiveOpMode.telemetry().addData("IntakeServo Power:" , CR_left.getPower());

    }

    public Command IntakePower(double pow) {
        return new LambdaCommand()
                .setStart(()-> IntakeMotor.setPower(pow))
                .setIsDone( ()-> IntakeMotor.getPower() == pow)
                .requires(this);
    }


    public Command Transfer(double pow) {
        return new LambdaCommand().
                setStart(()-> TransferPower(pow))
                .setIsDone( ()-> CR_left.getPower() == pow && CR_right.getPower() == pow)
                .requires(this);
    }
    public Command IntakeFullyTransfer(){
        return new ParallelGroup(
                Transfer(RobotMap.TRANSFER_SERVO_POWER),
                IntakePower(RobotMap.INTAKE_MOTOR_POWER)
        );
    }

    public Command IntakeFullyNotTransfer(){
        return new ParallelGroup(
                IntakePower(RobotMap.INTAKE_MOTOR_POWER),
                Transfer(-RobotMap.TRANSFER_SERVO_POWER),
                IntakePower(RobotMap.INTAKE_MOTOR_POWER),
                Transfer(-RobotMap.TRANSFER_SERVO_POWER)
                );
    }

    public Command IntakeStop(){
        return new ParallelGroup(
                IntakePower(0.0),
                Transfer(0.0)
        ).setName("קומנד הפסיק אינטייק");
    }

    public Command ReversedIntake() {
        return new ParallelGroup(
            Transfer(-RobotMap.TRANSFER_SERVO_POWER),
            IntakePower(RobotMap.INTAKE_REVERSED_POWER)
        );
    }
}
