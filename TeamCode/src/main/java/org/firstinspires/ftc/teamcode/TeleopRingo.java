package org.firstinspires.ftc.teamcode;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubSystem;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.TurretSubsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import dev.nextftc.bindings.Button;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.ParallelDeadlineGroup;
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



    public TeleopRingo(){
        addComponents(
                new SubsystemComponent(
                        ShooterSubsystem.INSTANCE,
                        IntakeSubSystem.INSTANCE,
                        TurretSubsystem.INSTANCE
                        ),

                new PedroComponent(Constants::createFollower),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }

    Command Shoot = new SequentialGroup(
            ShooterSubsystem.INSTANCE.RunFullSpeed(),
            IntakeSubSystem.INSTANCE.IntakeFullyTransfer().setInterruptible(Math.abs(RobotMap.SHOOTER_SPEED) <= RobotMap.SHOOTER_SPEED_RANGE),
            IntakeSubSystem.INSTANCE.IntakeStop().afterTime(RobotMap.SHOOT_TIME)
    );



    Command FinalShoot = new SequentialGroup(
            IntakeSubSystem.INSTANCE.ReversedIntake(),
            IntakeSubSystem.INSTANCE.IntakeFullyTransfer(),
            ShooterSubsystem.INSTANCE.RunFullSpeed(),
            IntakeSubSystem.INSTANCE.IntakeStop()
    );


    Command ShootFromFar =
            new SequentialGroup(
                    new ParallelDeadlineGroup(
                            ShooterSubsystem.INSTANCE.RunFullSpeed(),
                            ShooterSubsystem.INSTANCE.ServoAim(RobotMap.SERVO_MOVE_FAR)
                    ),
                    Shoot.afterTime(RobotMap.SHOOT_TIME),
                    Shoot.afterTime(RobotMap.SHOOT_TIME),
                    FinalShoot.afterTime(RobotMap.SHOOT_TIME)
    );

    Command ShootFromMid =
            new SequentialGroup(
                    new ParallelDeadlineGroup(
                            ShooterSubsystem.INSTANCE.RunFullSpeed(),
                            ShooterSubsystem.INSTANCE.ServoAim(RobotMap.SERVO_MOVE_MID)
                    ),
                    Shoot.afterTime(RobotMap.SHOOT_TIME),
                    Shoot.afterTime(RobotMap.SHOOT_TIME),
                    FinalShoot.afterTime(RobotMap.SHOOT_TIME)
    );

    Command ShootFromClose =
            new SequentialGroup(
                    new ParallelDeadlineGroup(
                            ShooterSubsystem.INSTANCE.RunFullSpeed(),
                            ShooterSubsystem.INSTANCE.ServoAim(RobotMap.SERVO_MOVE_CLOSE)
                    ),
                    Shoot,
                    Shoot,
                    FinalShoot
    );


    private Button LBT;
    private Button GP2A;

    @Override
    public void onInit() {
        //TurretSubsystem.INSTANCE.ResetAngle().schedule();
        ShooterSubsystem.INSTANCE.StopSpeed().schedule();
        IntakeSubSystem.INSTANCE.IntakeStop().schedule();

        LBT = Gamepads.gamepad2().leftBumper().toggleOnBecomesTrue();
        GP2A = Gamepads.gamepad2().a();
    }

    @Override
    public void onUpdate() {
        telemetry.addData("ShootFinished: ", Shoot.isDone());
        //TurretSubsystem.INSTANCE.LimelightMove().schedule();



    }

    @Override
    public void onStartButtonPressed() {
        Follower follower =  Constants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose(0,0, 135));

        DriverControlledCommand driverControlled = new PedroDriverControlled(
                Gamepads.gamepad1().leftStickY(),
                Gamepads.gamepad1().leftStickX(),
                Gamepads.gamepad1().rightStickX().negate(),
                false
        );
        driverControlled.schedule();



        Gamepads.gamepad2().y()
                .whenBecomesTrue(
                        new SequentialGroup(
                                new InstantCommand(() ->
                                        ShooterSubsystem.INSTANCE.ShooterStopped = false
                                ),
                                ShootFromFar
                        )
                );


        Gamepads.gamepad2().x()
                .whenBecomesTrue(
                        new SequentialGroup(
                                new InstantCommand(() ->
                                        ShooterSubsystem.INSTANCE.ShooterStopped = false
                                ),
                                ShootFromMid
                        )
                );

        Gamepads.gamepad2().b()
                .whenBecomesTrue(
                        new SequentialGroup(
                                new InstantCommand(() ->
                                        ShooterSubsystem.INSTANCE.ShooterStopped = false
                                ),
                                ShootFromClose
                        )
                );

        GP2A.whenBecomesTrue(IntakeSubSystem.INSTANCE.ReversedIntake())
                .whenBecomesTrue(IntakeSubSystem.INSTANCE.IntakeStop().afterTime(RobotMap.INTAKE_REVERSED_TIME))
                .whenBecomesTrue( ShooterSubsystem.INSTANCE.StopSpeed());



        LBT.whenBecomesTrue(
                        IntakeSubSystem.INSTANCE.IntakeFullyNotTransfer()
                )
                .whenBecomesFalse(
                        IntakeSubSystem.INSTANCE.IntakeStop()
                );


//        Gamepads.gamepad1().rightTrigger().greaterThan(0.1).whenTrue(
//                Shoot
//        );



//        Gamepads.gamepad1().leftBumper().toggleOnBecomesTrue()
//                .whenBecomesTrue(TurretSubsystem.INSTANCE.MoveAngle(90))
//                .whenBecomesFalse(TurretSubsystem.INSTANCE.MoveAngle(-90));


//        Gamepads.gamepad1().dpadLeft().whenTrue(
//                new SequentialGroup(
//                        ShooterSubsystem.INSTANCE.RunFullSpeed(),
//                        ShooterSubsystem.INSTANCE.StopSpeed(),
//                        ShooterSubsystem.INSTANCE.RunFullSpeed()
//                )
//        );



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
