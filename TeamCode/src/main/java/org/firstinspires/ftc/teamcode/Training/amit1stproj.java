package org.firstinspires.ftc.teamcode.Training;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "amit1stproj")

public class amit1stproj extends LinearOpMode {
    /**
     * @throws InterruptedException
     */
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor intake;
        ElapsedTime timer;

        intake = hardwareMap.get(DcMotor.class, "3C");
        timer = new ElapsedTime();

        waitForStart();
        timer.reset();
        intake.setPower(1);
        while (opModeIsActive()) {
            if (timer.seconds() >= 10) {
                intake.setPower(0);
                telemetry.addData("finshed: ", timer.seconds());
                telemetry.addLine("banana man");
            } else {
                telemetry.addData("Seconds passed: ", timer.seconds());
            }
            telemetry.update();

        }
    }
}
