package Tests;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes.FiducialResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.util.HashMap;
import java.util.List;

@TeleOp (name = "LimeLightTest")
public class LimeLightTest extends LinearOpMode {
    private Limelight3A limelight;
    HashMap<Integer, String> obeliskMap = new HashMap<Integer, String>();
    List<FiducialResult> fiducials;

    @Override
    public void runOpMode() throws InterruptedException {
        obeliskMap.put(22, "PGP");
        obeliskMap.put(23, "PPG");
        obeliskMap.put(21, "GPP");
        obeliskMap.put(20, "Blue goal");
        obeliskMap.put(24, "Red goal");

        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100); // This sets how often we ask Limelight for data (100 times per second)
        limelight.start(); // This tells Limelight to start looking!

        limelight.pipelineSwitch(0); // Switch to pipeline number 0

        while (opModeInInit()) {
            LLResult result = limelight.getLatestResult();
            fiducials = result.getFiducialResults();

            if (!fiducials.isEmpty()) {
                for (FiducialResult fiducial : fiducials) {
                    int id = fiducial.getFiducialId(); // The ID number of the fiducial
                    double x = fiducial.getTargetXDegrees(); // Where it is (left-right)
                    double y = fiducial.getTargetYDegrees(); // Where it is (up-down)
                    double area = fiducial.getTargetArea();

                    telemetry.addData("Target ID:", id);

                    if (obeliskMap.containsKey(id)) {
                        telemetry.addData("Target Pattern:", obeliskMap.get(id));
                    }

                    telemetry.addData("Target X:", x);
                    telemetry.addData("Target Y:", y);
                    telemetry.addData("Target Area:", area);
                }
            } else {
                telemetry.addData("Limelight:", "No Targets");
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
