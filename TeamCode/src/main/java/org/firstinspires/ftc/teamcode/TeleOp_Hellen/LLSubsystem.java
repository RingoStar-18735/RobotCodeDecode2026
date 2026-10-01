package org.firstinspires.ftc.teamcode.TeleOp_Hellen;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes.FiducialResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import java.util.HashMap;
import java.util.List;

import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;

public class LLSubsystem implements Subsystem {

    public static final LLSubsystem INSTANCE = new LLSubsystem();

    private Limelight3A limelight;
    private double tx = 0;
    private double ty = 0;

    private boolean hasTarget = false;

    private final HashMap<Integer, String> obeliskMap = new HashMap<>();

    public LLSubsystem() {
        obeliskMap.put(21, "BLUE SCORING");
        obeliskMap.put(22, "BLUE SCORING");
        obeliskMap.put(23, "BLUE SCORING");
    }

    @Override
    public void initialize() {
        limelight = ActiveOpMode.hardwareMap().get(
                Limelight3A.class,
                "limelight"
        );

        limelight.setPollRateHz(100);
        limelight.pipelineSwitch(0);
        limelight.start();
    }

    @Override
    public void periodic() {
        tx = 0;
        ty = 0;
        LLResult result = limelight.getLatestResult();

        if (result == null || !result.isValid()) {
            hasTarget = false;
            return;
        }

        List<FiducialResult> fiducials = result.getFiducialResults();

        if (fiducials == null || fiducials.isEmpty()) {
            hasTarget = false;
            return;
        }

        hasTarget = true;

        // לוקח את ה-Fiducial הראשון
        FiducialResult fiducial = fiducials.get(0);

        int id = fiducial.getFiducialId();

        tx = fiducial.getTargetXDegrees();
        ty = fiducial.getTargetYDegrees();

        ActiveOpMode.telemetry().addData("Target ID", id);
        ActiveOpMode.telemetry().addData("Target X", tx);
        ActiveOpMode.telemetry().addData("Target Y", ty);

        if (obeliskMap.containsKey(id)) {
            ActiveOpMode.telemetry().addData(
                    "Target Color",
                    obeliskMap.get(id)
            );
        }
    }

    public double getTx() {
        return tx;
    }

    public double getTy() {
        return ty;
    }

    public boolean hasTarget() {
        return hasTarget;
    }

}
