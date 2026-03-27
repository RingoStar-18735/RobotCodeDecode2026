package org.firstinspires.ftc.teamcode.Teleop;


import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.FieldMap;
import org.firstinspires.ftc.teamcode.RobotMap;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubSystem;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

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


@TeleOp (name = "BitCode")
public class BuiltInTest extends NextFTCOpMode {
    Pose NewTargetPose = FieldMap.BLUE_TARGET_POS;
    Pose RobotPose = new Pose(0, 0, 0);


    public BuiltInTest(){
        addComponents(
                new SubsystemComponent(
                        DriveSubsystem.INSTANCE,
                        IntakeSubSystem.INSTANCE,
                        ShooterSubsystem.INSTANCE
                ),

                new PedroComponent(Constants::createFollower),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
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

                    } else {
                        IntakeSubSystem.INSTANCE.IntakeMotor.setPower(0);
                        IntakeSubSystem.INSTANCE.TransferMotor.setPower(0);
                    }
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
    public void onStartButtonPressed() {
        ShooterSubsystem.INSTANCE.ServoMoveByField(() ->
                Math.sqrt(
                        Math.pow(NewTargetPose.getX() - RobotPose.getX(), 2) +
                                Math.pow(NewTargetPose.getY() - RobotPose.getY(), 2)
                )
        ).schedule();

        DriverControlledCommand driverControlled = new PedroDriverControlled(
                Gamepads.gamepad1().leftStickY(),
                Gamepads.gamepad1().leftStickX(),
                Gamepads.gamepad1().rightStickX().negate(),
                false
        );
        driverControlled.schedule();

        Gamepads.gamepad1().leftBumper().toggleOnBecomesTrue()
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

        Gamepads.gamepad1().x()
                .whenBecomesTrue(
                        shootSequence
                );

        Gamepads.gamepad1().a()
                .whenTrue(
                        new ParallelGroup(
                                new InstantCommand(()-> shootSequence.cancel()),
                                ShooterSubsystem.INSTANCE.StopSpeed(),
                                new InstantCommand(()-> IntakeSubSystem.INSTANCE.IntakeMotor.setPower(0)),
                                new InstantCommand(()-> IntakeSubSystem.INSTANCE.TransferMotor.setPower(0))
                        ));

    }

}
