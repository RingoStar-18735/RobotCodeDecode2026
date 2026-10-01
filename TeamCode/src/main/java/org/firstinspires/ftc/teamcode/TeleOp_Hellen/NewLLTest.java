package org.firstinspires.ftc.teamcode.TeleOp_Hellen;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes.FiducialResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.HashMap;
import java.util.List;

@TeleOp (name = "NewLLTest")
public class NewLLTest extends LinearOpMode {
    private Limelight3A limelight;
    public DcMotor turret;
    double tx;
    double ty;
    HashMap<Integer, String> obeliskMap = new HashMap<Integer, String>();
    List<FiducialResult> fiducials;

    public double getTx() {
        return tx;
    }


    @Override
    public void runOpMode() throws InterruptedException { // AprilTag's IDs
        /*obeliskMap.put(30, "RED SCORING");
        obeliskMap.put(31, "RED SCORING");
        obeliskMap.put(32, "RED SCORING");
        obeliskMap.put(33, "RED SCORING");
        obeliskMap.put(34, "RED SCORING");
        obeliskMap.put(35, "RED SCORING");
        obeliskMap.put(36, "RED SCORING");
        obeliskMap.put(37, "RED SCORING");
        obeliskMap.put(38, "BLUE SCORING");
        obeliskMap.put(39, "BLUE SCORING");
        obeliskMap.put(40, "BLUE SCORING");
        obeliskMap.put(41, "BLUE SCORING");
        obeliskMap.put(42, "BLUE SCORING");
        obeliskMap.put(43, "BLUE SCORING");
        obeliskMap.put(44, "BLUE SCORING");
        obeliskMap.put(45, "BLUE SCORING");*/

        obeliskMap.put(21, "BLUE SCORING");
        obeliskMap.put(22, "BLUE SCORING");
        obeliskMap.put(23, "BLUE SCORING");


        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100); // This sets how often we ask Limelight for data (100 times per second)
        limelight.start(); // This tells Limelight to start looking!

        limelight.pipelineSwitch(0); // Switch to pipeline number 0


        while (opModeInInit()) {
            turret = hardwareMap.get(DcMotor.class, "0C");
            LLResult result = limelight.getLatestResult();
            fiducials = result.getFiducialResults();

            if (result != null && result.isValid()) {
                Pose3D botpose = result.getBotpose();
                if (botpose != null) {
                    double rx = botpose.getPosition().x;
                    double ry = botpose.getPosition().y;
                    telemetry.addData("MT1 Location", "(" + rx + ", " + ry + ")");
                }
            }

            if (!fiducials.isEmpty()) {
                for (FiducialResult fiducial : fiducials) {
                    int id = fiducial.getFiducialId(); // The ID number of the fiducial
                    tx = fiducial.getTargetXDegrees(); // Where it is (left-right)
                    ty = fiducial.getTargetYDegrees(); // Where it is (up-down)
                    double area = fiducial.getTargetArea();

                    telemetry.addData("Target ID:", id);

                    if (obeliskMap.containsKey(id)) {
                        telemetry.addData("Target Color:", obeliskMap.get(id));
                    }

                    telemetry.addData("Target X:", tx);
                    telemetry.addData("Target Y:", ty);
                    telemetry.addData("Target Area:", area);


                }
            } else {
                telemetry.addData("Limelight:", "No Targets");
            }

//            if (tx < -3) {
//                turret.setPower(-0.1);
////                telemetry.addLine("tx < 0");
//            } else if (tx > 3) {
//                turret.setPower(0.1);
////                telemetry.addLine("tx > 0");
//            } else {
//                turret.setPower(0);
////                telemetry.addLine("tx = 0");
//            }
            telemetry.update();
        }

//        while (opModeIsActive()) {
//            telemetry.addData("Target X:", tx);
//            if (tx < 0) {
////                turret.setPower(1);
//                telemetry.addLine("tx < 0");
//            } else if (tx > 0) {
////                turret.setPower(-1);
//                telemetry.addLine("tx > 0");
//            } else {
////                turret.setPower(0);
//                telemetry.addLine("tx = 0");
//            }
//            telemetry.update();
//        }
    }
}