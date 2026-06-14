package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.PedroComponent;
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
                new PedroComponent(Constants::createFollower),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }


    @Override
    public void onStartButtonPressed() {
        Gamepads.gamepad1().dpadUp()
                .whenBecomesTrue(ShooterSubsystem.INSTANCE.ServoAimCommand(0));

        Gamepads.gamepad1().dpadDown()
                .whenBecomesTrue(ShooterSubsystem.INSTANCE.ServoAimCommand(0.45));
    }

}
