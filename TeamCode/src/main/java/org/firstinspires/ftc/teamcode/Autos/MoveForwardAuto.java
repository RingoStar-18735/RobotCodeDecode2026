package org.firstinspires.ftc.teamcode.Autos;


import com.pedropathing.follower.Follower;
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
public class MoveForwardAuto extends NextFTCOpMode {
    public PathChain Path1;
    boolean hasStarted = false;

    Command Path1Command;
    Command Auto;
    Follower follower;
    List<String> a = new ArrayList<String>();


    public MoveForwardAuto(){

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
        RobotBank.Alliance = AllianceType.BLUE          ;
        TurretSubsystem.INSTANCE.ResetAngleRight().schedule();
        ShooterSubsystem.INSTANCE.StopSpeed().schedule();
        IntakeSubSystem.INSTANCE.IntakeStop().schedule();
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose(56, 8, Math.toRadians(180)));
        Path1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(56.000, 8.000),

                                new Pose(56.000, 36.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();


        Path1Command = new FollowPath(Path1);

        Auto = new SequentialGroupFixed(
                Path1Command
        );



    }

    Command Shoot =
            new ParallelDeadlineGroup(
                    new Delay(RobotMap.INTAKE_SHOOT_TIME_FIRST),
                    ShooterSubsystem.INSTANCE.RunFullSpeed(),
                    new SequentialGroupFixed(
                            new Delay(2),
                            IntakeSubSystem.INSTANCE.IntakePower(RobotMap.INTAKE_MOTOR_POWER),
                            IntakeSubSystem.INSTANCE.Transfer(RobotMap.TRANSMISSION_MOTOR_POWER)
                    )
            );

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
