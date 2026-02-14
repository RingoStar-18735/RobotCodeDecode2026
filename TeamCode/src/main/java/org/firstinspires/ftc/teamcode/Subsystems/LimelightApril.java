package org.firstinspires.ftc.teamcode.Subsystems;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes.FiducialResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.RobotMap;

import java.util.ArrayList;
import java.util.List;

import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;

public class LimelightApril implements Subsystem {
    public final static LimelightApril INSTANCE = new LimelightApril();
    public LLResult CurrentResult;
    public Limelight3A limelight;

    private List<Integer> idList = new ArrayList<Integer>();

    @Override
    public void initialize() {
        limelight = ActiveOpMode.hardwareMap().get(Limelight3A.class, "limelightapril");
        limelight.setPollRateHz(RobotMap.LIMELIGHT_HZ);
        limelight.start();
        CurrentResult = limelight.getLatestResult();

        List<FiducialResult> fiducials = CurrentResult.getFiducialResults();
        List<Integer> tempidList = new ArrayList<Integer>();
        for (FiducialResult fiducial : fiducials)
            tempidList.add(fiducial.getFiducialId());

        idList = tempidList;
    }

    public double getTa() {
        if (CurrentResult != null && CurrentResult.isValid()) {
            return CurrentResult.getTa();
        }
        else return -1;
    }

    @Override
    public void periodic() {
        CurrentResult = limelight.getLatestResult();
        ActiveOpMode.telemetry().addData("PoseLimelightConverted: ", getPoseLimelight());
        ActiveOpMode.telemetry().addData("PosRobotCalced: ", CurrentResult.getBotpose());
        ActiveOpMode.telemetry().addData("PoseLimelightRaw: ", CurrentResult.getBotpose());

        List<FiducialResult> fiducials = CurrentResult.getFiducialResults();
        List<Integer> tempidList = new ArrayList<Integer>();
        for (FiducialResult fiducial : fiducials)
            tempidList.add(fiducial.getFiducialId());

        idList = tempidList;
    }

    public List<Integer> getIdList() {
        return idList;
    }

    public void setPipeline(int num){
        limelight.pipelineSwitch(num);
    }

    public Pose getPoseLimelight(){
        Pose limelightPos = new Pose(-100, -100);
        if (CurrentResult != null && CurrentResult.isValid()) {
            Pose3D limePos3d = CurrentResult.getBotpose();
            if (limePos3d != null) {
                limelightPos = ConvertPose3dToPose(limePos3d);
            }
        }
        return  limelightPos;
    }

    private static Pose ConvertPose3dToPose(Pose3D pose3D){
        return new Pose(pose3D.getPosition().x + 72,pose3D.getPosition().y + 72, pose3D.getOrientation().getYaw(AngleUnit.RADIANS));
    }
    public Pose getRobotPos(Follower follower, double TurretAngle ){
        Pose CamPos = getPoseLimelight();
        if(CamPos.getPose().getX() == -100) return follower.getPose();

        double xBot = CamPos.getX() - RobotMap.TURRET_RADIUS * Math.cos((Math.toRadians(TurretAngle) + follower.getPose().getHeading()));
        double yBot = CamPos.getY() - RobotMap.TURRET_RADIUS * Math.sin((Math.toRadians(TurretAngle) + follower.getPose().getHeading()));

//        double xBot = CamPos.getX()*39.37 + 72 - RobotMap.TURRET_RADIUS * Math.cos((Math.toRadians(TurretAngle) + follower.getPose().getHeading()));
//        double yBot = CamPos.getY()*39.37 + 72 - RobotMap.TURRET_RADIUS * Math.sin((Math.toRadians(TurretAngle) + follower.getPose().getHeading()));
        double headingBot = CamPos.getHeading() -  Math.toRadians(TurretAngle);
        return  new Pose(xBot,yBot,headingBot);
    }


}
