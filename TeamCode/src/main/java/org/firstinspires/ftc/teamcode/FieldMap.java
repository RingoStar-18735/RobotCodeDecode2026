package org.firstinspires.ftc.teamcode;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.geometry.Pose;

@Configurable
public class FieldMap {
    public static Pose BLUE_TARGET_POS = new Pose(8, 142); // Inches
    public static Pose RED_TARGET_POS = new Pose(139, 142); // Inches // 138,142
    public static double MAX_SHOOT_DISTANCE = 173;
}
