package org.firstinspires.ftc.teamcode.TeleOp_Hellen;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@Configurable
@TeleOp(name = "A TeleopLL")
public class TeleopLLTest extends NextFTCOpMode {
//    List<String> a = new ArrayList<String>();

    public boolean hasMatchStarted = false;

    public TeleopLLTest() {
        addComponents(
                new SubsystemComponent(
                        TurretSubsystemLL.INSTANCE,
                        LLSubsystem.INSTANCE
                ),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }

    @Override
    public void onInit() {
        TurretSubsystemLL.INSTANCE.ResetEncoder();
    }

    @Override
    public void onUpdate() {
        if (hasMatchStarted) {
            //TurretSubsystemLL.INSTANCE.FollowPointLL();
            telemetry.addData("motor pow:", TurretSubsystemLL.INSTANCE.turretLL.getPower());
            telemetry.addData("Motor pos: ", TurretSubsystemLL.INSTANCE.getAngle());
            telemetry.addData("Motor Target: ", TurretSubsystemLL.INSTANCE.getTarget());
            telemetry.update();
            new InstantCommand(()-> TurretSubsystemLL.INSTANCE.moveDegrees(LLSubsystem.INSTANCE.getTx())).schedule();

        }
    }

    @Override
    public void onStartButtonPressed() {
        hasMatchStarted = true;
//        Gamepads.gamepad1().a()
//                .whenBecomesTrue(
//                        new InstantCommand(()-> TurretSubsystemLL.INSTANCE.moveToSetDegree(0))
//                );
//
//        Gamepads.gamepad1().b()
//                .whenBecomesTrue(
//                        new InstantCommand(()-> TurretSubsystemLL.INSTANCE.moveToSetDegree(90))
//                );
//
//        Gamepads.gamepad1().y()
//                .whenBecomesTrue(
//                        new InstantCommand(()-> TurretSubsystemLL.INSTANCE.moveToSetDegree(180))
//                );
//
//        Gamepads.gamepad1().x()
//                .whenBecomesTrue(
//                        new InstantCommand(()-> TurretSubsystemLL.INSTANCE.moveToSetDegree(270))
//                );
    }


}
