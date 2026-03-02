package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotBank;
import org.firstinspires.ftc.teamcode.RobotMap;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubSystem;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.TurretSubsystem;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@TeleOp (name = "ServoTest")
public class ServoTest extends NextFTCOpMode {
    public ServoTest(){
        addComponents(
                new SubsystemComponent(
                        ShooterSubsystem.INSTANCE
                ),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );

    }


    Command Shoot = new SequentialGroup(
            ShooterSubsystem.INSTANCE.RunFullSpeed(),
            new InstantCommand(ShooterSubsystem.INSTANCE::getShooterVelocity)
    );



    @Override
    public void onInit() {
//        ShooterSubsystem.INSTANCE.StopSpeed().schedule();
        IntakeSubSystem.INSTANCE.IntakeStop().schedule();
        TurretSubsystem.INSTANCE.setReset(true);
////        TurretSubsystem.INSTANCE.ResetAngleRight().schedule();
        TurretSubsystem.INSTANCE.setOffset(RobotBank.Offset);
        ShooterSubsystem.INSTANCE.StopSpeed().schedule();
    }

    @Override
    public void onStartButtonPressed() {
        Gamepads.gamepad1().a()
                .whenTrue(ShooterSubsystem.INSTANCE.ServoAimCommand(RobotMap.SERVO_MOVE_MID));

//        Gamepads.gamepad1().b()
//                .whenTrue(ShooterSubsystem.INSTANCE.RunFullSpeed());

//        Gamepads.gamepad1().b()
//                .whenTrue(IntakeSubSystem.INSTANCE.IntakePower(1))
//                .whenBecomesTrue(IntakeSubSystem.INSTANCE.Transfer(-1));

       Gamepads.gamepad1().b()
               .whenBecomesTrue(ShooterSubsystem.INSTANCE.ServoAimCommand(0));
//
//        Gamepads.gamepad1().x()
//                .whenBecomesTrue(ShooterSubsystem.INSTANCE.ServoAim(0.8));
//
//        Gamepads.gamepad1().y()
//                .whenBecomesTrue(ShooterSubsystem.INSTANCE.ServoAim(1));
    }
}
