package org.firstinspires.ftc.teamcode.Teleop;

import org.firstinspires.ftc.teamcode.Subsystems.HDhexMotorsSubsystem;

import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

public class HDhexMotorsTest extends NextFTCOpMode {
    public HDhexMotorsTest() {
        addComponents(
                new SubsystemComponent(
                        HDhexMotorsSubsystem.INSTANCE),

                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }

    @Override
    public void onStartButtonPressed() {
        Gamepads.gamepad1().a()
                .whenBecomesTrue(HDhexMotorsSubsystem.INSTANCE.HHMmovement(0.2));

        Gamepads.gamepad1().b()
                .whenBecomesTrue(HDhexMotorsSubsystem.INSTANCE.HHMmovement(0.4));

        Gamepads.gamepad1().y()
                .whenBecomesTrue(HDhexMotorsSubsystem.INSTANCE.HHMmovement(0.6));

        Gamepads.gamepad1().x()
                .whenBecomesTrue(HDhexMotorsSubsystem.INSTANCE.HHMmovement(0.8));

        Gamepads.gamepad1().dpadUp()
                .whenBecomesTrue(HDhexMotorsSubsystem.INSTANCE.HHMmovement(1));

        Gamepads.gamepad1().dpadDown()
                .whenBecomesTrue(HDhexMotorsSubsystem.INSTANCE.HHMmovement(0));
    }
}
