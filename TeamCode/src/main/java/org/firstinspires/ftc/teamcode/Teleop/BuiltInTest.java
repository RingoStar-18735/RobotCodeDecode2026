package org.firstinspires.ftc.teamcode.Teleop;


import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SequentialGroupFixed;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubSystem;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.TurretSubsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;


@TeleOp (name = "BitCode")
public class BuiltInTest extends NextFTCOpMode {

    public BuiltInTest(){
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

    /*@Override
    public void onInit() {
        AllianceType = RobotBank.Alliance;
        if (RobotBank.Alliance == AllianceType.BLUE) {
            NewTargetPose = FieldMap.BLUE_TARGET_POS;
        } else if (RobotBank.Alliance == AllianceType.RED) {
            NewTargetPose = FieldMap.RED_TARGET_POS;
        }
        ShooterSubsystem.INSTANCE.StopSpeed().schedule();
        IntakeSubSystem.INSTANCE.IntakeStop().schedule();
        TurretSubsystem.INSTANCE.setReset(true);
//        TurretSubsystem.INSTANCE.ResetAngleRight().schedule();
        TurretSubsystem.INSTANCE.setOffset(RobotBank.Offset);
    }*/

    public Command BitCommand(){
        return new SequentialGroupFixed(
//                ShooterSubsystem.INSTANCE.move(0.5),
                ShooterSubsystem.INSTANCE.ServoAim(0.6), // CLOSE
                ShooterSubsystem.INSTANCE.ServoAim(0.45), // MID
                ShooterSubsystem.INSTANCE.ServoAim(0.65)// FAR
//                IntakeSubSystem.INSTANCE.IntakeFullyTransfer(),
//                TurretSubsystem.INSTANCE.ResetAngle()
        );
    }

    @Override
    public void onStartButtonPressed() {

//        DriverControlledCommand driverControlled = new PedroDriverControlled(
//                Gamepads.gamepad1().leftStickY(),
//                Gamepads.gamepad1().leftStickX(),
//                Gamepads.gamepad1().rightStickX().negate(),
//                false
//        );
//        driverControlled.schedule();

         Gamepads.gamepad1().a()
                .whenTrue(BitCommand());

    }

}
