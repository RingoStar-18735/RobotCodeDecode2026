package org.firstinspires.ftc.teamcode.Subsystems;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.hardware.driving.MecanumDriverControlled;
import dev.nextftc.hardware.impl.MotorEx;

public class DriveSubsystem implements Subsystem {
    public static final DriveSubsystem INSTANCE = new DriveSubsystem();
    private DriveSubsystem() {}

    public MotorEx frontLeftMotor = new MotorEx("2E").brakeMode();
    public MotorEx frontRightMotor = new MotorEx("0E").brakeMode();
    public MotorEx backLeftMotor = new MotorEx("3E").brakeMode()  ;
    public MotorEx backRightMotor = new MotorEx("1E").brakeMode();
//    private IMUEx imu = new IMUEx("imu", Direction.UP, Direction.FORWARD).zeroed();


    public Command driverControlled = new MecanumDriverControlled(
            frontLeftMotor,
            frontRightMotor,
            backLeftMotor,
            backRightMotor,
            Gamepads.gamepad1().leftStickY().negate(),
            Gamepads.gamepad1().leftStickX(),
            Gamepads.gamepad1().rightStickX()
    );

}
