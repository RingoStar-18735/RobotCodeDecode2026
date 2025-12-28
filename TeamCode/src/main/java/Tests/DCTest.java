package Tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.DCTestSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.TurretSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.launch;
import org.firstinspires.ftc.teamcode.servo_checking;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.powerable.SetPower;

@TeleOp(name = "DCTest")
public class DCTest extends NextFTCOpMode {
    public DCTest() {
        addComponents(
                new SubsystemComponent(DCTestSubsystem.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }

    @Override
    public void onStartButtonPressed(){
        Gamepads.gamepad1().b()
                .whenBecomesTrue(DCTestSubsystem.INSTANCE.DCmovement(1));
    }
}
