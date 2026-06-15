package org.firstinspires.ftc.teamcode.Autos;


import static org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem.ShooterSpeed;

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
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@Autonomous
public class AutoRed9BallsFarN extends NextFTCOpMode {
    public PathChain Path1;

    public PathChain Path2;
    public PathChain Path3;
    public PathChain Path4;
    public PathChain Path5;
    public PathChain Path6;
    public PathChain Path7;
    boolean hasStarted = false;
    double distance;

    Command Path1Command;
    Command Path2Command;
    Command Path3Command;
    Command Path4Command;
    Command Path5Command;
    Command Path6Command;
    Command Path7Command;
    Command Auto;
    Follower follower;
    List<String> a = new ArrayList<String>();


    public AutoRed9BallsFarN(){

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

    public Command Shoot() {
        return new LambdaCommand()
                .setStart(() -> {
                    telemetry.addLine("SHOOT COMMAND STARTED");
                    telemetry.addData("vel: ", -ShooterSubsystem.INSTANCE.Shooter.getVelocity());
                    telemetry.addData("Speed: ", ShooterSpeed);
                })
                .setUpdate(() -> {
                    if (Math.abs(ShooterSubsystem.INSTANCE.Shooter.getVelocity()) >= RobotMap.SHOOTER_SPEED_FAR_AUTO - 500) {
                        telemetry.addData("אני2: ", -ShooterSubsystem.INSTANCE.Shooter.getVelocity());
                        IntakeSubSystem.INSTANCE.IntakeMotor.setPower(1);
                        IntakeSubSystem.INSTANCE.TransferMotor.setPower(1);
                    }
                    else {
                        new SequentialGroup(
                                new InstantCommand(()-> IntakeSubSystem.INSTANCE.IntakeMotor.setPower(0)),
                                new InstantCommand(()-> IntakeSubSystem.INSTANCE.TransferMotor.setPower(0)),
                                new Delay(1)
                        );
                    }
                })
                .setIsDone(()-> false)
                .requires(IntakeSubSystem.INSTANCE);
    }
    Command shootSequence = new SequentialGroup(
            new ParallelDeadlineGroup(
                    new Delay(2),
                    ShooterSubsystem.INSTANCE.RunFullSpeed(() ->
                            Math.sqrt(
                                    Math.pow(FieldMap.RED_TARGET_POS.getX() - follower.getPose().getX(), 2) +
                                            Math.pow(FieldMap.RED_TARGET_POS.getY() - follower.getPose().getY(), 2)
                            )
                    )
            ),
            new ParallelDeadlineGroup(
                    new Delay(2),
                    Shoot()
            )
//            ShooterSubsystem.INSTANCE.StopSpeed()
    );


    @Override
    public void onInit() {
        RobotMap.TURRET_ROBOT_DIFRANCE = false;
        RobotBank.Alliance = AllianceType.RED;
        TurretSubsystem.INSTANCE.ResetAngleRight().schedule();
        ShooterSubsystem.INSTANCE.Shooter.setPower(0);
        IntakeSubSystem.INSTANCE.IntakeStop().schedule();
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose(88, 8, Math.toRadians(90)));
        telemetry.addData("pos: ", follower.getPose());
        Path1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(56.000, 8.000).mirror(),

                                new Pose(57, 22.031).mirror()
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(90))

                .build();

        Path2 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(57, 22.031).mirror(),
                                new Pose(60.123, 34.476).mirror(),
                                new Pose(49.931, 37.920).mirror(),
                                new Pose(20.000, 36.000).mirror()
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

                .build();

        Path3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(20.000, 36.000).mirror(),

                                new Pose(57, 22.031).mirror()
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0),Math.toRadians(90))

                .build();

        Path4 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(61.302, 22.031).mirror(),
                                new Pose(11.910, 91.182).mirror(),
                                new Pose(-0.579, 17.240).mirror(),
                                new Pose(10.222, -5.000).mirror(),
                                new Pose(34.700, 10.156).mirror(),
                                new Pose(62.344, 18.427).mirror()
                        )
                ).setTangentHeadingInterpolation()

                .build();

        Path5 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(57, 18.427).mirror(),

                                new Pose(20, 15.000).mirror()
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(90),Math.toRadians(0))

                .build();


        Path1Command = new FollowPath(Path1);
        Path2Command = new FollowPath(Path2);
        Path3Command = new FollowPath(Path3);
        Path4Command = new FollowPath(Path4);
        Path5Command = new FollowPath(Path5);
//        Path6Command = new FollowPath(Path6);
//        Path7Command = new FollowPath(Path7);


        Auto = new SequentialGroup(
                Path1Command,
                new Delay(0.3),
                shootSequence,
                ShooterSubsystem.INSTANCE.StopSpeed(),
                MoveWithoutShooting(Path2Command),
                MoveWithoutShooting(Path3Command),
                shootSequence,
                ShooterSubsystem.INSTANCE.StopSpeed(),
                MoveWithoutShooting(Path4Command),
                shootSequence,
                ShooterSubsystem.INSTANCE.StopSpeed(),
                MoveWithoutShooting(Path5Command)

//                Path1Command,
//                new Delay(0.3),
//                shootSequence,
//                ShooterSubsystem.INSTANCE.StopSpeed(),
//                MoveWithoutShooting(Path2Command),
//                MoveWithoutShooting(Path3Command),
//                shootSequence,
//                ShooterSubsystem.INSTANCE.StopSpeed(),
//                MoveWithoutShooting(Path4Command),
//                shootSequence,
//                ShooterSubsystem.INSTANCE.StopSpeed(),
//                MoveWithoutShooting(Path5Command)
        );
    }


    private Command MoveWithoutShooting(Command path) {
        ShooterSubsystem.INSTANCE.StopSpeed();
        return new SequentialGroup(
                new ParallelGroup(
                        path,
                        IntakeSubSystem.INSTANCE.IntakePower(RobotMap.INTAKE_MOTOR_POWER),
                        IntakeSubSystem.INSTANCE.Transfer(RobotMap.INTAKE_REVERSED_POWER)
                ),
                new Delay(2),
                IntakeSubSystem.INSTANCE.IntakePower(0.2)
        );
    }

    @Override
    public void onUpdate() {
        follower.update();
        distance = Math.sqrt(Math.pow(FieldMap.RED_TARGET_POS.getX() - follower.getPose().getX(), 2) + Math.pow(FieldMap.RED_TARGET_POS.getY() - follower.getPose().getY(), 2));
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
//        ShooterSubsystem.INSTANCE.ServoMoveByField(() ->
//                Math.sqrt(
//                        Math.pow(FieldMap.RED_TARGET_POS.getX() - follower.getPose().getX(), 2) +
//                                Math.pow(FieldMap.RED_TARGET_POS.getY() - follower.getPose().getY(), 2)
//                )
//        ).schedule();
        Auto.schedule();
        RobotBank.Offset = TurretSubsystem.INSTANCE.getAngle();
        RobotBank.LastAutoPos = follower.getPose();
        RobotBank.LastAutoTurretAngle = TurretSubsystem.INSTANCE.getAngle();
    }
}
