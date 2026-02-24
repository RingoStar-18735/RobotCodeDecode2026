package org.firstinspires.ftc.teamcode.Subsystems;

import org.firstinspires.ftc.teamcode.RobotMap;
import org.firstinspires.ftc.teamcode.SequentialGroupFixed;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;

public class IntakeSubSystem implements Subsystem {
    public final static IntakeSubSystem INSTANCE = new IntakeSubSystem();
    public IntakeSubSystem() {}

    public MotorEx IntakeMotor = new MotorEx("1E");

    public MotorEx TransferMotor = new MotorEx("0E");

    private void TransferPower(double pow){
        TransferMotor.setPower(pow);
    }

    @Override
    public void periodic() {
//        ActiveOpMode.telemetry().addData("IntakeMotor Power:" , IntakeMotor.getPower());
//        ActiveOpMode.telemetry().addData("Transition Power:" , TransferMotor.getPower());

    }

    public Command IntakePower(double pow) {
        return new LambdaCommand()
                .setStart(()-> IntakeMotor.setPower(pow))
                .setIsDone( ()-> IntakeMotor.getPower() == pow)
                .requires(this);
    }


    public Command Transfer(double pow) {
        return new LambdaCommand()
                .setStart(()-> TransferPower(pow))
                .setIsDone( ()-> TransferMotor.getPower() == pow)
                .requires(this);
    }

    public Command IntakeFullyTransfer(){
        return new ParallelGroup(
                Transfer(RobotMap.TRANSMISSION_MOTOR_POWER),
                IntakePower(RobotMap.INTAKE_MOTOR_POWER)
        );
    }

    public Command IntakeFullyNotTransfer(){
        return new ParallelGroup(
                IntakePower(RobotMap.INTAKE_MOTOR_POWER),
                Transfer(-RobotMap.TRANSMISSION_MOTOR_POWER),
                IntakePower(RobotMap.INTAKE_MOTOR_POWER),
                Transfer(-RobotMap.TRANSMISSION_MOTOR_POWER)
                );
    }

    public Command IntakeStop(){
        return new SequentialGroupFixed(
                IntakePower(0.0),
                Transfer(0.0)
        ).setName("קומנד הפסיק אינטייק");
    }

    public Command ReversedIntake() {
        return new ParallelGroup(
            Transfer(-RobotMap.TRANSMISSION_MOTOR_POWER),
            IntakePower(RobotMap.INTAKE_REVERSED_POWER)
        );
    }
}
