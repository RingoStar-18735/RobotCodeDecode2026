package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.hardware.impl.MotorEx;

@TeleOp (name = "MotorCheck")
public class motorcheck extends NextFTCOpMode {
    public MotorEx Motor1 = new MotorEx("0C");

    @Override
    public void onUpdate() {
        if (Motor1.getVelocity() > 1800) {
            Motor1.setPower(0);
        }
        telemetry.addData("getVelocity", Motor1.getVelocity());
        telemetry.update();
    }
    @Override
    public void onStartButtonPressed() {
        Motor1.setPower(1);
    }

}
