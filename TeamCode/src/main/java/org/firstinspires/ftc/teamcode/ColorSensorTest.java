package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "ColorSensorTest", group = "")
public class ColorSensorTest extends OpMode {
    Robot robot = new Robot();
    double[] blank;

    @Override
    public void init() {
        robot.initialize(this);
        robot.color.enableLed(true);
        blank = new double[]{robot.color.red(), robot.color.green(), robot.color.blue()};
    }

    @Override
    public void loop() {
        telemetry.addData("Blue: ", robot.color.blue());
        telemetry.addData("Green: ", robot.color.green());
        telemetry.addData("Red: ", robot.color.red());

        robot.isOrange(blank);


    }

}
