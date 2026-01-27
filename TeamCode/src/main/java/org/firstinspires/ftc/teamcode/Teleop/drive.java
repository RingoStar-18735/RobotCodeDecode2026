package org.firstinspires.ftc.teamcode.Teleop;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.hardware.driving.MecanumDriverControlled;
import dev.nextftc.hardware.impl.Direction;
import dev.nextftc.hardware.impl.IMUEx;
import dev.nextftc.hardware.impl.MotorEx;

public class drive implements Subsystem {
    public static final drive INSTANCE = new drive();

    private drive() {}

    private MotorEx frontLeftMotor = new MotorEx("0C").brakeMode().reversed();
    private MotorEx frontRightMotor = new MotorEx("1C").brakeMode(); // להרחיק מהעץ
    private MotorEx backLeftMotor = new MotorEx("2C").brakeMode().reversed(); // להרחיק מהעץ
    private MotorEx backRightMotor = new MotorEx("3C").brakeMode();
    private IMUEx imu = new IMUEx("imu", Direction.UP, Direction.FORWARD).zeroed();


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
