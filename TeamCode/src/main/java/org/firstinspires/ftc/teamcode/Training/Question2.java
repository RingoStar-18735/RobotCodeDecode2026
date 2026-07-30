package org.firstinspires.ftc.teamcode.Training;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
@Autonomous (name = "Training 2")
public class Question2 extends LinearOpMode {

    /**
     * @throws InterruptedException
     */
    private DigitalChannel magnet;
    private DcMotor intake;

    @Override
    public void runOpMode() throws InterruptedException {
        magnet = hardwareMap.get(DigitalChannel.class , "magnet");
        intake = hardwareMap.get(DcMotor.class, "3C");

        magnet.setMode(DigitalChannel.Mode.INPUT) ;
        magnet.getState();
        telemetry.addLine("hello people");



        waitForStart();

        while (opModeIsActive()){
            telemetry.addData("magnet state: ", magnet.getState());
            if (magnet.getState()){
                intake.setPower(0.4);
            }
            else {intake.setPower(0);

            }
            telemetry.update();
        }
    }
}
