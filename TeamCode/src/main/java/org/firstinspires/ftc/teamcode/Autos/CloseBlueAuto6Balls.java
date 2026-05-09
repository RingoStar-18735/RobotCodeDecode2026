package org.firstinspires.ftc.teamcode.Autos;


import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.RobotBank;
import org.firstinspires.ftc.teamcode.Subsystems.AllianceType;
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
public class CloseBlueAuto6Balls extends NextFTCOpMode {
    public PathChain Path1;
    public PathChain Path2;
    public PathChain Path3;
    public PathChain Path4;
    public PathChain Path5;

    Command Path1Command;
    Command Path2Command;
    Command Path3Command;
    Command Path4Command;
    Command Path5Command;
    Command Auto;
    Follower follower;
    List<String> a = new ArrayList<String>();

    public CloseBlueAuto6Balls() {
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
        follower.setStartingPose(new Pose(28.681751824817518, 131.68175182481747, 144));
        Path1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(28.682, 131.682),

                                new Pose(70, 90)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(324), Math.toRadians(325))

                .build();

        Path2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(70, 90),

                                new Pose(60.000, 84.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(330), Math.toRadians(180))

                .build();

        Path3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(60.000, 84.000),

                                new Pose(17.312, 84.000)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();

        Path4 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(17.312, 84.000),

                                new Pose(70, 90)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(330))

                .build();

        Path5 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(70, 90),

                                new Pose(15.000, 94.641)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(330), Math.toRadians(180))

                .build();
        Path1Command = new FollowPath(Path1);
        Path2Command = new FollowPath(Path2);
        Path3Command = new FollowPath(Path3);
        Path4Command = new FollowPath(Path4);
        Path5Command = new FollowPath(Path5);
        Auto = new SequentialGroup(
                Path1Command,
//                ShootFromClose,
                Path2Command,
                MoveWhilePathing(Path3Command),
                Path4Command,
//                ShootFromClose,
                Path5Command
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





    private Command MoveWhilePathing(Command path){
        return new ParallelDeadlineGroup(
                        new Delay(4),
                        path,
                IntakeSubSystem.INSTANCE.IntakeFullyNotTransfer()
        );
    }



    @Override
    public void onStartButtonPressed() {
        RobotBank.Alliance = AllianceType.BLUE;
        Auto.schedule();
        RobotBank.LastAutoTurretAngle = TurretSubsystem.INSTANCE.getAngle();
        RobotBank.LastAutoPos = follower.getPose();
        RobotBank.Offset = TurretSubsystem.INSTANCE.getOffset();
    }
}
