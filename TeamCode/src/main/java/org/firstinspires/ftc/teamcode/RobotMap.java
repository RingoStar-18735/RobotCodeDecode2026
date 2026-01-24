package org.firstinspires.ftc.teamcode;

import com.bylazar.configurables.annotations.Configurable;

@Configurable
public class RobotMap {
    public static  double MINIMUM_PERCENT_FOR_DETECT = 5;
    public static  double LIME_CONSTANT = 1.1;
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

    public static double TURRET_P = 0.005;
    public static double TURRET_I = 0.0001;
    public static double TURRET_D = 0.00001;

    public static  double TURRET_GEAR_RATIO = -(41.0 / 128.0) / 3.7 / 103.8 * (90 / 25.22);

    public static double INTAKE_FULLY_TIME = 1;


    public static double RESET_TURRET_POWER = 0.15;

    public static double MAX_TURRET_ANGLE = 240;
    public static double MIN_TURRET_ANGLE = -48;

    public static int LIMELIGHT_HZ = 100;

    public static double INTAKE_REVERSED_TIME = 0.5;
    public static double INTAKE_REVERSED_POWER = -0.5;



    public static int BLUE_PIPELINE = 0;
    public static int SHOOT_TIME = 1;
    public static int RED_PIPELINE = 1;

}
