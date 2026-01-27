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
import org.firstinspires.ftc.teamcode.pedroPathing.DrawingRobot;

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
public class FarBlueAuto extends NextFTCOpMode {
    public PathChain Path1;
    public PathChain Path2;
    public PathChain Path3;
    public PathChain Path4;
    public PathChain Path5;
    Command Path1Command ;
    Command Path2Command;
    Command Path3Command;
    Command Path4Command ;
    Command Path5Command ;
    Command Auto;
    Follower follower;

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
    }


    @Override
    public void onInit() {
        ShooterSubsystem.INSTANCE.StopSpeed().schedule();
        IntakeSubSystem.INSTANCE.IntakeStop().schedule();
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose(62.72700729927008, 9.261313868613128, -90 ));
        Path1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(62.727, 9.261),

                                new Pose(60.300, 24.400)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(-90), Math.toRadians(300))

                .build();

        Path2 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(60.300, 24.400),
                                new Pose(54.339, 35.794),
                                new Pose(40.000, 35.500)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        Path3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(40.000, 35.500),

                                new Pose(9.000, 35.500)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        Path4 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(9.000, 35.500),

                                new Pose(60.300, 24.400)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(300))

                .build();

        Path5 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(60.300, 24.400),

                                new Pose(45.364, 43.323)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(300), Math.toRadians(0))

                .build();
        Path1Command = new FollowPath(Path1);
        Path2Command = new FollowPath(Path2);
        Path3Command = new FollowPath(Path3);
        Path4Command = new FollowPath(Path4);
        Path5Command = new FollowPath(Path5);
        Auto = new SequentialGroup(
                Path1Command,
                ShootFromFar,
                Path2Command,
                MoveWhilePathing(Path3Command),
                Path4Command,
                ShootFromFar,
                Path5Command
        );
    }

    @Override
    public void onUpdate() {
        DrawingRobot.drawDebug(follower);
        DrawingRobot.drawPoseHistory(follower.getPoseHistory());
    }

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
                            ShooterSubsystem.INSTANCE.ServoAim(RobotMap.SERVO_MOVE_CLOSE)
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



    private Command MoveWhilePathing(Command path){
        return new ParallelDeadlineGroup(
                path,
                IntakeSubSystem.INSTANCE.IntakeFullyNotTransfer()
        );
    }



    @Override
    public void onStartButtonPressed() {
        Auto.schedule();
    }
}
