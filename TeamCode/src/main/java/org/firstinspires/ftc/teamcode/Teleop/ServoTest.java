package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;

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

    @Override
    public void onStartButtonPressed() {
        Gamepads.gamepad1().a()
                .whenBecomesTrue(ShooterSubsystem.INSTANCE.ServoAim(0));

        Gamepads.gamepad1().b()
                .whenBecomesTrue(ShooterSubsystem.INSTANCE.ServoAim(0.2));

        Gamepads.gamepad1().x()
                .whenBecomesTrue(ShooterSubsystem.INSTANCE.ServoAim(0.8));

        Gamepads.gamepad1().y()
                .whenBecomesTrue(ShooterSubsystem.INSTANCE.ServoAim(1));
    }
}
