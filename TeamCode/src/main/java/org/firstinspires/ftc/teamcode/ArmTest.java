package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "ArmTest", group = "")
public class ArmTest extends OpMode {
    Robot robot = new Robot();

    @Override
    public void init() {
        robot.initialize(this);
    }

    @Override
    public void loop() {
        robot.arm.setPower(gamepad2.right_stick_y);
    }

}
