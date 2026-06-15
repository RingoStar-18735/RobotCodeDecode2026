package org.firstinspires.ftc.teamcode.Autos;


import com.pedropathing.follower.Follower;
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
public class FarRedAuto3Balls extends NextFTCOpMode {
    public PathChain Path1;
    public PathChain Path2;

    Command Path1Command ;
    Command Path2Command;
    Command Auto;
    Follower follower;
    List<String> a = new ArrayList<String>();

    public FarRedAuto3Balls() {
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
        follower.setStartingPose(new Pose(56, 8, -90).mirror());
        Path1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(56.000, 8.000).mirror(),

                                new Pose(60.000, 18.000).mirror()
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(MirrorAngle(270)), Math.toRadians(MirrorAngle(265)))

                .build();

        Path2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(60.000, 18.000).mirror(),

                                new Pose(50.000, 35.000).mirror()
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(MirrorAngle(300)), Math.toRadians(MirrorAngle(-210)))

                .build();
        Path1Command = new FollowPath(Path1);
        Path2Command = new FollowPath(Path2);

        Auto = new SequentialGroup(
                Path1Command,
//                ShootFromFar,
                Path2Command
        );
    }

    @Override
    public void onUpdate() {
        DrawingRobot.drawDebug(follower);
        DrawingRobot.drawPoseHistory(follower.getPoseHistory());
        telemetry.addData("pose: ", follower.getPose());
        telemetry.addData("RunTime: ", getRuntime());
        telemetry.addData("Commands: ", a);
        follower.update();
    }
    public Command PrintCommand(String b){
        return new InstantCommand(()->a.add(b));
    }


    public double MirrorAngle(double ang){
        return (180 - ang + 360) % 360;
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
        Auto.schedule();
    }
}
