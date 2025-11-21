package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@TeleOp(name = "TeleOpProgram")
    public class TeleOpProgram extends NextFTCOpMode {
        public TeleOpProgram() {
            addComponents(
                    new SubsystemComponent(IntakeSubsystem.INSTANCE),
                    BulkReadComponent.INSTANCE,
                    BindingsComponent.INSTANCE
            );
        }

    @Override
    public void onStartButtonPressed() {
        Command driverControlled = DriveSubsystem.INSTANCE.driverControlled;
        driverControlled.schedule();

        Gamepads.gamepad1().a().whenBecomesTrue(
                IntakeSubsystem.INSTANCE.intakePow(1)
        );

        Gamepads.gamepad1().b().whenBecomesTrue(
                IntakeSubsystem.INSTANCE.intakePow(0)
        );

        }

}

