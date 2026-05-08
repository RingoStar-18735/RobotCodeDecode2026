package org.firstinspires.ftc.teamcode.Teleop;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.FieldMap;
import org.firstinspires.ftc.teamcode.Printer;
import org.firstinspires.ftc.teamcode.RobotBank;
import org.firstinspires.ftc.teamcode.RobotMap;
import org.firstinspires.ftc.teamcode.Subsystems.AllianceType;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubSystem;
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
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.extensions.pedro.PedroDriverControlled;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.driving.DriverControlledCommand;

@Configurable
@TeleOp(name = "TeleopRingo - RED")
public class TeleopRingoRed extends NextFTCOpMode {
    Follower follower;
    Pose RobotPose = new Pose(0, 0, 0);
    double distance = 0;
    List<String> a = new ArrayList<String>();
    AllianceType allianceType = AllianceType.BLUE;
    Pose NewTargetPose = FieldMap.RED_TARGET_POS;

    public TeleopRingoRed(){
        addComponents(
                new SubsystemComponent(
                        ShooterSubsystem.INSTANCE,
                        IntakeSubSystem.INSTANCE,
                        TurretSubsystem.INSTANCE,
                        Printer.INSTANCE
                ),
                new PedroComponent(Constants::createFollower),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
        hasStartedMatch = false;
    }

    public Command Shoot() {
        return new LambdaCommand()
                .setStart(() -> {
                    telemetry.addLine("SHOOT COMMAND STARTED");
                    telemetry.addData("vel: ", -ShooterSubsystem.INSTANCE.Shooter.getVelocity());
                    telemetry.addData("Speed: ", ShooterSubsystem.INSTANCE.ShooterSpeed - 300);
                })
                .setUpdate(() -> {
                    if (Math.abs(ShooterSubsystem.INSTANCE.Shooter.getVelocity()) >= ShooterSubsystem.INSTANCE.ShooterSpeed - 300) {
                        telemetry.addData("אני2: ", -ShooterSubsystem.INSTANCE.Shooter.getVelocity());
                        IntakeSubSystem.INSTANCE.IntakeMotor.setPower(RobotMap.INTAKE_MOTOR_POWER);
                        IntakeSubSystem.INSTANCE.TransferMotor.setPower(RobotMap.TRANSMISSION_MOTOR_POWER);

                    }
//                    else {
//                        IntakeSubSystem.INSTANCE.IntakeMotor.setPower(0);
//                        IntakeSubSystem.INSTANCE.TransferMotor.setPower(0);
//                    }
                })
                .setIsDone(()-> false)
                .requires(IntakeSubSystem.INSTANCE);
    }

    Command shootSequence = new SequentialGroup(
            new ParallelDeadlineGroup(
                    new Delay(2),
                    ShooterSubsystem.INSTANCE.RunFullSpeed(() ->
                            Math.sqrt(
                                    Math.pow(NewTargetPose.getX() - RobotPose.getX(), 2) +
                                            Math.pow(NewTargetPose.getY() - RobotPose.getY(), 2)
                            )
                    )
            ),
            Shoot()
    );

    @Override
    public void onInit() {
        follower =  Constants.createFollower(hardwareMap);
        follower.setPose(RobotBank.LastAutoPos);
        new InstantCommand(ShooterSubsystem.INSTANCE.StopSpeed());
        allianceType = RobotBank.Alliance;
        if (RobotBank.Alliance == AllianceType.BLUE) {
            NewTargetPose = FieldMap.BLUE_TARGET_POS;
        } else if (RobotBank.Alliance == AllianceType.RED) {
            NewTargetPose = FieldMap.RED_TARGET_POS;
        }
        TurretSubsystem.INSTANCE.setOffset(RobotBank.Offset);
        new InstantCommand(()-> IntakeSubSystem.INSTANCE.IntakeMotor.setPower(0));
        new InstantCommand(()-> IntakeSubSystem.INSTANCE.TransferMotor.setPower(0));
        new InstantCommand(ShooterSubsystem.INSTANCE.StopSpeed());

    }

    boolean hasStartedMatch;
    @Override
    public void onUpdate() {
        follower.update();
        RobotPose = follower.getPose();


        telemetry.addData("Pos: ",follower.getPose());
//        telemetry.addData("yaw: ",follower.getPose().getHeading());
        telemetry.addData("LastAutoPos: ",RobotBank.LastAutoPos);
//        telemetry.addData("turretRobotDifrance: ",turretRobotDifrance);
//        telemetry.addData("Math.toRadians(90): ",Math.toRadians(90));
//        telemetry.addData("getHeading() - Math.toRadians(90): ",follower.getPose().getHeading() - Math.toRadians(90));
//        telemetry.addData("lastPos: ", lastYPos);
//        telemetry.addData("errorDistance: ",errorDistance);
//        telemetry.addData("follower.getPose().getX(): ",follower.getPose().getX());
        telemetry.addData("getVelocity: ",-ShooterSubsystem.INSTANCE.Shooter.getVelocity());
        telemetry.addData("מרחק: ",distance);
//        telemetry.addData("סרבו לאסט פוס", ShooterSubsystem.INSTANCE.ServoLastPos);
        telemetry.addData("RobotPose", RobotPose);
//        telemetry.addData("ServoActivate", ServoActivate);
        distance = Math.sqrt(Math.pow(NewTargetPose.getX() - RobotPose.getX(), 2) + Math.pow(NewTargetPose.getY() - RobotPose.getY(), 2));


        if (hasStartedMatch) {
            TurretSubsystem.INSTANCE.FollowPoint(NewTargetPose, RobotPose).schedule();
        }
    }

    @Override
    public void onStartButtonPressed() {
        hasStartedMatch = true;
        ShooterSubsystem.INSTANCE.ServoMoveByField(() ->
                Math.sqrt(
                        Math.pow(NewTargetPose.getX() - RobotPose.getX(), 2) +
                                Math.pow(NewTargetPose.getY() - RobotPose.getY(), 2)
                )
        ).schedule();

        TurretSubsystem.INSTANCE.setReset(true);
        ShooterSubsystem.INSTANCE.StopSpeed();

        DriverControlledCommand driverControlled = new PedroDriverControlled(
                Gamepads.gamepad1().leftStickY(),
                Gamepads.gamepad1().leftStickX(),
                Gamepads.gamepad1().rightStickX().negate(),
                false
        );
        driverControlled.schedule();

        Gamepads.gamepad1().leftBumper().toggleOnBecomesTrue()
                .whenBecomesTrue(
                        new InstantCommand(()->TurretSubsystem.INSTANCE.setToFollow(false))
                ).whenBecomesFalse(
                        new InstantCommand(()->TurretSubsystem.INSTANCE.setToFollow(true))
                );

        Gamepads.gamepad2().x()
                .whenBecomesTrue(
                        shootSequence
                );

        Gamepads.gamepad2().a()
                .whenTrue(
                        new ParallelGroup(
                                new InstantCommand(()-> shootSequence.cancel()),
                                ShooterSubsystem.INSTANCE.StopSpeed(),
                                new InstantCommand(()-> IntakeSubSystem.INSTANCE.IntakeMotor.setPower(0)),
                                new InstantCommand(()-> IntakeSubSystem.INSTANCE.TransferMotor.setPower(0))
                        ));


        Gamepads.gamepad2().leftBumper().toggleOnBecomesTrue()
                .whenBecomesTrue(
                        new InstantCommand(()-> IntakeSubSystem.INSTANCE.IntakeMotor.setPower(RobotMap.INTAKE_MOTOR_POWER))
                )
                .whenBecomesTrue(
                        new InstantCommand(()-> IntakeSubSystem.INSTANCE.TransferMotor.setPower(-RobotMap.TRANSMISSION_MOTOR_POWER))
                )
                .whenBecomesFalse(
                        new InstantCommand(()-> IntakeSubSystem.INSTANCE.IntakeMotor.setPower(0))
                )
                .whenBecomesFalse(
                        new InstantCommand(()-> IntakeSubSystem.INSTANCE.TransferMotor.setPower(0))
                );
    }
}
