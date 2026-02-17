package org.firstinspires.ftc.teamcode;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.geometry.Pose;

@Configurable
public class FieldMap {
    public static Pose BLUE_TARGET_POS = new Pose(5.757664233576641, 139.21751824817517); // Inches
    public static Pose RED_TARGET_POS = new Pose(5.757664233576641, 139.21751824817517).mirror(); // Inches
    public static double MAX_SHOOT_DISTANCE = 173;
}
