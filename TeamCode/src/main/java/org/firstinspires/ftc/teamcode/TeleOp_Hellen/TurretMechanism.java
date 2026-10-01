/*
package org.firstinspires.ftc.teamcode.TeleOp_Hellen;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.openftc.apriltag.AprilTagDetection;

import java.util.HashMap;

import dev.nextftc.hardware.impl.MotorEx;

public class TurretMechanism {
    private DcMotorEx turret;

    private double kP = 0.0001;
    private double kD = 0.0000;
    private double goalX = 0;
    private double lastError = 0;
    private double angleTolerance = 0.2;
    private double MAX_POWER = 0.8;
    private double power = 0;

    private final ElapsedTime timer = new ElapsedTime();

    public void init(HardwareMap hwMap) {
        turret = hwMap.get(DcMotorEx.class, "turret");
        turret = setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void setkP(double newkP) {
        kP = newkP;
    }

    public void getkP() {
        return kP;
    }

    public void setkD(double newkD) {
        kD = newkD;
    }

    public void getkD() {
        return kD;
    }

    public void resetTimer() {
        timer.reset();
    }

    public void update(*/
/*what out current fiduciary marker is*//*
 AprilTagDetection curID){
        double deltaTime = timer.seconds();
        timer.reset(); // allows us to keep track of how long it was since our previous loop

        if (curID == null) {
            turret.setPower(0);
            lastError = 0;
            return;
        }


        // --------- start PD controller ---------

        double error = goalX - curID.ftcPose.bearing; // tx in ll
        double pTerm = error * kP;

        double dTerm = 0;
        if (deltaTime > 0) {
            dTerm = ((error - lastError) / deltaTime);
        }

        if (Math.abs(error) <angleTolerance) {
            power = 0;
        }

    }



}
*/
