package Tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.TurretSubsystem;
import org.firstinspires.ftc.teamcode.TeleOp_Hellen.DriveSubsystem;
import org.firstinspires.ftc.teamcode.TeleOp_Hellen.IntakeTransportSubsystem;
import org.firstinspires.ftc.teamcode.TeleOp_Hellen.Launch;
import org.firstinspires.ftc.teamcode.TeleOp_Hellen.ServoSubsystem;

import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@TeleOp (name = "Servo And Launch")
public class ServoLaunchTest extends NextFTCOpMode {
    public ServoLaunchTest() {
        addComponents(
                new SubsystemComponent(ServoSubsystem.INSTANCE,
                        Launch.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }

    @Override
    public void onStartButtonPressed() {

        //----------------------------SERVO----------------------------
        Gamepads.gamepad2().b()
                .whenBecomesTrue(ServoSubsystem.INSTANCE.ServoMovement(0));

        Gamepads.gamepad2().dpadUp()
                .whenBecomesTrue(ServoSubsystem.INSTANCE.ServoMovement(0.2));

        Gamepads.gamepad2().dpadRight()
                .whenBecomesTrue(ServoSubsystem.INSTANCE.ServoMovement(0.4));

        Gamepads.gamepad2().dpadDown()
                .whenBecomesTrue(ServoSubsystem.INSTANCE.ServoMovement(0.6));

        //Gamepads.gamepad2().dpadLeft()
        //        .whenBecomesTrue(ServoSubsystem.INSTANCE.ServoMovement(0.8));

        //Gamepads.gamepad2().a()
        //        .whenBecomesTrue(ServoSubsystem.INSTANCE.ServoMovement(1));


        //----------------------------LAUNCH----------------------------
        Gamepads.gamepad1().a()
                .whenBecomesTrue(Launch.INSTANCE.launchPower(1));

        Gamepads.gamepad1().b()
                .whenBecomesTrue(Launch.INSTANCE.launchPower(0.8));

        Gamepads.gamepad1().x()
                .whenBecomesTrue(Launch.INSTANCE.launchPower(0.6));

        Gamepads.gamepad1().y()
                .whenBecomesTrue(Launch.INSTANCE.launchPower(0));

    }
}
