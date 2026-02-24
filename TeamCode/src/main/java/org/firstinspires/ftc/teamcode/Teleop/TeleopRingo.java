package org.firstinspires.ftc.teamcode.Teleop;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.FieldMap;
import org.firstinspires.ftc.teamcode.Printer;
import org.firstinspires.ftc.teamcode.RobotBank;
import org.firstinspires.ftc.teamcode.RobotMap;
import org.firstinspires.ftc.teamcode.SequentialGroupFixed;
import org.firstinspires.ftc.teamcode.Subsystems.AllianceType;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubSystem;
import org.firstinspires.ftc.teamcode.Subsystems.LimelightApril;
import org.firstinspires.ftc.teamcode.Subsystems.ShootType;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.TurretSubsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import java.util.ArrayList;
import java.util.List;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.ParallelDeadlineGroup;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.extensions.pedro.PedroDriverControlled;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.driving.DriverControlledCommand;

@Configurable
@TeleOp(name = "TeleopRingo")
public class TeleopRingo extends NextFTCOpMode {
    Follower follower;
    double newOffset = 0;
    double ServoPos;
    boolean turretRobot = false;
    Pose turretRobotDifrance;
    List<String> a = new ArrayList<String>();
    AllianceType allianceType = AllianceType.BLUE;
    Pose NewTargetPose = FieldMap.BLUE_TARGET_POS;

    public TeleopRingo(){
        addComponents(
                new SubsystemComponent(
                        ShooterSubsystem.INSTANCE,
                        IntakeSubSystem.INSTANCE,
                        TurretSubsystem.INSTANCE,
                        LimelightApril.INSTANCE,
                        Printer.INSTANCE
                ),
                new PedroComponent(Constants::createFollower),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
        hasStartedMatch = false;
    }


    @Override
    public void onInit() {
        allianceType = RobotBank.Alliance;
        if (RobotBank.Alliance == AllianceType.BLUE) {
            NewTargetPose = FieldMap.BLUE_TARGET_POS;
        } else if (RobotBank.Alliance == AllianceType.RED) {
            NewTargetPose = FieldMap.RED_TARGET_POS;
        }
//        ShooterSubsystem.INSTANCE.StopSpeed().schedule();
        IntakeSubSystem.INSTANCE.IntakeStop().schedule();
        TurretSubsystem.INSTANCE.setReset(true);
////        TurretSubsystem.INSTANCE.ResetAngleRight().schedule();
        TurretSubsystem.INSTANCE.setOffset(RobotBank.Offset);
        ShooterSubsystem.INSTANCE.StopSpeed().schedule();
    }


    public Command shootThreeBall(String Range) {
        if (Range == "Far") {
            ServoPos = RobotMap.SERVO_MOVE_FAR;
        } else if (Range == "Mid") {
            ServoPos = RobotMap.SERVO_MOVE_MID;
        } else if (Range == "Close") {
            ServoPos = RobotMap.SERVO_MOVE_CLOSE;
        }
        return new ParallelGroup(
                new SequentialGroup(
                        ShooterSubsystem.INSTANCE.ServoAim(ServoPos),
                        ShooterSubsystem.INSTANCE.RunFullSpeed()
                ),
                new SequentialGroup(
                        IntakeSubSystem.INSTANCE.IntakePower(RobotMap.INTAKE_MOTOR_POWER),
                        IntakeSubSystem.INSTANCE.Transfer(RobotMap.TRANSMISSION_MOTOR_POWER)
                )
        );
    }

    public Command PrintCommand(String b){
        return new InstantCommand(()->a.add(b));
    }

    boolean hasStartedMatch;
    @Override
    public void onUpdate() {
        follower.update();


//        telemetry.addData("ShootFinished: ", ShooterSubsystem.INSTANCE.RunFullSpeed().isDone());
//        telemetry.addData("FinishedCommands: ",a);
        telemetry.addData("Pos: ",follower.getPose());
        telemetry.addData("yaw: ",follower.getPose().getHeading());
        telemetry.addData("LastAutoPos: ",RobotBank.LastAutoPos);
        telemetry.addData("turretRobotDifrance: ",turretRobotDifrance);

        if (hasStartedMatch) {
//            turretRobotDifrance = new Pose(follower.getPose().getX(), follower.getPose().getY(),follower.getPose().getHeading());
            TurretSubsystem.INSTANCE.FollowPoint(NewTargetPose, follower.getPose()).schedule();
        }
//        ActiveOpMode.telemetry().addData("PosRobotCalced: ", LimelightApril.INSTANCE.getRobotPos(follower, TurretSubsystem.INSTANCE.getAngle()));

        //follower.setPose(LimelightApril.INSTANCE.getRobotPos(follower, 01.INSTANCE.getAngle()));




    }

    @Override
    public void onStartButtonPressed() {
        follower =  Constants.createFollower(hardwareMap);
        follower.setPose(RobotBank.LastAutoPos);
        hasStartedMatch = true;
        DriverControlledCommand driverControlled = new PedroDriverControlled(
                Gamepads.gamepad1().leftStickX(),
                Gamepads.gamepad1().leftStickY().negate(),
                Gamepads.gamepad1().rightStickX().negate(),
                false
        );
        driverControlled.schedule();

        Gamepads.gamepad1().rightBumper()
//                .whenTrue(
//                        new InstantCommand(()-> TurretSubsystem.INSTANCE.setOffset(RobotBank.Offset + Math.toRadians(180) + follower.getPose().getHeading()))
//                )
                .whenTrue(
                        new InstantCommand(()-> follower.setPose(new Pose(follower.getPose().getX(), follower.getPose().getY(), Math.toRadians(0))))
                );


        Gamepads.gamepad1().leftBumper().toggleOnBecomesTrue()
                .whenBecomesTrue(
                        new InstantCommand(()->TurretSubsystem.INSTANCE.setToFollow(false))
                ).whenBecomesFalse(
                        new InstantCommand(()->TurretSubsystem.INSTANCE.setToFollow(true))
                );

        Gamepads.gamepad1().x().whenTrue(
                ShooterSubsystem.INSTANCE.ServoAim(0)
        );

        Gamepads.gamepad2().y()
                        .whenBecomesTrue(
                                new ParallelGroup(
                                        ShooterSubsystem.INSTANCE.ServoAim(RobotMap.SERVO_MOVE_FAR),
                                        ShooterSubsystem.INSTANCE.RunFullSpeed()
                                )
                        );
//                .whenTrue(ShootFromFar);
//                .whenTrue(
//                        new InstantCommand( () -> ShooterSubsystem.INSTANCE.ReverseWheel = false)
//                )
//                .whenTrue(
//                        KeyCommands.Shoot(ShootType.FAR)
//                );


        Gamepads.gamepad2().x()
                .whenTrue(
                        new InstantCommand( () -> ShooterSubsystem.INSTANCE.ReverseWheel = false)
                )
                .whenTrue(
                        KeyCommands.Shoot((ShootType.MID))
                );

        Gamepads.gamepad2().b()
                .whenTrue(
                new InstantCommand( () -> ShooterSubsystem.INSTANCE.ReverseWheel = false)
                )
                .whenTrue(
                        KeyCommands.Shoot((ShootType.CLOSE))
                );

        Gamepads.gamepad2().a()
                .whenTrue(
                        new ParallelDeadlineGroup(
                                new Delay(1),
                                ShooterSubsystem.INSTANCE.StopSpeed(),
                                IntakeSubSystem.INSTANCE.IntakeStop()

                        ));



            Gamepads.gamepad2().leftBumper().toggleOnBecomesTrue()
                    .whenBecomesTrue(
                            IntakeSubSystem.INSTANCE.IntakePower(RobotMap.INTAKE_MOTOR_POWER)
                    )
                    .whenBecomesTrue(IntakeSubSystem.INSTANCE.Transfer(-RobotMap.TRANSMISSION_MOTOR_POWER)
                    )
                    .whenBecomesFalse(
                            IntakeSubSystem.INSTANCE.IntakeStop()
                    );


            Gamepads.gamepad2().rightBumper().toggleOnBecomesTrue()
                    .whenBecomesTrue(
                            IntakeSubSystem.INSTANCE.IntakePower(RobotMap.INTAKE_REVERSED_POWER)
                    )
                    .whenBecomesTrue(IntakeSubSystem.INSTANCE.Transfer(-RobotMap.TRANSMISSION_MOTOR_POWER)
                    )
                    .whenBecomesFalse(
                            IntakeSubSystem.INSTANCE.IntakeStop()
                    );



        Gamepads.gamepad2().dpadDown().whenTrue(
                new SequentialGroupFixed(
                        new InstantCommand( () -> ShooterSubsystem.INSTANCE.ReverseWheel = true)
                        )
        );

        Gamepads.gamepad2().dpadDown().whenFalse(
                new SequentialGroupFixed(
                        new InstantCommand( () -> ShooterSubsystem.INSTANCE.ReverseWheel = false
                        )
                )
        );
    }
}
