package org.firstinspires.ftc.teamcode;

import com.bylazar.configurables.annotations.Configurable;

@Configurable
public class RobotMap {
    public static double SHOOTER_P = 0.014;
    public static double SHOOTER_I = 0.0;
    public static double SHOOTER_D = 0.0;

    public static double SHOOTER_SPEED = 2150;
    public static double SERVO_MOVE_FAR = 0.8;
    public static double SERVO_MOVE_MID = 0.2;
    public static double SERVO_MOVE_CLOSE = 0.45;
    public static double SHOOTER_SPEED_RANGE = 110;


    public static double INTAKE_MOTOR_POWER = 1;
    public static double TRANSFER_SERVO_POWER = 1;

    public static Long INTAKE_FULLY_TIME = 450L;

}
