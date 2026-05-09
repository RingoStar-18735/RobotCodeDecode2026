package org.firstinspires.ftc.teamcode.Teleop;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.FieldMap;
import org.firstinspires.ftc.teamcode.Printer;
import org.firstinspires.ftc.teamcode.Subsystems.AllianceType;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubSystem;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.TurretSubsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import java.util.ArrayList;
import java.util.List;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;


@TeleOp
public class TeleopRingoBlueOG extends NextFTCOpMode {
    Follower follower;
    double newOffset = 0;
    double ServoPos;
    boolean turretRobot = false;
    Pose turretRobotDifrance;
    List<String> a = new ArrayList<String>();
    boolean activateFieldCentricCoraction = false;
    double errorDistance = 0;
    double lastYPos = 0;
    double newYPos;
    Timer timer;
    AllianceType allianceType = AllianceType.BLUE;
    Pose NewTargetPose = FieldMap.BLUE_TARGET_POS;

    public TeleopRingoBlueOG(){
        addComponents(
                new SubsystemComponent(
                        ShooterSubsystem.INSTANCE,
                        IntakeSubSystem.INSTANCE,
                        TurretSubsystem.INSTANCE,
                        DriveSubsystem.INSTANCE,
                        Printer.INSTANCE
                ),
                new PedroComponent(Constants::createFollower),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
        hasStartedMatch = false;
    }


    @Override
    public void onInit() {

    }


    boolean hasStartedMatch;
    @Override
    public void onUpdate() {

        telemetry.addData("fl amper: ",DriveSubsystem.INSTANCE.frontLeftMotor.getMotor().getCurrent(CurrentUnit.AMPS));
        telemetry.addData("fr amper: ",DriveSubsystem.INSTANCE.frontRightMotor.getMotor().getCurrent(CurrentUnit.AMPS));
        telemetry.addData("bl amper: ",DriveSubsystem.INSTANCE.backLeftMotor.getMotor().getCurrent(CurrentUnit.AMPS));
        telemetry.addData("br amper: ",DriveSubsystem.INSTANCE.backRightMotor.getMotor().getCurrent(CurrentUnit.AMPS));
        telemetry.update();
//        telemetry.addData("Pos: ",follower.getPose());
//        telemetry.addData("yaw: ",follower.getPose().getHeading());
//        telemetry.addData("LastAutoPos: ",RobotBank.LastAutoPos);
//        telemetry.addData("turretRobotDifrance: ",turretRobotDifrance);
//        telemetry.addData("Math.toRadians(90): ",Math.toRadians(90));
//        telemetry.addData("getHeading() - Math.toRadians(90): ",follower.getPose().getHeading() + Math.toRadians(90));
//        telemetry.addData("lastPos: ", lastYPos);
//        telemetry.addData("errorDistance: ",errorDistance);
//        telemetry.addData("follower.getPose().getX(): ",follower.getPose().getX());

    }

    @Override
    public void onStartButtonPressed() {
        Command driverControlled = DriveSubsystem.INSTANCE.driverControlled;
        driverControlled.schedule();
    }
}
