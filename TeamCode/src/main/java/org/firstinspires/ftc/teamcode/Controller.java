package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Controller", group=" ")
public class Controller extends OpMode {

    public void init() {

    }

    public void loop() {
        telemetry.addData("ly", gamepad1.left_stick_y);
        telemetry.addData("lx", gamepad1.left_stick_x);
        telemetry.addData("ry", gamepad1.right_stick_y);
        telemetry.addData("rx", gamepad1.right_stick_x);
        telemetry.update();


    }
}
