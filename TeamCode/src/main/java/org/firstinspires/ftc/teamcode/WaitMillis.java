package org.firstinspires.ftc.teamcode;

import com.pedropathing.util.Timer;

import dev.nextftc.core.commands.Command;

public class WaitMillis extends Command {

    private final long time;
    private Timer timer;

    public WaitMillis(long time) {
        this.time = time;
    }

    @Override
    public void start() {
        timer = new Timer();
    }

    @Override
    public boolean isDone() {
        return timer.getElapsedTime() >= time;
    }
}