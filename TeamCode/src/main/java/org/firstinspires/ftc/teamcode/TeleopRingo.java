package org.firstinspires.ftc.teamcode;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubSystem;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.ParallelDeadlineGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.PerpetualCommand;
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
                        IntakeSubSystem.INSTANCE),
                new PedroComponent(Constants::createFollower),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }

    Command Shoot = new SequentialGroup(
            ShooterSubsystem.INSTANCE.RunFullSpeed(),
            IntakeSubSystem.INSTANCE.IntakeFully().endAfter(RobotMap.INTAKE_FULLY_TIME)
    );


    Command ShootFromFar = new PerpetualCommand(
            new SequentialGroup(
                    new ParallelDeadlineGroup(
                            ShooterSubsystem.INSTANCE.RunFullSpeed(),
                            ShooterSubsystem.INSTANCE.ServoAim(RobotMap.SERVO_MOVE_FAR)
                    ),
                    Shoot,
                    Shoot,
                    Shoot
            )
    );

    Command ShootFromMid = new PerpetualCommand(
            new SequentialGroup(
                    new ParallelDeadlineGroup(
                            ShooterSubsystem.INSTANCE.RunFullSpeed(),
                            ShooterSubsystem.INSTANCE.ServoAim(RobotMap.SERVO_MOVE_MID)
                    ),
                    Shoot,
                    Shoot,
                    Shoot
            )
    );

    Command ShootFromClose = new PerpetualCommand(
            new SequentialGroup(
                    new ParallelDeadlineGroup(
                            ShooterSubsystem.INSTANCE.RunFullSpeed(),
                            ShooterSubsystem.INSTANCE.ServoAim(RobotMap.SERVO_MOVE_CLOSE)
                    ),
                    Shoot,
                    Shoot,
                    Shoot
            )
    );





    @Override
    public void onStartButtonPressed() {

        Follower follower =  Constants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose(0,0, 90));


        DriverControlledCommand driverControlled = new PedroDriverControlled(
                Gamepads.gamepad1().leftStickY(),
                Gamepads.gamepad1().leftStickX(),
                Gamepads.gamepad1().rightStickX(),
                false
        );

        Gamepads.gamepad2().leftBumper().toggleOnBecomesTrue()
                        .whenBecomesTrue(IntakeSubSystem.INSTANCE.IntakeFully())
                        .whenBecomesFalse(IntakeSubSystem.INSTANCE.IntakeStop());

        driverControlled.schedule();

        Gamepads.gamepad2().y()
                .whenBecomesTrue(
                        ShootFromFar
                );

        Gamepads.gamepad2().x()
                .whenBecomesTrue(
                        ShootFromMid
                );

        Gamepads.gamepad2().b()
                .whenBecomesTrue(
                        ShootFromClose
                );
        Gamepads.gamepad2().a()
                .whenBecomesTrue(
                        ShooterSubsystem.INSTANCE.StopSpeed()
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
