package Tests;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.pedropathing.ftc.localization.Encoder;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

public class ThreeWheelLocalizer {



    public ThreeWheelLocalizer(HardwareMap map , Pose setStartPose){
         Pose leftEncoderPose = new Pose(-10.5 / 25.4 - 0.1, 164.4 / 25.4, 0);
        Pose rightEncoderPose = new Pose(10.5 / 25.4 + 0.1, -164.4 / 25.4, 180);
         Pose strafeEncoderPose = new Pose(-107.9/ 25.4 + 0.25, -1.1/ 25.4 -0.23 , Math.toRadians(90));
        hardwareMap = map;

         IMU imu = hardwareMap.get(IMU.class, "imu");
        imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP ,
                RevHubOrientationOnRobot.UsbFacingDirection.LEFT)));

        Encoder leftEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "leftFront"));
        Encoder rightEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "rightFront"));
        Encoder strafeEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "strafe"));


        leftEncoder.setDirection(Encoder.REVERSE);
        rightEncoder.setDirection(Encoder.FORWARD);
        strafeEncoder.setDirection(Encoder.FORWARD);





    }


}
