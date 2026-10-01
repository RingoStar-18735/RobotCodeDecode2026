package org.firstinspires.ftc.teamcode.Training;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous ( name = "amit2ndproj")
public class amit2ndproj extends LinearOpMode {
    /**
     * @throws InterruptedException
     */
    DcMotor fr;
    DcMotor br;
    DcMotor fl;
    DcMotor bl;
    RevColorSensorV3 colorSensor;


    @Override
    public void runOpMode() throws InterruptedException {
        fl = hardwareMap.get(DcMotor.class, "2E");
        fr = hardwareMap.get(DcMotor.class, "0E");
        bl = hardwareMap.get(DcMotor.class, "3E");
        br = hardwareMap.get(DcMotor.class, "1E");
        colorSensor = hardwareMap.get(RevColorSensorV3.class, "csr");
        fr.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();
        boolean hasTouched = false;
        while(opModeIsActive())
        {
            telemetry.addData("Distance: ", colorSensor.getDistance(DistanceUnit.CM));
            telemetry.addData("hasTouched: ", hasTouched);
//            if(colorSensor.getDistance(DistanceUnit.CM) <= 10) hasTouched = true;
//            if(!hasTouched) DrivePower(0.2);
//            else DrivePower(0);
            double P = 0.5;
            DrivePower(colorSensor.getDistance(DistanceUnit.METER) * P);
            telemetry.update();
        }
    }


    private void DrivePower (double pow){ //PaPow
        fr.setPower(pow);
        br.setPower(pow);
        fl.setPower(pow);
        bl.setPower(pow);
    }



}
