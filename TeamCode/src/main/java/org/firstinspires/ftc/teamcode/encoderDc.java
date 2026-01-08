package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp
public class encoderDc extends OpMode {
    public static final encoderDc INSTANCE = new encoderDc();
    private void encoder_Dc() {}



    MotorEx motorDC = new MotorEx(dcMotorEx);
    double ticks = 103.8;



    @Override
    public void init (){
        motor = hardwareMap.get(DcMotorEx.class , "motor");
        telemetry.addData("Hardware" , "initialized");
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



    }
    @Override
    public void loop () {
        if (gamepad1.a){
            encoder(2);

        }

    }
    public void encoder( int turnage){
        //motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        double newTarget = ticks / turnage;
        motor.setTargetPosition((int) newTarget);
        motor.setPower(0.2);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        
    }



}
