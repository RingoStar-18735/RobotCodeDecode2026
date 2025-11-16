package org.firstinspires.ftc.teamcode.Commands.Drive;


import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;

import java.util.function.Supplier;

import dev.nextftc.core.commands.Command;

public class MecanumCommand extends Command {
    private final DriveSubsystem driveSubsystem = DriveSubsystem.INSTANCE;
    private Supplier<Double> leftYSupplier;
    private Supplier<Double> leftXSupplier;
    private Supplier<Double> rightXSupplier;
    private Supplier<Double> headingSupplier;

    private double leftY;
    private double leftX;
    private double rightX;
    private double heading;

    public MecanumCommand() {
        requires(driveSubsystem);
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public void start() {

    }

    @Override
    public void update() {
        leftY = leftYSupplier.get();
        leftX = leftXSupplier.get();
        rightX = rightXSupplier.get();
        heading = headingSupplier.get(); // Make sure this is in radians

        // מחשב את הערכים של כל מנוע לפי field centric
        leftX = leftX * Math.cos(-heading) - leftY * Math.sin(-heading);
        leftY = leftX * Math.sin(-heading) + leftY * Math.cos(-heading);

        double denominator = Math.max(Math.abs(leftX) + Math.abs(leftY) + Math.abs(rightX), 1.0);
        double frontLeftPower = (leftY + leftX + rightX) / denominator;
        double frontRightPower = (leftY - leftX - rightX) / denominator;
        double backLeftPower = (leftY - leftX + rightX) / denominator;
        double backRightPower = (leftY + leftX - rightX) / denominator;

        driveSubsystem.move(frontLeftPower, backLeftPower, frontRightPower, backRightPower);
    }

    @Override
    public void stop(boolean interrupted) {
        driveSubsystem.move(0, 0, 0 ,0);
    }
}
