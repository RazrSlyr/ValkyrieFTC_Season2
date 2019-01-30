package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "Turn", group = "eee")
public class TurningTest extends LinearOpMode {

    Robot robot = new Robot();
    @Override
    public void runOpMode() {
        robot.initialize(this);
        waitForStart();
        robot.encoderTurn(90, 0.125);
    }
}
