package Tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.ServoSubsystem;

import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@TeleOp (name = "ServoTest")
public class ServoTest  extends NextFTCOpMode {
    public ServoTest(){
       addComponents(
               new SubsystemComponent((Subsystem) ServoSubsystem.INSTANCE),
                       BulkReadComponent.INSTANCE,
                       BindingsComponent.INSTANCE
       );
    }

    @Override
    public void onStartButtonPressed(){
        Gamepads.gamepad2().b()
                .whenBecomesTrue(ServoSubsystem.INSTANCE.ServoMovement(0));

        Gamepads.gamepad2().dpadUp()
                .whenBecomesTrue(ServoSubsystem.INSTANCE.ServoMovement(0.2));

        Gamepads.gamepad2().dpadRight()
                .whenBecomesTrue(ServoSubsystem.INSTANCE.ServoMovement(0.4));

        Gamepads.gamepad2().dpadDown()
                .whenBecomesTrue(ServoSubsystem.INSTANCE.ServoMovement(0.6));

        Gamepads.gamepad2().dpadLeft()
                .whenBecomesTrue(ServoSubsystem.INSTANCE.ServoMovement(0.8));

        Gamepads.gamepad2().a()
                .whenBecomesTrue(ServoSubsystem.INSTANCE.ServoMovement(1));
    }
}
