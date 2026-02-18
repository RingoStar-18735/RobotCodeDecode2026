package org.firstinspires.ftc.teamcode;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubSystem;
import org.firstinspires.ftc.teamcode.Subsystems.LimelightApril;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.TurretSubsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import java.util.ArrayList;
import java.util.List;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.ParallelDeadlineGroup;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.extensions.pedro.PedroDriverControlled;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.driving.DriverControlledCommand;

@Configurable
@TeleOp(name = "TeleopRingo")
public class TeleopRingo extends NextFTCOpMode {
    Follower follower;

    List<String> a = new ArrayList<String>();
    public TeleopRingo(){
        addComponents(
                new SubsystemComponent(
                        ShooterSubsystem.INSTANCE,
                        IntakeSubSystem.INSTANCE,
                        TurretSubsystem.INSTANCE,
                        LimelightApril.INSTANCE
                        ),
                new PedroComponent(Constants::createFollower),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
         hasStartedMatch = false;
    }

    public Command FeedWithKickBack(){
        return new SequentialGroupFixed(
                new ParallelDeadlineGroup(
                        new Delay(RobotMap.BACK_INTAKE_LAST_BALL),
                        IntakeSubSystem.INSTANCE.Transfer(-RobotMap.TRANSMISSION_MOTOR__POWER),
                        IntakeSubSystem.INSTANCE.IntakePower(-RobotMap.INTAKE_MOTOR_POWER)
                ),

                new ParallelDeadlineGroup(
                        new Delay(RobotMap.FORWARD_INTAKE_LAST_BALL),
                        IntakeSubSystem.INSTANCE.Transfer(RobotMap.TRANSMISSION_MOTOR__POWER),
                        IntakeSubSystem.INSTANCE.IntakePower(RobotMap.INTAKE_MOTOR_POWER)
                )
        );
    }

    @Override
    public void onInit() {
        TurretSubsystem.INSTANCE.LimelightScan().schedule();
        ShooterSubsystem.INSTANCE.StopSpeed().schedule();
        IntakeSubSystem.INSTANCE.IntakeStop().schedule();
    }

    public Command PrintCommand(String b){
        return new InstantCommand(()->a.add(b));
    }

    boolean hasStartedMatch;
    @Override
    public void onUpdate() {
        telemetry.addData("ShootFinished: ", ShooterSubsystem.INSTANCE.RunFullSpeed().isDone());
        telemetry.addData("FinishedCommands: ",a);
        telemetry.addData("Pos: ",follower.getPose());
        ActiveOpMode.telemetry().addData("PosRobotCalced: ", LimelightApril.INSTANCE.getRobotPos(follower, TurretSubsystem.INSTANCE.getAngle()));

        //follower.setPose(LimelightApril.INSTANCE.getRobotPos(follower, 01.INSTANCE.getAngle()));
        follower.update();


        if (hasStartedMatch)
            TurretSubsystem.INSTANCE.   FollowPoint(FieldMap.RED_TARGET_POS, follower).schedule();
    }

    @Override
    public void onStartButtonPressed() {
        TurretSubsystem.INSTANCE.startMatch();
        hasStartedMatch = true;
        follower =  Constants.createFollower(hardwareMap);
        follower.setStartingPose(TurretSubsystem.INSTANCE.getNewStartingPosition());

        DriverControlledCommand driverControlled = new PedroDriverControlled(
                Gamepads.gamepad1().leftStickX(),
                Gamepads.gamepad1().leftStickY(),
                Gamepads.gamepad1().rightStickX().negate(),
                false
        );
        driverControlled.schedule();


        Gamepads.gamepad1().rightBumper().whenTrue(
                new InstantCommand(()-> follower.setPose(new Pose(follower.getPose().getX(), follower.getPose().getY(), 135)))
        );


        Gamepads.gamepad2().y()
                .whenTrue(
                        new InstantCommand( () -> ShooterSubsystem.INSTANCE.ReverseWheel = false)
                )
                .whenTrue(
                        KeyCommands.Shoot(ShootType.FAR)
                );


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
                                new Delay(2),
                                ShooterSubsystem.INSTANCE.StopSpeed(),
                                IntakeSubSystem.INSTANCE.IntakeStop()

                        ));


        Gamepads.gamepad1().x().whenTrue(
                ShooterSubsystem.INSTANCE.ServoAim(0)
        );


            Gamepads.gamepad2().leftBumper().toggleOnBecomesTrue()
                    .whenBecomesTrue(
                            IntakeSubSystem.INSTANCE.IntakePower(RobotMap.INTAKE_MOTOR_POWER)
                    )
                    .whenBecomesTrue(IntakeSubSystem.INSTANCE.Transfer(-RobotMap.TRANSMISSION_MOTOR__POWER)
                    )
                    .whenBecomesFalse(
                            IntakeSubSystem.INSTANCE.IntakeStop()
                    );


            Gamepads.gamepad2().rightBumper().toggleOnBecomesTrue()
                    .whenBecomesTrue(
                            IntakeSubSystem.INSTANCE.IntakePower(RobotMap.INTAKE_REVERSED_POWER)
                    )
                    .whenBecomesTrue(IntakeSubSystem.INSTANCE.Transfer(-RobotMap.TRANSMISSION_MOTOR__POWER)
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



//        Gamepads.gamepad2().dpadUp()
//                .whenBecomesTrue(ShooterSubsystem.INSTANCE.RunVelocity(RobotMap.SHOOTER_SPEED));
//
//        Gamepads.gamepad2().dpadDown()
//                .whenBecomesTrue(ShooterSubsystem.INSTANCE.RunVelocity(0));
//
//        Gamepads.gamepad2().y()
//                .whenBecomesTrue(ShooterSubsystem.INSTANCE.ServoAim(RobotMap.SERVO_MOVE_FAR));
//
//        Gamepads.gamepad2().x()
//                .whenBecomesTrue(ShooterSubsystem.INSTANCE.ServoAim(RobotMap.SERVO_MOVE_MID));
//
//        Gamepads.gamepad2().a()
//                .whenBecomesTrue(ShooterSubsystem.INSTANCE.ServoAim(RobotMap.SERVO_MOVE_CLOSE));
    }
}
