package org.firstinspires.ftc.teamcode.Autos;


import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.RobotMap;
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
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
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


    public newAutoBlueFar(){

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
        ShooterSubsystem.INSTANCE.StopSpeed().schedule();
        IntakeSubSystem.INSTANCE.IntakeStop().schedule();
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose(56, 8, 180));
        Path1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(56.000, 8.000),

                                new Pose(38.000, 36.000)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();

        Path2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(38.000, 36.000),

                                new Pose(8.000, 36.000)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();

        Path3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(8.000, 36.000),

                                new Pose(56.000, 8.000)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();

        Path4 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(56.000, 8.000),

                                new Pose(6.000, 25.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(270))

                .build();

        Path5 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(6.000, 25.000),

                                new Pose(6.000, 4.000)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(270))

                .build();

        Path6 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(6.000, 4.000),

                                new Pose(56.000, 8.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(270), Math.toRadians(180))

                .build();

        Path7 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(56.000, 8.000),

                                new Pose(38.000, 60.000)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();

        Path8 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(38.000, 60.000),

                                new Pose(8.000, 60.000)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        Path9 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(8.000, 60.000),

                                new Pose(56.000, 8.000)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();

        Command ShootFromFar =
                new SequentialGroup(
                        ShooterSubsystem.INSTANCE.ServoAim(RobotMap.SERVO_MOVE_FAR),
                        ShooterSubsystem.INSTANCE.RunFullSpeed(),
                        new ParallelDeadlineGroup(
                                new Delay(RobotMap.INTAKE_SHOOT_TIME_FIRST),
                                new SequentialGroup(
                                        IntakeSubSystem.INSTANCE.IntakeFullyTransfer(),
                                        ShooterSubsystem.INSTANCE.RunFullSpeed(),
                                        IntakeSubSystem.INSTANCE.IntakeFullyTransfer(),
                                        ShooterSubsystem.INSTANCE.RunFullSpeed(),
                                        IntakeSubSystem.INSTANCE.IntakeFullyTransfer()
                                )
                        ),
                        new SequentialGroup(
                                new ParallelDeadlineGroup(
                                        new Delay(RobotMap.BACK_INTAKE_LAST_BALL),
                                        IntakeSubSystem.INSTANCE.Transfer(-RobotMap.TRANSFER_SERVO_POWER),
                                        IntakeSubSystem.INSTANCE.IntakePower(-RobotMap.INTAKE_MOTOR_POWER),
                                        ShooterSubsystem.INSTANCE.RunFullSpeed()
                                ),
                                new ParallelDeadlineGroup(
                                        new Delay(RobotMap.FORWARD_INTAKE_LAST_BALL),
                                        IntakeSubSystem.INSTANCE.Transfer(RobotMap.TRANSFER_SERVO_POWER),
                                        IntakeSubSystem.INSTANCE.IntakePower(RobotMap.INTAKE_MOTOR_POWER),
                                        ShooterSubsystem.INSTANCE.RunFullSpeed()
                                )

                        ),
                        new ParallelDeadlineGroup(
                                ShooterSubsystem.INSTANCE.StopSpeed(),
                                IntakeSubSystem.INSTANCE.IntakeStop()
                        )
                );

        Command ShootFromMid =
                new SequentialGroup(
                        new ParallelGroup(
                                ShooterSubsystem.INSTANCE.RunFullSpeed(),
                                ShooterSubsystem.INSTANCE.ServoAim(RobotMap.SERVO_MOVE_MID)
                        ),
                        new ParallelDeadlineGroup(
                                new Delay(RobotMap.INTAKE_SHOOT_TIME_FIRST),
                                new SequentialGroup(
                                        IntakeSubSystem.INSTANCE.IntakeFullyTransfer(),
                                        ShooterSubsystem.INSTANCE.RunFullSpeed(),
                                        IntakeSubSystem.INSTANCE.IntakeFullyTransfer(),
                                        ShooterSubsystem.INSTANCE.RunFullSpeed(),
                                        IntakeSubSystem.INSTANCE.IntakeFullyTransfer()
                                )
                        ),
                        new SequentialGroup(
                                new ParallelDeadlineGroup(
                                        new Delay(RobotMap.BACK_INTAKE_LAST_BALL),
                                        IntakeSubSystem.INSTANCE.Transfer(-RobotMap.TRANSFER_SERVO_POWER),
                                        IntakeSubSystem.INSTANCE.IntakePower(-RobotMap.INTAKE_MOTOR_POWER),
                                        ShooterSubsystem.INSTANCE.RunFullSpeed()
                                ),

                                new ParallelDeadlineGroup(
                                        new Delay(RobotMap.FORWARD_INTAKE_LAST_BALL),
                                        IntakeSubSystem.INSTANCE.Transfer(RobotMap.TRANSFER_SERVO_POWER),
                                        IntakeSubSystem.INSTANCE.IntakePower(RobotMap.INTAKE_MOTOR_POWER),
                                        ShooterSubsystem.INSTANCE.RunFullSpeed()
                                )

                        ),
                        new ParallelDeadlineGroup(
                                ShooterSubsystem.INSTANCE.StopSpeed(),
                                IntakeSubSystem.INSTANCE.IntakeStop()
                        )
                );

        Command ShootFromClose =
                new SequentialGroup(
                        new ParallelGroup(
                                ShooterSubsystem.INSTANCE.RunFullSpeed(),
                                ShooterSubsystem.INSTANCE.ServoAim(RobotMap.SERVO_MOVE_AUTO_MID)
                        ),
                        new ParallelDeadlineGroup(
                                new Delay(RobotMap.INTAKE_SHOOT_TIME_FIRST),
                                new SequentialGroup(
                                        IntakeSubSystem.INSTANCE.IntakeFullyTransfer(),
                                        ShooterSubsystem.INSTANCE.RunFullSpeed(),
                                        IntakeSubSystem.INSTANCE.IntakeFullyTransfer(),
                                        ShooterSubsystem.INSTANCE.RunFullSpeed(),
                                        IntakeSubSystem.INSTANCE.IntakeFullyTransfer()
                                )
                        ),
                        new SequentialGroup(
                                new ParallelDeadlineGroup(
                                        new Delay(RobotMap.BACK_INTAKE_LAST_BALL),
                                        IntakeSubSystem.INSTANCE.Transfer(-RobotMap.TRANSFER_SERVO_POWER),
                                        IntakeSubSystem.INSTANCE.IntakePower(RobotMap.INTAKE_REVERSED_POWER),
                                        ShooterSubsystem.INSTANCE.RunFullSpeed()
                                ),

                                new ParallelDeadlineGroup(
                                        new Delay(RobotMap.FORWARD_INTAKE_LAST_BALL),
                                        IntakeSubSystem.INSTANCE.Transfer(RobotMap.TRANSFER_SERVO_POWER),
                                        IntakeSubSystem.INSTANCE.IntakePower(RobotMap.INTAKE_MOTOR_POWER),
                                        ShooterSubsystem.INSTANCE.RunFullSpeed()
                                )

                        ),
                        new ParallelDeadlineGroup(
                                ShooterSubsystem.INSTANCE.StopSpeed(),
                                IntakeSubSystem.INSTANCE.IntakeStop()
                        )

                );

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
                ShootFromFar,
                Path1Command,
                Path2Command,
                Path3Command,
                ShootFromFar,
                Path4Command,
                Path5Command,
                Path6Command,
                ShootFromFar,
                Path7Command,
                Path8Command,
                Path9Command

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
    @Override
    public void onStartButtonPressed() {
        Auto.schedule();
    }
}
