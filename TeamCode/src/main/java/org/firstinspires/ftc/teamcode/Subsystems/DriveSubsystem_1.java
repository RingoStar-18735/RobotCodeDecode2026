package org.firstinspires.ftc.teamcode.Subsystems;

import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.Direction;
import dev.nextftc.hardware.impl.IMUEx;
import dev.nextftc.hardware.impl.MotorEx;

public class DriveSubsystem_1 implements Subsystem {
    public static final DriveSubsystem_1 INSTANCE = new DriveSubsystem_1();
    private DriveSubsystem_1() {}

    private MotorEx frontLeft;
    private MotorEx backLeft;
    private MotorEx frontRight;
    private MotorEx backRight;

    private IMUEx imu;

    @Override
    public void periodic() {

    }

    @Override
    public void initialize() {
        frontLeft = new MotorEx("FrontLeft");
        backLeft = new MotorEx("BackLeft");
        frontRight = new MotorEx("FrontRight");
        backRight = new MotorEx("BackRight");
        imu = new IMUEx("imu", Direction.UP, Direction.FORWARD).zeroed();
    }

    public void move(double frontLeftPow, double backLeftPow, double frontRightPow, double backRightPow) {
        frontLeft.setPower(frontLeftPow);
        backLeft.setPower(backLeftPow);
        frontRight.setPower(frontRightPow);
        backRight.setPower(backRightPow);
    }
}