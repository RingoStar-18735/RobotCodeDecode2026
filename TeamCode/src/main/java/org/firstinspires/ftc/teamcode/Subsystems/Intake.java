package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.RunToPosition;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.powerable.SetPower;

public class Intake implements Subsystem {
    public final static Intake INSTANCE = new Intake();
    public Intake(){}

    public final ServoEx IntakeLeft = new ServoEx("intakeleft");
    public final ServoEx IntakeRight = new ServoEx("intakeright");

    private ControlSystem controlSystem = ControlSystem.builder()
            .posPid(0.005, 0, 0)
            .elevatorFF(0)
            .build();

    @Override
    public void periodic() {
//        IntakeLeft.setDirection(controlSystem.calculate( new KineticState(IntakeLeft.getVersion())));
        IntakeRight.setPosition(controlSystem.calculate( new KineticState(IntakeRight.getPosition())));
    }

//    public Command intake(double pow){
//        return new SetPower(IntakeLeft, pow).requires(this);
//    }
}
