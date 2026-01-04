package Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

@TeleOp
public class AprilTagTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        AprilTagProcessor niliAprilTagProcessor;
        niliAprilTagProcessor = AprilTagProcessor.easyCreateWithDefaults();

        VisionPortal niliVisionPortal;
        niliVisionPortal = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "Webcam"), niliAprilTagProcessor);

        List<AprilTagDetection> niliAprilTagDetections;
        int niliAprilTagIdCode;

        while (opModeInInit()) {
            niliAprilTagDetections = niliAprilTagProcessor.getDetections();

            for (AprilTagDetection niliAprilTagDetection : niliAprilTagDetections) {
                niliAprilTagDetection
            }
        }

        waitForStart();

    }
}
