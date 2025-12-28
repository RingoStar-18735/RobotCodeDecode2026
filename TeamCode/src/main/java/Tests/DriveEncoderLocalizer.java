package Tests;

import com.pedropathing.ftc.localization.Encoder;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
@Autonomous

public class DriveEncoderLocalizer {
    Encoder leftFront , rightFront ,leftBack , rightBack;

    public DriveEncoderLocalizer(HardwareMap hardwareMap, Pose startPose){
        leftFront = new Encoder(hardwareMap.get(DcMotorEx.class,"leftFront"));
        rightFront = new Encoder(hardwareMap.get(DcMotorEx.class,"rightFront"));
        leftBack = new Encoder(hardwareMap.get(DcMotorEx.class,"leftBack"));
        rightBack = new Encoder(hardwareMap.get(DcMotorEx.class,"rightBack"));

        leftFront.setDirection(Encoder.REVERSE);
        rightFront.setDirection(Encoder.FORWARD);
        leftBack.setDirection(Encoder.REVERSE);
        rightBack.setDirection(Encoder.FORWARD);

    }
}
