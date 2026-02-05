package org.firstinspires.ftc.teamcode;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.geometry.Pose;

@Configurable
public class FieldMap {
    public static Pose BLUE_TARGET_POS = new Pose(5.757664233576641, 139.21751824817517);
    public static Pose RED_TARGET_POS = new Pose(5.757664233576641, 139.21751824817517).mirror();
}
