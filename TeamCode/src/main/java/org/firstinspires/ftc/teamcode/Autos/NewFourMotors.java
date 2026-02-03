package org.firstinspires.ftc.teamcode.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous
public class NewFourMotors extends LinearOpMode {
    private DcMotor fl;
    private DcMotor fr;
    private DcMotor bl;
    private DcMotor br;
    private ElapsedTime timer = new ElapsedTime();



    // Helper method to keep your code clean
    private void runMotor() {
        fl.setPower(-1.0);
        fr.setPower(1.0);
        bl.setPower(-1.0);
        br.setPower(1.0);
    }

    private void StopMotor() {
        fl.setPower(-0);
        fr.setPower(0);
        bl.setPower(-0);
        br.setPower(0);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        fl = hardwareMap.get(DcMotor.class, "0C");
        fr = hardwareMap.get(DcMotor.class, "1C");
        bl = hardwareMap.get(DcMotor.class, "2C");
        br = hardwareMap.get(DcMotor.class, "3C");

        waitForStart();
        timer.reset();

        while (opModeIsActive()){
            telemetry.addData("h :" , timer.seconds());
            telemetry.update();

            if (timer.seconds() >= 0.3) {
                StopMotor();

            } else {
                runMotor();
            }
        }
    }
}


