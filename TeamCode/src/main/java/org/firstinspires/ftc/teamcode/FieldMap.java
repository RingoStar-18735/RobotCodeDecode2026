package org.firstinspires.ftc.teamcode;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.geometry.Pose;

@Configurable
public class FieldMap {
    public static Pose BLUE_TARGET_POS = new Pose(17, 130); // Inches
    public static Pose RED_TARGET_POS = BLUE_TARGET_POS.mirror(); // Inches
    public static double MAX_SHOOT_DISTANCE = 173;
}
