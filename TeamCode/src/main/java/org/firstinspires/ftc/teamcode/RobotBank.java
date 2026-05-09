package org.firstinspires.ftc.teamcode;

import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.teamcode.Subsystems.AllianceType;

import dev.nextftc.core.subsystems.Subsystem;

public class RobotBank implements Subsystem {
    public static double Offset = 0;
    public static double LastAutoTurretAngle = 0;
    public static Pose LastAutoPos = RobotMap.MIDDLE_STARTING_POSITION;
    public static AllianceType Alliance;

}
