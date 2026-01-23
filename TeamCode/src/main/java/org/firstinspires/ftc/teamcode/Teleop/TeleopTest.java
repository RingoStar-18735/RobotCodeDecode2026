package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubSystem;
import org.firstinspires.ftc.teamcode.Subsystems.TurretSubsystem;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;


@TeleOp (name = "Teleop")
public class TeleopTest extends NextFTCOpMode {
    public TeleopTest() {
        addComponents(
                new SubsystemComponent(launch.INSTANCE, CRTest.INSTANCE, IntakeSubSystem.INSTANCE, TurretSubsystem.INSTANCE, drive.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }

    @Override
    public void onStartButtonPressed() {

        //turret
//        Gamepads.gamepad1().a()
//                .whenBecomesTrue(turretSubsystem.INSTANCE.turret_move(0));

        //Launch
        Gamepads.gamepad2().dpadUp()
                .whenBecomesTrue(launch.INSTANCE.launchPower(1));

        Gamepads.gamepad2().dpadRight()
                .whenBecomesTrue(launch.INSTANCE.launchPower(0.8));

        Gamepads.gamepad2().dpadLeft()
                .whenBecomesTrue(launch.INSTANCE.launchPower(0.6));

        Gamepads.gamepad2().dpadDown()
                .whenBecomesTrue(launch.INSTANCE.launchPower(0));


    }

    @Override
    public void onUpdate() {
        //Intake
        IntakeSubSystem.INSTANCE.IntakePower(gamepad2.right_stick_y).schedule();
        telemetry.addData("val: ", gamepad2.right_stick_y);
        telemetry.update();


        //CR_Servo
        CRTest.INSTANCE.CR_move_right(gamepad2.left_stick_y).schedule();
        CRTest.INSTANCE.CR_move_left(gamepad2.left_stick_y).schedule();

        //Drive
        Command driverControlled = drive.INSTANCE.driverControlled;
        driverControlled.schedule();

        //turret
        TurretSubsystem.INSTANCE.turret_move(Gamepads.gamepad1().rightTrigger());
        telemetry.addData("val_2: ", gamepad1.right_trigger);
        telemetry.update();

    }








    /*public TurretCommand() {
            requires(turretSubsystem);
            setInterrptuptible(true); // this is the default, so you don't need to specify
        }

    private void requires() {
    }

    private void setInterrptuptible(boolean true) {
    }

    @Override
        public void update() {
            // executed on every update of the command
        turretSubsystem.INSTANCE.turret_move(1);
        }

    }*/

}
