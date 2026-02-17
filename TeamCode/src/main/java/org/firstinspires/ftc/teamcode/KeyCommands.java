package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubSystem;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.ParallelDeadlineGroup;

public class KeyCommands {
    public static Command Shoot(ShootType type){
        double shootAngle = RobotMap.SERVO_MOVE_CLOSE;
        if(type == ShootType.FAR) shootAngle = RobotMap.SERVO_MOVE_FAR;
        else if (type == ShootType.MID) shootAngle = RobotMap.SERVO_MOVE_MID;
        Command Shoot =
                new SequentialGroupFixed(
                        new ParallelDeadlineGroup(
                                new Delay(1),
                                ShooterSubsystem.INSTANCE.RunFullSpeed(),
                                ShooterSubsystem.INSTANCE.ServoAim(shootAngle)
                        ),
                        new ParallelDeadlineGroup(
                                new Delay(RobotMap.INTAKE_SHOOT_TIME_FIRST),
                                new SequentialGroupFixed(
                                        IntakeSubSystem.INSTANCE.IntakeFullyTransfer(),
                                        ShooterSubsystem.INSTANCE.RunFullSpeed(),
                                        IntakeSubSystem.INSTANCE.IntakeFullyTransfer(),
                                        ShooterSubsystem.INSTANCE.RunFullSpeed()
                                )
                        ),
//                    new SequentialGroup(
//                            new ParallelDeadlineGroup(
//                                    new Delay(RobotMap.BACK_INTAKE_LAST_BALL),
//                                    //IntakeSubSystem.INSTANCE.Transfer(-RobotMap.TRANSFER_SERVO_POWER),
//                                    //IntakeSubSystem.INSTANCE.IntakePower(-RobotMap.INTAKE_MOTOR_POWER),
//                                    ShooterSubsystem.INSTANCE.RunFullSpeed()
//                            ),
//                            PrintCommand("Finished 3"),
//
//                            new ParallelDeadlineGroup(
//                                    new Delay(RobotMap.FORWARD_INTAKE_LAST_BALL),
//                                    IntakeSubSystem.INSTANCE.Transfer(RobotMap.TRANSFER_SERVO_POWER),
//                                    IntakeSubSystem.INSTANCE.IntakePower(RobotMap.INTAKE_MOTOR_POWER),
//                                    ShooterSubsystem.INSTANCE.RunFullSpeed()
//                            ),
//                            PrintCommand("Finished 4")
//
//                    ),
                        new ParallelDeadlineGroup(
                                ShooterSubsystem.INSTANCE.StopSpeed(),
                                IntakeSubSystem.INSTANCE.IntakeStop()
                        )

                );
        return Shoot;
    }
}
