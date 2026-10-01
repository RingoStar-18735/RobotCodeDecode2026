package org.firstinspires.ftc.teamcode.Training;

import org.firstinspires.ftc.teamcode.IntakeSubsystem;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;
import dev.nextftc.hardware.powerable.SetPower;

public class ShooterSubSystem implements Subsystem {
    public final static IntakeSubsystem INSTANCE = new IntakeSubsystem();
    public ShooterSubSystem() {}
    public MotorEx ShooterMotor = new MotorEx("0C");
    public ServoEx ShooterAngleR = new ServoEx("1C");
    public ServoEx ShooterAngleL = new ServoEx("2C");

public Command ShooterPow(double pow){
    return new SetPower(ShooterMotor, pow);
}
public Command ShooterAngleRpos(double pos){
    return new SetPosition(ShooterAngleR, pos);
    }

public Command ShooterAngleLpos(double pos){
    return new SetPosition(ShooterAngleL, pos);
}
public Command ShooterAngleBoth( double ShooterAnglepos){
    return new ParallelGroup(
            ShooterAngleRpos(ShooterAnglepos),
            ShooterAngleLpos(ShooterAnglepos)

    );
}
public Command ShooterBoth( double ShooterAnglepos, double shooterpow){
     return new ParallelGroup(
             ShooterAngleBoth(ShooterAnglepos),
             ShooterPow(shooterpow)

        );
    }

}
