package Tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.TeleOp_Hellen.IntakeTransportSubsystem;
import org.firstinspires.ftc.teamcode.TeleOp_Hellen.DriveSubsystem;
import org.firstinspires.ftc.teamcode.TeleOp_Hellen.ServoSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.TurretSubsystem;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@TeleOp(name = "TeleOpProgram")
    public class TeleOpProgram extends NextFTCOpMode {
        public TeleOpProgram() {
            addComponents(
                    new SubsystemComponent(DriveSubsystem.INSTANCE,
                            IntakeTransportSubsystem.INSTANCE,
                            ServoSubsystem.INSTANCE,
                            TurretSubsystem.INSTANCE),
                    BulkReadComponent.INSTANCE,
                    BindingsComponent.INSTANCE
            );
        }

    @Override
    public void onStartButtonPressed() {

        //----------------------------DRIVE----------------------------
        Command driverControlled = DriveSubsystem.INSTANCE.driverControlled;
        driverControlled.schedule();
        //GAMEPAD1 - STICKS


        //----------------------------INTAKE----------------------------
        Gamepads.gamepad2().x()
                .whenBecomesTrue(IntakeTransportSubsystem.INSTANCE.DCmovement(1));


        //----------------------------ANGLE----------------------------
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


        //----------------------------TURRET----------------------------
       /* Gamepads.gamepad1().dpadUp()
                .whenBecomesTrue(TurretSubsystem.INSTANCE.MoveAngle(90));

        Gamepads.gamepad1().dpadLeft()
                .whenBecomesTrue(TurretSubsystem.INSTANCE.MoveAngle(0));

        Gamepads.gamepad1().dpadDown()
                .whenBecomesTrue(TurretSubsystem.INSTANCE.MoveAngle(270));

        Gamepads.gamepad1().dpadRight()
                .whenBecomesTrue(TurretSubsystem.INSTANCE.MoveAngle(180));


        Gamepads.gamepad1().a()
                .whenBecomesTrue(TurretSubsystem.INSTANCE.DCPower(1));

        Gamepads.gamepad1().b()
                .whenBecomesTrue(TurretSubsystem.INSTANCE.DCPower(0.8));*/
    }


    //GAMEPAD1
    //dpadup, dpadleft, dpadright, dpaddown, a, b, x, y, sticks
    //GAMEPAD2
    //dpadup, dpadleft, dpadright, dpaddown, a, b, x

}

