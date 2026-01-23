package org.firstinspires.ftc.teamcode.Autos;


import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.RobotMap;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubSystem;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.TurretSubsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import dev.nextftc.core.commands.Command;
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
public class FarBlueAuto extends NextFTCOpMode {
    public PathChain Path1;
    public PathChain Path2;
    public PathChain Path3;
    public PathChain Path4;
    public PathChain Path5;
    public PathChain Path6;
    public PathChain Path7;

    public FarBlueAuto() {
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
        Follower follower = Constants.createFollower(hardwareMap);
        Path1 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(56.000, 8.000),
                                new Pose(50.000, 35.000),
                                new Pose(44.000, 36.000)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();

        Path2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(44.000, 36.000),

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
                        new BezierCurve(
                                new Pose(56.000, 8.000),
                                new Pose(50.000, 55.000),
                                new Pose(44.000, 60.000)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();

        Path5 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(44.000, 60.000),

                                new Pose(8.000, 60.000)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();

        Path6 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(8.000, 60.000),

                                new Pose(56.000, 8.000)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();

        Path7 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(56.000, 8.000),
                                new Pose(50.472, 41.909),
                                new Pose(28.661, 49.007)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();
    }
    Command Path1Command = new FollowPath(Path1);
    Command Path2Command = new FollowPath(Path2);
    Command Path3Command = new FollowPath(Path3);
    Command Path4Command = new FollowPath(Path4);
    Command Path5Command = new FollowPath(Path5);
    Command Path6Command = new FollowPath(Path6);
    Command Path7Command = new FollowPath(Path7);

    Command Shoot = new SequentialGroup(
            new ParallelGroup(
            ShooterSubsystem.INSTANCE.RunFullSpeed(),
            ShooterSubsystem.INSTANCE.ServoAim(RobotMap.SERVO_MOVE_FAR)
            ),
            IntakeSubSystem.INSTANCE.IntakeFullyTransfer().endAfter(RobotMap.INTAKE_FULLY_TIME)
    );

    Command TripleShoot
        = new SequentialGroup(
                Shoot,
                Shoot,
                Shoot
        );


    private Command MoveWhilePathing(Command path){
        return new ParallelDeadlineGroup(
                path,
                IntakeSubSystem.INSTANCE.IntakeFullyNotTransfer()
        );
    }

    Command Auto = new ParallelGroup(
            TurretSubsystem.INSTANCE.LimelightMove(),
            new SequentialGroup(
                    TripleShoot,
                    Path1Command,
                    MoveWhilePathing(Path2Command),
                    Path3Command,
                    TripleShoot,
                    Path4Command,
                    MoveWhilePathing(Path5Command),
                    Path6Command,
                    TripleShoot,
                    Path7Command
            )

    ).endAfter(30);

    @Override
    public void onStartButtonPressed() {
        Auto.schedule();
    }
}
