package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubSystem;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.TurretSubsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.extensions.pedro.PedroDriverControlled;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.driving.DriverControlledCommand;


@TeleOp (name = "BitCode")
public class Bit extends NextFTCOpMode {

    public Bit(){
        addComponents(
                new SubsystemComponent(
                        ShooterSubsystem.INSTANCE,
                        IntakeSubSystem.INSTANCE,
                        TurretSubsystem.INSTANCE
                ),

                new PedroComponent(Constants::createFollower),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }

    public Command BitCommand(){
        return new SequentialGroup(
                ShooterSubsystem.INSTANCE.move(1),
                ShooterSubsystem.INSTANCE.ServoAim(0.6), // CLOSE
                ShooterSubsystem.INSTANCE.ServoAim(0.45), // MID
                ShooterSubsystem.INSTANCE.ServoAim(0.65), // FAR
                IntakeSubSystem.INSTANCE.IntakeFullyTransfer(),
                TurretSubsystem.INSTANCE.ResetAngle(),
                TurretSubsystem.INSTANCE.LimelightMove()
        );
    }

    @Override
    public void onStartButtonPressed() {

        DriverControlledCommand driverControlled = new PedroDriverControlled(
                Gamepads.gamepad1().leftStickY(),
                Gamepads.gamepad1().leftStickX(),
                Gamepads.gamepad1().rightStickX().negate(),
                false
        );
        driverControlled.schedule();

        Gamepads.gamepad1().a()
                .whenTrue(BitCommand());
    }

}
