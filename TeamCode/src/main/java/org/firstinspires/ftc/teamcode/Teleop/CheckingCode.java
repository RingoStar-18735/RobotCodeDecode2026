package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubSystem;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.ParallelDeadlineGroup;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@TeleOp (name = "CheckingCode")
public class CheckingCode extends NextFTCOpMode {

    public CheckingCode() {
        addComponents(
                new SubsystemComponent(
                        ShooterSubsystem.INSTANCE,
                        IntakeSubSystem.INSTANCE
                ),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }

    @Override
    public void onInit() {
        IntakeSubSystem.INSTANCE.IntakeStop().schedule();
        ShooterSubsystem.INSTANCE.StopSpeed().schedule();
    }

    public Command CC() {
        return new ParallelDeadlineGroup(
                new Delay(3),
                IntakeSubSystem.INSTANCE.IntakePower(1),
                IntakeSubSystem.INSTANCE.Transfer(-1)
//                ShooterSubsystem.INSTANCE.RunFullSpeedFar()
//                ShooterSubsystem.INSTANCE.ServoAim(0.65), // FAR
//                ShooterSubsystem.INSTANCE.ServoAim(0.45), // MID
//                ShooterSubsystem.INSTANCE.ServoAim(0.5) // CLOSE
        );
    }

    @Override
    public void onStartButtonPressed() {
        Gamepads.gamepad1().a()
                .whenTrue(CC());
    }

}
