package Tests;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp
public class TXtest extends LinearOpMode {
    private DcMotor motor;
    private Limelight3A limelight;

    @Override
    public void runOpMode() throws InterruptedException {
        motor = hardwareMap.get(DcMotor.class, "Motor");
        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        DcMotor motor =hardwareMap.dcMotor.get("Motor");
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while (opModeIsActive()){
            if (gamepad1.dpad_up){
                motor.setPower(1);

            }

            int position = motor.getCurrentPosition();

            telemetry.addData("Encoder position:", position);
            telemetry.update();
        }

    }
}
