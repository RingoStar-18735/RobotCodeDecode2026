package org.firstinspires.ftc.teamcode.Training;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
@Autonomous (name = "training 1")
public class Question1 extends LinearOpMode {
    /**
     * @throws InterruptedException
     */
    DcMotor intake;
    ElapsedTime timer;

    @Override
    public void runOpMode() throws InterruptedException {
        intake = hardwareMap.get(DcMotor.class, "3C");
        timer = new ElapsedTime() ;

        waitForStart();
        timer.reset();

        while (opModeIsActive()){
            if(timer.seconds() >= 10){
                intake.setPower(0);
                telemetry.addData("finshed: ",timer.seconds());
            }
            else {
                telemetry.addData("Seconds passed: ", timer.seconds());

            }
            telemetry.update();

        }

    }
}
