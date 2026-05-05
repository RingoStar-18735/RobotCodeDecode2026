package Tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.TurretSubsystem;

import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@TeleOp (name = "Turret Test ")
public class TeleopTest extends NextFTCOpMode {
    public TeleopTest() {
        addComponents(
                new SubsystemComponent(TurretSubsystem.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE

        );
    }

    @Override
    public void onStartButtonPressed() {
        Gamepads.gamepad1().dpadUp()
                .whenBecomesTrue(TurretSubsystem.INSTANCE.MoveAngle(90));
        Gamepads.gamepad1().dpadLeft()
                .whenBecomesTrue(TurretSubsystem.INSTANCE.MoveAngle(0));
        Gamepads.gamepad1().dpadDown()
                .whenBecomesTrue(TurretSubsystem.INSTANCE.MoveAngle(270));
        Gamepads.gamepad1().dpadRight()
                .whenBecomesTrue(TurretSubsystem.INSTANCE.MoveAngle(180));
     /*   Gamepads.gamepad1().a()
                .whenBecomesTrue(TurretSubsystem.INSTANCE.DCPower(1));
        Gamepads.gamepad1().b()
                .whenBecomesTrue(TurretSubsystem.INSTANCE.DCPower(0.8)); */
    }
}
