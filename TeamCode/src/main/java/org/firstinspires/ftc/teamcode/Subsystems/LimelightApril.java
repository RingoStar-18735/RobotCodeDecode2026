package org.firstinspires.ftc.teamcode.Subsystems;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.RobotMap;

import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;

public class LimelightApril implements Subsystem {
    public final static LimelightApril INSTANCE = new LimelightApril();
    public LLResult CurrentResult;
    public Limelight3A limelight;

    @Override
    public void initialize() {
        limelight = ActiveOpMode.hardwareMap().get(Limelight3A.class, "limelightapril");
        limelight.setPollRateHz(RobotMap.LIMELIGHT_HZ);
        limelight.start();
        CurrentResult = limelight.getLatestResult();

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
    }
    public void setPipeline(int num){
        limelight.pipelineSwitch(num);
    }
    public Pose getPoseLimelight(){
        if (CurrentResult != null && CurrentResult.isValid()) {
            Pose3D botpose = CurrentResult.getBotpose();
            if (botpose != null) {
                double x = botpose.getPosition().x;
                double y = botpose.getPosition().y;
                double heading = botpose.getOrientation().getYaw(AngleUnit.RADIANS);
                Pose pose = new Pose(x, y, heading);
                return new Pose(x, y, heading);
            }
        }
        return new Pose(-10,-10, -10);
    }

    public Pose getRobotPos(Follower follower, double TurretAngle ){
        Pose CamPos = getPoseLimelight();
        if(CamPos.getPose().getX() == -10) return follower.getPose();

        double xBot = CamPos.getX()*39.37 + 72 - RobotMap.TURRET_RADIUS * Math.cos((Math.toRadians(TurretAngle) + follower.getPose().getHeading()));
        double yBot = CamPos.getY()*39.37 + 72 - RobotMap.TURRET_RADIUS * Math.sin((Math.toRadians(TurretAngle) + follower.getPose().getHeading()));
        double headingBot = CamPos.getHeading() -  Math.toRadians(TurretAngle);
        return  new Pose(xBot,yBot,headingBot);
    }


}
