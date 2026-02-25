package org.firstinspires.ftc.teamcode.Teleop;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.FieldMap;
import org.firstinspires.ftc.teamcode.Printer;
import org.firstinspires.ftc.teamcode.RobotBank;
import org.firstinspires.ftc.teamcode.RobotMap;
import org.firstinspires.ftc.teamcode.SequentialGroupFixed;
import org.firstinspires.ftc.teamcode.Subsystems.AllianceType;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubSystem;
import org.firstinspires.ftc.teamcode.Subsystems.LimelightApril;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.TurretSubsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import java.util.ArrayList;
import java.util.List;

import dev.nextftc.core.commands.Command;
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
@TeleOp(name = "TeleopRingo - BLUE")
public class TeleopRingoBlue extends NextFTCOpMode {
    Follower follower;
    double newOffset = 0;
    double ServoPos;
    boolean turretRobot = false;
    Pose turretRobotDifrance;
    List<String> a = new ArrayList<String>();
    boolean activateFieldCentricCoraction = false;
    double errorDistance = 0;
    double lastYPos = 0;
    double newYPos;
    Timer timer;
    AllianceType allianceType = AllianceType.BLUE;
    Pose NewTargetPose = FieldMap.BLUE_TARGET_POS;

    public TeleopRingoBlue(){
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


    Command ShootFar = new SequentialGroup(
            new ParallelGroup(
                    IntakeSubSystem.INSTANCE.Transfer(RobotMap.TRANSMISSION_MOTOR_POWER),
                    IntakeSubSystem.INSTANCE.IntakePower(RobotMap.INTAKE_MOTOR_POWER)
            ),
            ShooterSubsystem.INSTANCE.RunFullSpeed(),
            ShooterSubsystem.INSTANCE.ServoAim(RobotMap.SERVO_MOVE_FAR)
    );

    Command ShootMid = new SequentialGroup(
            new ParallelGroup(
                    IntakeSubSystem.INSTANCE.Transfer(RobotMap.TRANSMISSION_MOTOR_POWER),
                    IntakeSubSystem.INSTANCE.IntakePower(RobotMap.INTAKE_MOTOR_POWER)
            ),
            ShooterSubsystem.INSTANCE.RunFullSpeed(),
            ShooterSubsystem.INSTANCE.ServoAim(RobotMap.SERVO_MOVE_MID)
    );

    Command ShootClose = new SequentialGroup(
            new ParallelGroup(
                    IntakeSubSystem.INSTANCE.Transfer(RobotMap.TRANSMISSION_MOTOR_POWER),
                    IntakeSubSystem.INSTANCE.IntakePower(RobotMap.INTAKE_MOTOR_POWER)
            ),
            ShooterSubsystem.INSTANCE.RunFullSpeed(),
            ShooterSubsystem.INSTANCE.ServoAim(RobotMap.SERVO_MOVE_CLOSE)
    );

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
////        TurretSubsystem.INSTANCE.ResetAngleRight().schedule();
        TurretSubsystem.INSTANCE.setOffset(RobotBank.Offset);
        ShooterSubsystem.INSTANCE.StopSpeed().schedule();
    }


    Command ShootNew =
            ShooterSubsystem.INSTANCE.RunFullSpeed();


    public Command PrintCommand(String b){
        return new InstantCommand(()->a.add(b));
    }

//    public Command shoot(){
//        timer.resetTimer();
//        if (timer.getElapsedTimeSeconds() <= 3) {
//            new ParallelDeadlineGroup(
//                    ShooterSubsystem.INSTANCE.ServoAim(RobotMap.SERVO_MOVE_FAR),
//                    ShooterSubsystem.INSTANCE.RunFullSpeed()
//            );
//        } else if (timer.getElapsedTimeSeconds() > 3) {
//            ShooterSubsystem.INSTANCE.StopSpeed();
//
//        }
//    }


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
        telemetry.addData("Math.toRadians(90): ",Math.toRadians(90));
        telemetry.addData("getHeading() - Math.toRadians(90): ",follower.getPose().getHeading() + Math.toRadians(90));
        telemetry.addData("lastPos: ", lastYPos);
        telemetry.addData("errorDistance: ",errorDistance);
        telemetry.addData("follower.getPose().getX(): ",follower.getPose().getX());

        if (hasStartedMatch) {
            if (activateFieldCentricCoraction) {
                errorDistance = follower.getPose().getY() - lastYPos;
                newYPos = lastYPos - errorDistance;
                turretRobotDifrance = new Pose(follower.getPose().getX(), newYPos,follower.getPose().getHeading() + Math.toRadians(90));
            } else {
                newYPos = follower.getPose().getY();
                turretRobotDifrance = new Pose(follower.getPose().getX(), newYPos,follower.getPose().getHeading());
            }
            TurretSubsystem.INSTANCE.FollowPoint(NewTargetPose, turretRobotDifrance).schedule();
        }
//        ActiveOpMode.telemetry().addData("PosRobotCalced: ", LimelightApril.INSTANCE.getRobotPos(follower, TurretSubsystem.INSTANCE.getAngle()));

        //follower.setPose(LimelightApril.INSTANCE.getRobotPos(follower, 01.INSTANCE.getAngle()));




    }

    @Override
    public void onStartButtonPressed() {
        TurretSubsystem.INSTANCE.setReset(true);
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
                .whenTrue(
                        new InstantCommand(()-> lastYPos = follower.getPose().getY())
                )
                .whenTrue(
                        new InstantCommand(()-> activateFieldCentricCoraction = true)
                )
                .whenTrue(
                        new InstantCommand(()-> follower.setPose(new Pose(follower.getPose().getX(), follower.getPose().getY(), Math.toRadians(90))))
                );


        Gamepads.gamepad1().leftBumper().toggleOnBecomesTrue()
                .whenBecomesTrue(
                        new InstantCommand(()->TurretSubsystem.INSTANCE.setToFollow(false))
                ).whenBecomesFalse(
                        new InstantCommand(()->TurretSubsystem.INSTANCE.setToFollow(true))
                );

        Gamepads.gamepad2().y()
                .whenBecomesTrue(
                        ShootFar
                );


        Gamepads.gamepad2().x()
                .whenBecomesTrue(
                        ShootMid
                );

        Gamepads.gamepad2().b()
                .whenBecomesTrue(
                        ShootClose
                );

        Gamepads.gamepad2().a()
                .whenTrue(
                        new ParallelGroup(
                                ShooterSubsystem.INSTANCE.StopSpeed(),
                                IntakeSubSystem.INSTANCE.IntakeStop()
                        ));



        Gamepads.gamepad2().leftBumper().toggleOnBecomesTrue()
                .whenBecomesTrue(
                        IntakeSubSystem.INSTANCE.IntakePower(RobotMap.INTAKE_MOTOR_POWER)
                )
                .whenBecomesTrue(IntakeSubSystem.INSTANCE.Transfer(-RobotMap.TRANSMISSION_MOTOR_REVERSE_POWER)
                )
                .whenBecomesFalse(
                        IntakeSubSystem.INSTANCE.IntakeStop()
                );


        Gamepads.gamepad2().rightBumper()
                .whenBecomesTrue(
                        IntakeSubSystem.INSTANCE.IntakePower(RobotMap.INTAKE_MOTOR_POWER)
                )
                .whenBecomesTrue(IntakeSubSystem.INSTANCE.Transfer(RobotMap.TRANSMISSION_MOTOR_POWER));




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


//
//        Gamepads.gamepad2().dpadDown().whenTrue(
//                new SequentialGroupFixed(
//                        new InstantCommand( () -> ShooterSubsystem.INSTANCE.ReverseWheel = true)
//                        )
//        );
//
//        Gamepads.gamepad2().dpadDown().whenFalse(
//                new SequentialGroupFixed(
//                        new InstantCommand( () -> ShooterSubsystem.INSTANCE.ReverseWheel = false
//                        )
//                )
//        );
//        Gamepads.gamepad1().a().whenBecomesTrue(
//                new ParallelGroup(
//                        IntakeSubSystem.INSTANCE.IntakePower(RobotMap.INTAKE_MOTOR_POWER),
//                        IntakeSubSystem.INSTANCE.Transfer(RobotMap.TRANSMISSION_MOTOR_POWER),
//                        ShooterSubsystem.INSTANCE.ServoAim(RobotMap.SERVO_MOVE_MID),
//                        ShooterSubsystem.INSTANCE.RunFullSpeed()
//        ));
//
//        Gamepads.gamepad1().y().whenBecomesTrue(
//                new ParallelGroup(
//                        IntakeSubSystem.INSTANCE.IntakeStop(),
//                        IntakeSubSystem.INSTANCE.Transfer(0),
//                        ShooterSubsystem.INSTANCE.StopSpeed()
//                )
//        );
//    }
    }
}
