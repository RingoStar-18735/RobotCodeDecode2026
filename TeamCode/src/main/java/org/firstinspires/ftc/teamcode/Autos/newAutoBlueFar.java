package org.firstinspires.ftc.teamcode.Autos;


import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubSystem;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.TurretSubsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.pedroPathing.DrawingRobot;

import java.util.ArrayList;
import java.util.List;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.ParallelDeadlineGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@Autonomous
public class newAutoBlueFar extends NextFTCOpMode {
    public PathChain Path1;
    public PathChain Path2;
    public PathChain Path3;
    public PathChain Path4;
    public PathChain Path5;
    public PathChain Path6;
    public PathChain Path7;
    public PathChain Path8;
    public PathChain Path9;

    Command Path1Command;
    Command Path2Command;
    Command Path3Command;
    Command Path4Command;
    Command Path5Command;
    Command Path6Command;
    Command Path7Command;
    Command Path8Command;
    Command Path9Command;
    Command Auto;
    Follower follower;
    List<String> a = new ArrayList<String>();

    public newAutoBlueFar() {
        addComponents(
                new SubsystemComponent(
                        ShooterSubsystem.INSTANCE,
                        IntakeSubSystem.INSTANCE,
                        TurretSubsystem.INSTANCE
                ),
                new PedroComponent(Constants::createFollower),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }


    @Override
    public void onInit() {
        ShooterSubsystem.INSTANCE.Shooter.setPower(0);
        IntakeSubSystem.INSTANCE.IntakeStop().schedule();
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose(34, 136, 135));
        Path1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(34.000, 136.000),

                                new Pose(58, 90.000)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();

        Path2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(58.000, 90.000),

                                new Pose(50.000, 84.000)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();

        Path3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(50.000, 84.000),

                                new Pose(15.000, 84.000)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();

        Path4 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(15.000, 84.000),

                                new Pose(58.000, 90.000)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();

        Path5 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(58.000, 90.000),

                                new Pose(50.000, 60.000)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();

        Path6 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(50.000, 60.000),

                                new Pose(8.000, 60.000)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();

        Path7 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(8.000, 60.000),
                                new Pose(66.584, 57.546),
                                new Pose(58.000, 90.000)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();

        Path8 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(58.000, 90.000),
                                new Pose(53.869, 65.292),
                                new Pose(7.000, 58.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(130))

                .build();

        Path9 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(7.000, 58.000),
                                new Pose(68.715, 60.234),
                                new Pose(58.000, 90.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(130), Math.toRadians(180))

                .build();
        Path1Command = new FollowPath(Path1);
        Path2Command = new FollowPath(Path2);
        Path3Command = new FollowPath(Path3);
        Path4Command = new FollowPath(Path4);
        Path5Command = new FollowPath(Path5);
        Path6Command = new FollowPath(Path6);
        Path7Command = new FollowPath(Path7);
        Path8Command = new FollowPath(Path8);
        Path9Command = new FollowPath(Path9);
        Auto = new SequentialGroup(
                Path1Command,
//                ShootFromClose,
                Path2Command,
                MoveWhilePathing(Path3Command),
                Path4Command,
//                ShootFromClose,
                Path5Command,
                MoveWhilePathing(Path6Command),
                Path7Command,
                Path8Command,
                IntakeSubSystem.INSTANCE.IntakeFullyNotTransfer(),
                Path9Command
//                ShootFromClose
        );
    }

    @Override
    public void onUpdate() {
        DrawingRobot.drawDebug(follower);
        DrawingRobot.drawPoseHistory(follower.getPoseHistory());
        telemetry.addData("pose: ", follower.getPose());
        telemetry.addData("RunTime: ", getRuntime());
        follower.update();
    }
    public Command PrintCommand(String b){
        return new InstantCommand(()->a.add(b));
    }




//                    new SequentialGroup(
//                            new ParallelDeadlineGroup(
//                                    new Delay(RobotMap.BACK_INTAKE_LAST_BALL),
//                                    //IntakeSubSystem.INSTANCE.Transfer(-RobotMap.TRANSFER_SERVO_POWER),
//                                    //IntakeSubSystem.INSTANCE.IntakePower(-RobotMap.INTAKE_MOTOR_POWER),
//                                    ShooterSubsystem.INSTANCE.RunFullSpeed()
//                            ),
//                            PrintCommand("Finished 3"),
//
//                            new ParallelDeadlineGroup(
//                                    new Delay(RobotMap.FORWARD_INTAKE_LAST_BALL),
//                                    IntakeSubSystem.INSTANCE.Transfer(RobotMap.TRANSFER_SERVO_POWER),
//                                    IntakeSubSystem.INSTANCE.IntakePower(RobotMap.INTAKE_MOTOR_POWER),
//                                    ShooterSubsystem.INSTANCE.RunFullSpeed()
//                            ),
//                            PrintCommand("Finished 4")
//
//                    ),

    private Command MoveWhilePathing(Command path){
        return new ParallelDeadlineGroup(
                new Delay(4),
                path,
                IntakeSubSystem.INSTANCE.IntakeFullyNotTransfer()
        );
    }



    @Override
    public void onStartButtonPressed() {
        Auto.schedule();
    }
}

