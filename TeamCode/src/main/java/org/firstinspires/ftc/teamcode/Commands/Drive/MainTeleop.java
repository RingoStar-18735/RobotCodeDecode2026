package org.firstinspires.ftc.teamcode.Commands.Drive;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystemUpdated;
import org.firstinspires.ftc.teamcode.Subsystems.TurretSubsystem;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@TeleOp(name = "NextFTC TeleOp Program Java")
    public class MainTeleop extends NextFTCOpMode {
    public MainTeleop() {
        addComponents(
                new SubsystemComponent(TurretSubsystem.INSTANCE, DriveSubsystemUpdated.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE

        );
    }

    @Override
    public void onStartButtonPressed() {
        Command driverControlled = DriveSubsystemUpdated.INSTANCE.driverControlled;
        driverControlled.schedule();

        Gamepads.gamepad2().a()
                .whenBecomesTrue(TurretSubsystem.INSTANCE.MoveAngle(0));
    }


}

