package org.firstinspires.ftc.teamcode.Autos;


import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.FieldMap;
import org.firstinspires.ftc.teamcode.Printer;
import org.firstinspires.ftc.teamcode.RobotBank;
import org.firstinspires.ftc.teamcode.RobotMap;
import org.firstinspires.ftc.teamcode.SequentialGroupFixed;
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
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@Autonomous
public class AutoRed9BallsClose extends NextFTCOpMode {
    public PathChain Path1;
    public PathChain Path2;
    public PathChain Path3;
    public PathChain Path4;
    public PathChain Path5;
    public PathChain Path6;
    public PathChain Path7;
    public PathChain Path8;
/*    public PathChain Path9;
    public PathChain Path10;
    public PathChain Path11;*/
    boolean hasStarted = false;

    Command Path1Command;
    Command Path2Command;
    Command Path3Command;
    Command Path4Command;
    Command Path5Command;
    Command Path6Command;
    Command Path7Command;
    Command Path8Command;
//  Command Path9Command;
    Command Path10Command;
    Command Path11Command;
    Command Auto;
    Follower follower;
    List<String> a = new ArrayList<String>();


    public AutoRed9BallsClose(){

        addComponents(
                new SubsystemComponent(
                        ShooterSubsystem.INSTANCE,
                        IntakeSubSystem.INSTANCE,
                        TurretSubsystem.INSTANCE,
                        Printer.INSTANCE
                ),
                new PedroComponent(Constants::createFollower),
                BulkReadComponent.INSTANCE,

                BindingsComponent.INSTANCE
        );

    }

    @Override
    public void onInit() {
        RobotMap.TURRET_ROBOT_DIFRANCE = false;
        RobotBank.Alliance = AllianceType.RED;
        TurretSubsystem.INSTANCE.ResetAngleRight().schedule();
        ShooterSubsystem.INSTANCE.Shooter.setPower(0);
        IntakeSubSystem.INSTANCE.IntakeStop().schedule();
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose(34, 136, Math.toRadians(180)).mirror());
        Path1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(34.000, 136.000).mirror(),

                                new Pose(58.000, 90.000).mirror()
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(MirrorAngle(180)), Math.toRadians(MirrorAngle(180)))

                .build();

        Path2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(58.000, 90.000).mirror(),

                                new Pose(58.000, 64).mirror()
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(MirrorAngle(180)))

                .build();

        Path3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(58.000, 64).mirror(),

                                new Pose(20.000, 64).mirror()
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(MirrorAngle(180)))

                .build();

        Path4 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(20.000, 64).mirror(),
                                new Pose(53.209, 60.177).mirror(),
                                new Pose(58.000, 90.000).mirror()
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(MirrorAngle(180)))

                .build();

        Path5 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(58.000, 90.000).mirror(),

                                new Pose(58.000, 90).mirror()
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(MirrorAngle(180)))

                .build();

        Path6 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(58.000, 90).mirror(),

                                new Pose(25.000, 90).mirror()
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(MirrorAngle(180)))

                .build();

        Path7 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(25.000, 90).mirror(),

                                new Pose(62.000, 90.000).mirror()
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(MirrorAngle(180)))

                .build();

        Path8 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(62.000, 90.000).mirror(),

                                new Pose(33.000, 85.000).mirror()
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(MirrorAngle(180)), Math.toRadians(MirrorAngle(180)))

                .build();


        Path1Command = new FollowPath(Path1);
        Path2Command = new FollowPath(Path2);
        Path3Command = new FollowPath(Path3);
        Path4Command = new FollowPath(Path4);
        Path5Command = new FollowPath(Path5);
        Path6Command = new FollowPath(Path6);
        Path7Command = new FollowPath(Path7);
        Path8Command = new FollowPath(Path8);
//        Path9Command = new FollowPath(Path9);
//        Path10Command = new FollowPath(Path10);
//        Path11Command = new FollowPath(Path11);

//        Command ShootMid = new SequentialGroup(
//                new ParallelDeadlineGroup(
//                        new Delay(1),
//                        ShooterSubsystem.INSTANCE.RunFullSpeedFar()
//                ),
//                ShooterSubsystem.INSTANCE.ServoAimCommand(RobotMap.SERVO_MOVE_AUTO_MID),
//                IntakeSubSystem.INSTANCE.Transfer(RobotMap.TRANSMISSION_MOTOR_POWER),
//                IntakeSubSystem.INSTANCE.IntakePower(RobotMap.INTAKE_MOTOR_POWER),
//                new Delay(2),
//                IntakeSubSystem.INSTANCE.IntakePower(0),
//                IntakeSubSystem.INSTANCE.Transfer(0),
//                new ParallelDeadlineGroup(
//                        new Delay(1),
//                        ShooterSubsystem.INSTANCE.StopSpeed()
//                )
//        );

        Auto = new SequentialGroupFixed(
                Path1Command,
//                ShootMid,
                Path2Command,
                MoveWithoutShooting(Path3Command),
                Path4Command,
//                ShootMid,
                MoveWithoutShooting(Path6Command),
                Path7Command,
//                ShootMid,
                Path8Command
        );



    }

//    Command Shoot =
//            new ParallelDeadlineGroup(
//                    new Delay(RobotMap.INTAKE_SHOOT_TIME_FIRST),
//                    ShooterSubsystem.INSTANCE.RunFullSpeedFar(),
//                    new SequentialGroupFixed(
//                            new Delay(2),
//                            IntakeSubSystem.INSTANCE.IntakePower(RobotMap.INTAKE_MOTOR_POWER),
//                            IntakeSubSystem.INSTANCE.Transfer(RobotMap.TRANSMISSION_MOTOR_POWER)
//                    )
//            );

    private Command MoveWhilePathing(Command path) {
        return new ParallelDeadlineGroup(
                new Delay(4),
                path,
               IntakeSubSystem.INSTANCE.IntakeFullyNotTransfer()
        );
    }

    public double MirrorAngle(double ang){
        return (180 - ang + 360) % 360;
    }

    private Command MoveWithoutShooting(Command path) {
        ShooterSubsystem.INSTANCE.StopSpeed();
        return new SequentialGroup(
                new ParallelGroup(
                        path,
                        IntakeSubSystem.INSTANCE.IntakePower(RobotMap.INTAKE_MOTOR_POWER),
                        IntakeSubSystem.INSTANCE.Transfer(-RobotMap.INTAKE_REVERSED_POWER)
                ),
                new Delay(2),
                IntakeSubSystem.INSTANCE.IntakePower(0),
                IntakeSubSystem.INSTANCE.Transfer(0)
        );

    }

    @Override
    public void onUpdate() {
        follower.update();
        if (hasStarted){
            if (RobotBank.Alliance == AllianceType.BLUE) {
                TurretSubsystem.INSTANCE.FollowPoint(FieldMap.BLUE_TARGET_POS, follower.getPose()).schedule();
            } else if (RobotBank.Alliance == AllianceType.RED) {
                TurretSubsystem.INSTANCE.FollowPoint(FieldMap.RED_TARGET_POS, follower.getPose()).schedule();
            }
        }
        RobotBank.Offset = TurretSubsystem.INSTANCE.getOffset();
        RobotBank.LastAutoPos = follower.getPose();
        RobotBank.LastAutoTurretAngle = TurretSubsystem.INSTANCE.getAngle();
        DrawingRobot.drawDebug(follower);
        DrawingRobot.drawPoseHistory(follower.getPoseHistory());
        telemetry.addData("pose: ", RobotBank.LastAutoPos);
        telemetry.addData("yaw: ", follower.getPose().getHeading());
    }
    @Override
    public void onStartButtonPressed() {
        hasStarted = true;
        Auto.schedule();
        RobotBank.Offset = TurretSubsystem.INSTANCE.getAngle();
        RobotBank.LastAutoPos = follower.getPose();
        RobotBank.LastAutoTurretAngle = TurretSubsystem.INSTANCE.getAngle();
    }
}
