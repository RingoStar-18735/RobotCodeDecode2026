package Tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcontroller.external.samples.UtilityOctoQuadConfigMenu;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.TurretSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.launch;
import org.firstinspires.ftc.teamcode.servo_checking;


import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.positionable.SetPositions;


@TeleOp (name = "Turret Test ")
public class TeleopTest extends NextFTCOpMode {
    public TeleopTest() {
        addComponents(
                new SubsystemComponent(launch.INSTANCE, servo_checking.INSTANCE/*,TurretSubsystem.INSTANCE, Intake.INSTANCE*/),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE

        );
    }

    @Override
    public void onStartButtonPressed() {


//        Gamepads.gamepad1().dpadUp()
//                .whenBecomesTrue(TurretSubsystem.INSTANCE.MoveAngle(90));
//
//
//        Gamepads.gamepad1().dpadLeft()
//                .whenBecomesTrue(TurretSubsystem.INSTANCE.MoveAngle(0));
//
//
//        Gamepads.gamepad1().dpadDown()
//                .whenBecomesTrue(TurretSubsystem.INSTANCE.MoveAngle(270));
//
//
//        Gamepads.gamepad1().dpadRight()
//                .whenBecomesTrue(TurretSubsystem.INSTANCE.MoveAngle(180));
//
//
//        Gamepads.gamepad1().a()
//                .whenBecomesTrue(TurretSubsystem.INSTANCE.DCPower(1));
//
//
//        Gamepads.gamepad1().b()
//                .whenBecomesTrue(TurretSubsystem.INSTANCE.DcPower(0.8));
//


//        Gamepads.gamepad2().a()
//                .whenBecomesTrue(launch.INSTANCE.setAngleLeft(0))
//                .whenBecomesTrue(launch.INSTANCE.setAngleRight(0));
//
//
//        Gamepads.gamepad2().b()
//                .whenBecomesTrue(launch.INSTANCE.setAngleLeft(0.2))
//                .whenBecomesTrue(launch.INSTANCE.setAngleRight(0.2));


        Gamepads.gamepad2().a().whenBecomesTrue(servo_checking.INSTANCE.pos(0));

        Gamepads.gamepad2().b().whenBecomesTrue(servo_checking.INSTANCE.pos(0.4));


        ;


        Gamepads.gamepad2().x()
                .whenBecomesTrue(launch.INSTANCE.launchPower(0.8));

        Gamepads.gamepad2().y()
                .whenBecomesTrue(launch.INSTANCE.launchPower(1));


        Gamepads.gamepad2().dpadDown()
                .whenBecomesTrue(new InstantCommand(() -> {
                    telemetry.addData("pow: ", launch.INSTANCE.launchMotor.getPower());
                    telemetry.update();
                }));



        // Gamepads.gamepad1().b().whenBecomesTrue(servo_checking.INSTANCE.angle(0.5));



    }
}
