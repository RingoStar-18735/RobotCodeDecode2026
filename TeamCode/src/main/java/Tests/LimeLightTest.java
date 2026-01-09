package Tests;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp (name = "LimeLightTest")
public class LimeLightTest extends LinearOpMode {
    private Limelight3A limelight;

    @Override
    public void runOpMode() throws InterruptedException {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100); // This sets how often we ask Limelight for data (100 times per second)
        limelight.start(); // This tells Limelight to start looking!

        limelight.pipelineSwitch(0); // Switch to pipeline number 0


        while (opModeInInit()) {
            LLResult result = limelight.getLatestResult();

            if (result != null && result.isValid()) {
                double tx = result.getTx(); // How far left or right the target is (degrees)
                double ty = result.getTy(); // How far up or down the target is (degrees)
                double ta = result.getTa(); // How big the target looks (0%-100% of the image)

                telemetry.addData("Target X:", tx);
                telemetry.addData("Target Y:", ty);
                telemetry.addData("Target Area:", ta);
            } else {
                telemetry.addData("Limelight", "No Targets");
            }

            telemetry.update();
        }
    }
}
//public class LimeLightTest extends LinearOpMode {
//
//    private Limelight3A limelight;
//
//    @Override
//
//    public void runOpMode() throws InterruptedException
//    {
//        limelight = hardwareMap.get(Limelight3A.class, "limelight");
//
//        telemetry.setMsTransmissionInterval(11);
//
//        limelight.pipelineSwitch(0);
//
//        /*
//         * Starts polling for data.
//         */
//        limelight.start();
//
//        while (opModeIsActive()) {
//            LLResult result = limelight.getLatestResult();
//            if (result != null) {
//                if (result.isValid()) {
//                    Pose3D botpose = result.getBotpose();
//                    telemetry.addData("tx", result.getTx());
//                    telemetry.addData("ty", result.getTy());
//                    telemetry.addData("Botpose", botpose.toString());
//                }
//            }
//        }
//    }
//}
