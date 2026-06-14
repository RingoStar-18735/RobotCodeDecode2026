package org.firstinspires.ftc.teamcode;

import java.util.ArrayList;
import java.util.List;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;

public class Printer implements Subsystem {
    public final static Printer INSTANCE = new Printer();

    List<String> a = new ArrayList<String>();
    public Command addPrintCommand(String b){
        return new InstantCommand(()->a.add(b));
    }
    public Printer(){
    }

    public List<String> getA() {
        return a;
    }
    public Command PrintCommand(){
        return new InstantCommand(()-> ActiveOpMode.telemetry().addData("Printer" , a));
    }

    public Command addToList(String txt) {
        return new InstantCommand(() -> a.add(txt));
    }

    @Override
    public void periodic() {
        ActiveOpMode.telemetry().addData("CommandsStats " , a);
    }
}
