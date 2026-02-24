package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SequentialGroupFixed;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubSystem;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;

import dev.nextftc.core.commands.Command;
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

    public Command CC() {
        return new SequentialGroupFixed(
                IntakeSubSystem.INSTANCE.IntakePower(1),
                //new Delay(100),
                IntakeSubSystem.INSTANCE.Transfer(-1),
                //new Delay(100),
                ShooterSubsystem.INSTANCE.RunFullSpeed(),
                //new Delay(100),
                ShooterSubsystem.INSTANCE.ServoAim(0.65), // FAR
                //new Delay(100),
                ShooterSubsystem.INSTANCE.ServoAim(0.45), // MID
                //new Delay(100),
                ShooterSubsystem.INSTANCE.ServoAim(0.5) // CLOSE
        );
    }

    @Override
    public void onStartButtonPressed() {
        Gamepads.gamepad1().a()
                .whenTrue(CC());
    }

}
