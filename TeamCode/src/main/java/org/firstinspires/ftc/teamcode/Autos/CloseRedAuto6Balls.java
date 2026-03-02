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
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@Autonomous
public class CloseRedAuto6Balls extends NextFTCOpMode {
    public PathChain Path1;
    public PathChain Path2;
    public PathChain Path3;
    public PathChain Path4;
    public PathChain Path5;
    public PathChain Path6;
    Command Path1Command ;
    Command Path2Command;
    Command Path3Command;
    Command Path4Command ;
    Command Path5Command ;
    Command Path6Command ;

    Command Auto;
    Follower follower;
    List<String> a = new ArrayList<String>();

    public CloseRedAuto6Balls() {
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
        follower.setStartingPose(new Pose(28.681751824817518, 131.68175182481747, 144).mirror());
        Path1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(28.682, 131.682).mirror(),

                                new Pose(75, 92).mirror()
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(MirrorAngle(324)), Math.toRadians(MirrorAngle(327)))

                .build();

        Path6 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(75, 92).mirror(),

                                new Pose(80, 87).mirror()
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(MirrorAngle(325)), Math.toRadians(MirrorAngle(183)))

                .build();

        Path2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(80, 87).mirror(),

                                new Pose(67, 87).mirror()
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(MirrorAngle(183)), Math.toRadians(MirrorAngle(183)))

                .build();

        Path3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(67, 87).mirror(),

                                new Pose(17.312, 87).mirror()
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(MirrorAngle(183)))

                .build();

        Path4 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(17.312, 87).mirror(),

                                new Pose(75, 92).mirror()
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(MirrorAngle(183)), Math.toRadians(MirrorAngle(327)))

                .build();

        Path5 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(75, 92).mirror(),

                                new Pose(15.000, 94.641).mirror()
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(MirrorAngle(330)), Math.toRadians(MirrorAngle(180)))

                .build();
        Path1Command = new FollowPath(Path1);
        Path2Command = new FollowPath(Path2);
        Path3Command = new FollowPath(Path3);
        Path4Command = new FollowPath(Path4);
        Path5Command = new FollowPath(Path5);
        Path6Command = new FollowPath(Path6);
        Auto = new SequentialGroup(
                Path1Command,
                ShootFromClose,
                Path6Command,
                Path2Command,
                MoveWhilePathing(Path3Command),
                Path4Command,
                ShootFromClose,
                Path5Command
        );
    }

    public double MirrorAngle(double ang){
        return (180 - ang + 360) % 360;
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



    Command ShootFromFar =
            new SequentialGroup(
                    ShooterSubsystem.INSTANCE.ServoAimCommand(RobotMap.SERVO_MOVE_FAR),
                    ShooterSubsystem.INSTANCE.RunFullSpeed(),
                    PrintCommand("Finished 1"),
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
                    PrintCommand("Finished 2"),
                    new SequentialGroup(
                            new ParallelDeadlineGroup(
                                    new Delay(RobotMap.BACK_INTAKE_LAST_BALL),
                                    IntakeSubSystem.INSTANCE.Transfer(-RobotMap.TRANSMISSION_MOTOR_POWER),
                                    IntakeSubSystem.INSTANCE.IntakePower(RobotMap.INTAKE_REVERSED_POWER),
                                    ShooterSubsystem.INSTANCE.RunFullSpeed()
                            ),
                            PrintCommand("Finished 3"),
                            new ParallelDeadlineGroup(
                                    new Delay(RobotMap.FORWARD_INTAKE_LAST_BALL),
                                    IntakeSubSystem.INSTANCE.Transfer(RobotMap.TRANSMISSION_MOTOR_POWER),
                                    IntakeSubSystem.INSTANCE.IntakePower(RobotMap.INTAKE_MOTOR_POWER),
                                    ShooterSubsystem.INSTANCE.RunFullSpeed()
                            ),
                            PrintCommand("Finished 4")

                    ),
                    new ParallelDeadlineGroup(
                            ShooterSubsystem.INSTANCE.StopSpeed(),
                            IntakeSubSystem.INSTANCE.IntakeStop()
                    ),
                    PrintCommand("Finished 5")
            );

    Command ShootFromMid =
            new SequentialGroup(
                    new ParallelGroup(
                            ShooterSubsystem.INSTANCE.RunFullSpeed(),
                            ShooterSubsystem.INSTANCE.ServoAimCommand(RobotMap.SERVO_MOVE_MID)
                    ),
                    PrintCommand("Finished 1"),
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
                    PrintCommand("Finished 2"),
                    new SequentialGroup(
                            new ParallelDeadlineGroup(
                                    new Delay(RobotMap.BACK_INTAKE_LAST_BALL),
                                    IntakeSubSystem.INSTANCE.Transfer(-RobotMap.TRANSMISSION_MOTOR_POWER),
                                    IntakeSubSystem.INSTANCE.IntakePower(-RobotMap.INTAKE_MOTOR_POWER),
                                    ShooterSubsystem.INSTANCE.RunFullSpeed()
                            ),
                            PrintCommand("Finished 3"),

                            new ParallelDeadlineGroup(
                                    new Delay(RobotMap.FORWARD_INTAKE_LAST_BALL),
                                    IntakeSubSystem.INSTANCE.Transfer(RobotMap.TRANSMISSION_MOTOR_POWER),
                                    IntakeSubSystem.INSTANCE.IntakePower(RobotMap.INTAKE_MOTOR_POWER),
                                    ShooterSubsystem.INSTANCE.RunFullSpeed()
                            ),
                            PrintCommand("Finished 4")

                    ),
                    new ParallelDeadlineGroup(
                            ShooterSubsystem.INSTANCE.StopSpeed(),
                            IntakeSubSystem.INSTANCE.IntakeStop()
                    ),
                    PrintCommand("Finished 5")
            );

    Command ShootFromClose =
            new SequentialGroup(
                    new ParallelGroup(
                            ShooterSubsystem.INSTANCE.RunFullSpeed(),
                            ShooterSubsystem.INSTANCE.ServoAimCommand(RobotMap.SERVO_MOVE_CLOSE)
                    ),
                    PrintCommand("Finished 1"),
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
                    PrintCommand("Finished 2"),
                    new SequentialGroup(
                            new ParallelDeadlineGroup(
                                    new Delay(RobotMap.BACK_INTAKE_LAST_BALL),
                                    IntakeSubSystem.INSTANCE.Transfer(-RobotMap.TRANSMISSION_MOTOR_POWER),
                                    IntakeSubSystem.INSTANCE.IntakePower(-RobotMap.INTAKE_MOTOR_POWER),
                                    ShooterSubsystem.INSTANCE.RunFullSpeed()
                            ),
                            PrintCommand("Finished 3"),

                            new ParallelDeadlineGroup(
                                    new Delay(RobotMap.FORWARD_INTAKE_LAST_BALL),
                                    IntakeSubSystem.INSTANCE.Transfer(RobotMap.TRANSMISSION_MOTOR_POWER),
                                    IntakeSubSystem.INSTANCE.IntakePower(RobotMap.INTAKE_MOTOR_POWER),
                                    ShooterSubsystem.INSTANCE.RunFullSpeed()
                            ),
                            PrintCommand("Finished 4")

                    ),
                    new ParallelDeadlineGroup(
                            ShooterSubsystem.INSTANCE.StopSpeed(),
                            IntakeSubSystem.INSTANCE.IntakeStop()
                    ),
                    PrintCommand("Finished 5")

            );

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
