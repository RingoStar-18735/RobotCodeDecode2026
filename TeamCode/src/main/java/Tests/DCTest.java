package Tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.TeleOp_Hellen.IntakeTransportSubsystem;

import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@TeleOp(name = "DCTest")
public class DCTest extends NextFTCOpMode {
    public DCTest() {
        addComponents(
                new SubsystemComponent(IntakeTransportSubsystem.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }

    @Override
    public void onStartButtonPressed(){
        Gamepads.gamepad1().b()
                .whenBecomesTrue(IntakeTransportSubsystem.INSTANCE.DCmovement(1));
    }
}