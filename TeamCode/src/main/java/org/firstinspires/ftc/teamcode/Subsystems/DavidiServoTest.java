package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ServoController;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.ServoEx;

public class DavidiServoTest implements Subsystem {
    public final static DavidiServoTest INSTANCE = new DavidiServoTest();
    public DavidiServoTest(){}

    /*public CRServo collectServo = new CRServo("01C") {
        @Override
        public ServoController getController() {
            return null;
        }

        @Override
        public int getPortNumber() {
            return 0;
        }

        @Override
        public void setDirection(Direction direction) {

        }

        @Override
        public Direction getDirection() {
            return null;
        }

        @Override
        public void setPower(double power) {

        }

        @Override
        public double getPower() {
            return 0;
        }

        @Override
        public Manufacturer getManufacturer() {
            return null;
        }

        @Override
        public String getDeviceName() {
            return "";
        }

        @Override
        public String getConnectionInfo() {
            return "";
        }

        @Override
        public int getVersion() {
            return 0;
        }

        @Override
        public void resetDeviceConfigurationForOpMode() {

        }

        @Override
        public void close() {

        }
    };


    public Command spinning(double ){

    }*/

}
