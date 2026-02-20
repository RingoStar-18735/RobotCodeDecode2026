package org.firstinspires.ftc.teamcode;

import java.util.ArrayList;
import java.util.List;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.ftc.ActiveOpMode;

public class Printer {
    String name;
    List<String> a = new ArrayList<String>();
    public Command addPrintCommand(String b){
        return new InstantCommand(()->a.add(b));
    }
    public Printer(String name){
        this.name = name;
    }

    public List<String> getA() {
        return a;
    }
    public Command PrintCommand(){
        return new InstantCommand(()-> ActiveOpMode.telemetry().addData("Printer" + name, a));
    }
}
