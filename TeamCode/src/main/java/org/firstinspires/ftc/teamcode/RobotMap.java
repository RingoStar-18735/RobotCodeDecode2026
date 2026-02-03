package org.firstinspires.ftc.teamcode;

import com.bylazar.configurables.annotations.Configurable;

@Configurable
public class RobotMap {
    public static  double INTAKE_VELOCITY_CONSTRAINT = 25;
    public static  double STARTING_ANGLE_OFFSET = 45;
    public static  double FORWARD_INTAKE_LAST_BALL = 3.5 ;
    public static  double BACK_INTAKE_LAST_BALL = 0.9  ;
    public static  double INTAKE_SHOOT_TIME_FIRST = 2.5;
    public static  double ROBOT_DISTANCE_X =-15;//
    public static  double ROBOT_DISTANCE_Y =-13;//
    public static  double INTAKE_SHOOT_TIME_SECOND =2
            ;
//    public static  double MINIMUM_PERCENT_FOR_DETECT = 5;
//    public static  double LIME_CONSTANT = 1.1;
    public static double SHOOTER_P = 0.05;
    public static double SHOOTER_I = 0.0;
    public static double SHOOTER_D = 0.1;

    public static double SHOOTER_SPEED = 2200;
    public static double SERVO_MOVE_AUTO_MID = 0.5;
    public static double SERVO_MOVE_FAR = 0.65;
    public static double SERVO_MOVE_MID = 0.45;
    public static double SERVO_MOVE_CLOSE = 0.5;
    public static double SHOOTER_SPEED_RANGE = 150;

    public static double INTAKE_MOTOR_POWER = 1;
    public static double TRANSFER_SERVO_POWER = 1;

    public static double TURRET_P = 0.005;
    public static double TURRET_I = 0.0001;
    public static double TURRET_D = 0.00001;

    public static  double TURRET_GEAR_RATIO = (double) (-147 - 20) / 180 * 180/150;

    public static double TURRET_RADIUS = 3;


    public static double RESET_TURRET_POWER = 0.3;

    public static double MAX_TURRET_ANGLE = 240;
    public static double MIN_TURRET_ANGLE = -48;

    public static int LIMELIGHT_HZ = 100;

    public static double INTAKE_REVERSED_TIME = 1;
    public static double INTAKE_REVERSED_POWER = -0.5;



    public static int BLUE_PIPELINE = 0;
    public static double SHOOT_TIME = 0.5;
    public static int RED_PIPELINE = 1;

}