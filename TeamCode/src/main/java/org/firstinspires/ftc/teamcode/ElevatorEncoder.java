package org.firstinspires.ftc.teamcode;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "ElevatorEncoder", group = "")
public class ElevatorEncoder extends OpMode {
    Robot robot = new Robot();

    @Override
    public void init() {
        robot.initialize(this);
        robot.climber.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.climber.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void loop() {
        double climbPow = 1 * (gamepad2.left_stick_y);
        robot.climber.setPower(climbPow);

        telemetry.addData("Position", robot.climber.getCurrentPosition());
        telemetry.update();
    }

}
