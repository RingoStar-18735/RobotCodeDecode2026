package org.firstinspires.ftc.teamcode.Teleop;

import org.firstinspires.ftc.teamcode.Printer;
import org.firstinspires.ftc.teamcode.RobotMap;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubSystem;
import org.firstinspires.ftc.teamcode.Subsystems.ShootType;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.ParallelDeadlineGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;

public class KeyCommands {

    public static Command Shoot(ShootType type) {
        double shootAngle = RobotMap.SERVO_MOVE_CLOSE;
        if(type == ShootType.FAR) shootAngle = RobotMap.SERVO_MOVE_FAR;
        else if (type == ShootType.MID) shootAngle = RobotMap.SERVO_MOVE_MID;
        Command Shoot =
                new SequentialGroup(
                        new ParallelDeadlineGroup(
                                new Delay(1),
                                Printer.INSTANCE.addPrintCommand("Shoot 1"),
                                ShooterSubsystem.INSTANCE.RunFullSpeed(),
                                ShooterSubsystem.INSTANCE.ServoAim(shootAngle),
                                Printer.INSTANCE.addPrintCommand("Shoot 2")
                                ),
                        Printer.INSTANCE.addPrintCommand("Shoot 3"),//good
                        new ParallelDeadlineGroup(
                                new Delay(RobotMap.INTAKE_SHOOT_TIME_FIRST),
                                new SequentialGroup(
                                        IntakeSubSystem.INSTANCE.IntakePower(RobotMap.INTAKE_MOTOR_POWER),
                                        Printer.INSTANCE.addPrintCommand("Shoot 4"),
                                        IntakeSubSystem.INSTANCE.Transfer(RobotMap.TRANSMISSION_MOTOR_POWER),
                                        Printer.INSTANCE.addPrintCommand("Shoot 5"),
                                        ShooterSubsystem.INSTANCE.RunFullSpeed(),
                                        Printer.INSTANCE.addPrintCommand("Shoot 6"),
                                        IntakeSubSystem.INSTANCE.IntakePower(RobotMap.INTAKE_MOTOR_POWER),
                                        Printer.INSTANCE.addPrintCommand("Shoot 7"),
                                        IntakeSubSystem.INSTANCE.Transfer(RobotMap.TRANSMISSION_MOTOR_POWER),
                                        Printer.INSTANCE.addPrintCommand("Shoot 8"),
                                        ShooterSubsystem.INSTANCE.RunFullSpeed()
                                ),
                                Printer.INSTANCE.addPrintCommand("Shoot 9")
                                ),

                        Printer.INSTANCE.addPrintCommand("Shoot 10"),
                        new ParallelDeadlineGroup(
                                ShooterSubsystem.INSTANCE.StopSpeed(),
                                Printer.INSTANCE.addPrintCommand("Shoot 11"),
                                IntakeSubSystem.INSTANCE.IntakeStop()
                        ),
                        Printer.INSTANCE.addPrintCommand("Shoot 12")

                );
        return Shoot;
    }

}
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