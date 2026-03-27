package org.firstinspires.ftc.teamcode.Teleop;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotMap;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubSystem;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.TurretSubsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.extensions.pedro.PedroDriverControlled;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.driving.DriverControlledCommand;

@TeleOp
public class VoltTest extends NextFTCOpMode {
    Follower follower;
    Pose RobotPose = new Pose(0, 0, 0);
    double distance = 0;
//    List<String> a = new ArrayList<String>();
//    AllianceType allianceType = AllianceType.BLUE;
//    Pose NewTargetPose = FieldMap.BLUE_TARGET_POS;

    public VoltTest(){
        addComponents(
                new SubsystemComponent(
                        DriveSubsystem.INSTANCE
//                        ShooterSubsystem.INSTANCE,
//                        IntakeSubSystem.INSTANCE
//                        TurretSubsystem.INSTANCE
                ),
                new PedroComponent(Constants::createFollower),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
        hasStartedMatch = false;
    }

//    public Command Shoot() {
//        return new LambdaCommand()
//                .setStart(() -> {
//                    telemetry.addLine("SHOOT COMMAND STARTED");
//                    telemetry.addData("vel: ", -ShooterSubsystem.INSTANCE.Shooter.getVelocity());
//                    telemetry.addData("Speed: ", ShooterSubsystem.INSTANCE.ShooterSpeed - 300);
//                })
//                .setUpdate(() -> {
//                    if (Math.abs(ShooterSubsystem.INSTANCE.Shooter.getVelocity()) >= ShooterSubsystem.INSTANCE.ShooterSpeed - 300) {
//                        telemetry.addData("אני2: ", -ShooterSubsystem.INSTANCE.Shooter.getVelocity());
//                        IntakeSubSystem.INSTANCE.IntakeMotor.setPower(RobotMap.INTAKE_MOTOR_POWER);
//                        IntakeSubSystem.INSTANCE.TransferMotor.setPower(RobotMap.TRANSMISSION_MOTOR_POWER);
//
//                    } else {
//                        IntakeSubSystem.INSTANCE.IntakeMotor.setPower(0);
//                        IntakeSubSystem.INSTANCE.TransferMotor.setPower(0);
//                    }
//                })
//                .setIsDone(()-> false)
//                .requires(IntakeSubSystem.INSTANCE);
//    }
//
//    Command shootSequence = new SequentialGroup(
//            new ParallelDeadlineGroup(
//                    new Delay(2),
//                    ShooterSubsystem.INSTANCE.RunFullSpeed(() ->
//                            Math.sqrt(
//                                    Math.pow(NewTargetPose.getX() - RobotPose.getX(), 2) +
//                                            Math.pow(NewTargetPose.getY() - RobotPose.getY(), 2)
//                            )
//                    )
//            ),
//            Shoot()
//    );

    @Override
    public void onInit() {
//        follower =  Constants.createFollower(hardwareMap);
//        follower.setPose(RobotBank.LastAutoPos);
//        new InstantCommand(ShooterSubsystem.INSTANCE.StopSpeed());
//        allianceType = RobotBank.Alliance;
//        if (RobotBank.Alliance == AllianceType.BLUE) {
//            NewTargetPose = FieldMap.BLUE_TARGET_POS;
//        } else if (RobotBank.Alliance == AllianceType.RED) {
//            NewTargetPose = FieldMap.RED_TARGET_POS;
//        }
//        TurretSubsystem.INSTANCE.setOffset(RobotBank.Offset);
        new InstantCommand(()-> IntakeSubSystem.INSTANCE.IntakeMotor.setPower(0));
        new InstantCommand(()-> IntakeSubSystem.INSTANCE.TransferMotor.setPower(0));
        new InstantCommand(ShooterSubsystem.INSTANCE.StopSpeed());

    }

    boolean hasStartedMatch;
    @Override
    public void onUpdate(){
//        follower.update();
        RobotPose = follower.getPose();

//        if (hasStartedMatch) {
//            TurretSubsystem.INSTANCE.FollowPoint(NewTargetPose, RobotPose).schedule();
//        }
    }

    @Override
    public void onStartButtonPressed() {
        hasStartedMatch = true;
//        ShooterSubsystem.INSTANCE.ServoMoveByField(() ->
//                Math.sqrt(
//                        Math.pow(NewTargetPose.getX() - RobotPose.getX(), 2) +
//                                Math.pow(NewTargetPose.getY() - RobotPose.getY(), 2)
//                )
//        ).schedule();

        TurretSubsystem.INSTANCE.setReset(true);
        ShooterSubsystem.INSTANCE.StopSpeed();

        DriverControlledCommand driverControlled = new PedroDriverControlled(
                Gamepads.gamepad1().leftStickY(),
                Gamepads.gamepad1().leftStickX(),
                Gamepads.gamepad1().rightStickX().negate(),
                false
        );
        driverControlled.schedule();

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

//        Gamepads.gamepad2().x()
//                .whenBecomesTrue(
//                        shootSequence
//                );
//
//        Gamepads.gamepad2().a()
//                .whenTrue(
//                        new ParallelGroup(
//                                new InstantCommand(()-> shootSequence.cancel()),
//                                ShooterSubsystem.INSTANCE.StopSpeed(),
//                                new InstantCommand(()-> IntakeSubSystem.INSTANCE.IntakeMotor.setPower(0)),
//                                new InstantCommand(()-> IntakeSubSystem.INSTANCE.TransferMotor.setPower(0))
//                        ));

    }

}
