package org.firstinspires.ftc.teamcode;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.geometry.Pose;

@Configurable
public class RobotMap {
    public static  double INTAKE_VELOCITY_CONSTRAINT = 25;
    public static  double STARTING_ANGLE_OFFSET = 45;
    public static  double FORWARD_INTAKE_LAST_BALL = 3.5 ;
    public static  double BACK_INTAKE_LAST_BALL = 0.9  ;
    public static  double INTAKE_SHOOT_TIME_FIRST = 5;
    public static  double ROBOT_DISTANCE_X =-15;//
    public static  double ROBOT_DISTANCE_Y =-13;//
    public static  double INTAKE_SHOOT_TIME_SECOND =2;

    public static double SHOOTER_P = 0.0004; // 0.0002
    public static double SHOOTER_I = 0; // 0
    public static double SHOOTER_D = 0.9 ; // 0.88
    public static double F_Active = 0.0005; // 0.00045
    public static double F_Passive = 0; // 0

    public static double SHOOTER_SPEED_CLOSE = 1450; // 2000 1450
    public static double SHOOTER_SPEED_MID = 1450  ; // 2050 1450
    public static double SHOOTER_SPEED_FAR = 1500; // 2600 2300

    public static double SERVO_MOVE_AUTO_MID = 0.45;
    public static double SERVO_MOVE_FAR = 0.25; // 0.15
    public static double SERVO_MOVE_MID = 0.278; // 0.26
    public static double SERVO_MOVE_CLOSE = 0; // 0
    public static double SHOOTER_SPEED_RANGE = 150;

    public static double INTAKE_MOTOR_POWER = 1;
    public static double TRANSMISSION_MOTOR_POWER = 1;
    public static double TRANSMISSION_MOTOR_REVERSE_POWER = 1;

    public static double TURRET_P = 0.07; // 0.06 // 0.075
    public static double TURRET_I = 0;
    public static double TURRET_D = 0.15; // 0.2

    public static  double TURRET_GEAR_RATIO = (double) (-147 - 20) / 180 * 180/150;
    public static double TURRET_RADIUS = 0.394 * 16.977;
    public static boolean TURRET_ROBOT_DIFRANCE = false;
    public static double RESET_TURRET_POWER = 0.25;

    public static double MAX_TURRET_ANGLE = 200; // looking at the back
    public static double MIN_TURRET_ANGLE = 0;

    public static int LIMELIGHT_HZ = 100;

    public static double INTAKE_REVERSED_TIME = 1;
    public static double INTAKE_REVERSED_POWER = -0.5;

    public static int BLUE_PIPELINE = 0;
    public static double SHOOT_TIME = 0.5;
    public static int RED_PIPELINE = 1;

    public static Pose CLOSE_BLUE_STARTING_POSITION = new Pose(34, 136, 135);
    public static Pose MIDDLE_STARTING_POSITION = new Pose(72, 72, Math.toRadians(90));
}