package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubSystem;
import org.firstinspires.ftc.teamcode.Subsystems.LimelightApril;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.TurretSubsystem;
import org.firstinspires.ftc.teamcode.TeleOp_Hellen.DriveSubsystem;
import org.firstinspires.ftc.teamcode.TeleOp_Hellen.IntakeTransportSubsystem;
import org.firstinspires.ftc.teamcode.TeleOp_Hellen.ServoSubsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@TeleOp (name = "ServoTest")
public class ServoTest extends NextFTCOpMode {
    public ServoTest() {
        addComponents(
                    new SubsystemComponent(
                        ServoSubsystem.INSTANCE),

        BulkReadComponent.INSTANCE,
        BindingsComponent.INSTANCE
        );
    }

    @Override
    public void onStartButtonPressed() {
        Gamepads.gamepad1().a()
                .whenBecomesTrue(ServoSubsystem.INSTANCE.ServoMovement(1));

        Gamepads.gamepad1().b()
                .whenBecomesTrue(ServoSubsystem.INSTANCE.ServoMovement(0.5));

        Gamepads.gamepad1().x()
                .whenBecomesTrue(ServoSubsystem.INSTANCE.ServoMovement(0));
    }
}
