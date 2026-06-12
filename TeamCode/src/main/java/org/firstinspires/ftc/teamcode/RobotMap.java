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

    public static double SHOOTER_P = 0.033; // 0.9
    public static double SHOOTER_I = 0; // 0
    public static double SHOOTER_D = 0.005 ; // 0.9 og autoworks
    public static double F_Active = 0.00044; // 0.0005 og autoworks
    public static double F_Passive = 0; // 0

    public static double SHOOTER_SPEED_CLOSE = 2000 / 1.14; //1450 og autoworks
    public static double SHOOTER_SPEED_MID = 2000 / 1.14; //1450 og autoworks
    public static double SHOOTER_SPEED_FAR = 2100 / 1.14; //1700 og autoworks

    public static double SERVO_MOVE_AUTO_MID = 0.45;
    public static double SERVO_MOVE_FAR = 0.33; //  0.25 og autoworks
    public static double SERVO_MOVE_MID = 0.45; // 0.278 og autoworksg  
    public static double SERVO_MOVE_CLOSE = 0; // 0
    public static double SHOOTER_SPEED_RANGE = 150;

    public static double INTAKE_MOTOR_POWER = 1;
    public static double TRANSMISSION_MOTOR_POWER = 1;
    public static double TRANSMISSION_MOTOR_REVERSE_POWER = 1;

    public static double TURRET_P = 0.06; // 0.06
    public static double TURRET_I = 0;
    public static double TURRET_D = 0.2; // 0.2

    public static  double TURRET_GEAR_RATIO = (double) (-147 - 20) / 180 * 180/150;
    public static double TURRET_RADIUS = 0.394 * 16.977;
    public static boolean TURRET_ROBOT_DIFRANCE = false;
    public static double RESET_TURRET_POWER = 0.25;

    public static double MAX_TURRET_ANGLE = 200; // 25/5 -> 170 // looking at the back // 130
    public static double MIN_TURRET_ANGLE = 0; // 25/5 -> -155 // -75

    public static int LIMELIGHT_HZ = 100;

    public static double INTAKE_REVERSED_TIME = 1;
    public static double INTAKE_REVERSED_POWER = -0.5;

    public static int BLUE_PIPELINE = 0;
    public static double SHOOT_TIME = 0.5;
    public static int RED_PIPELINE = 1;

    public static Pose CLOSE_BLUE_STARTING_POSITION = new Pose(34, 136, 135);
    public static Pose MIDDLE_STARTING_POSITION = new Pose(72, 72, Math.toRadians(90));
}