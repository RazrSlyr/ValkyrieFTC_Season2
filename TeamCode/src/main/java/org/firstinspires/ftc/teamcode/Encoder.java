package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Encoder", group="Auto")
public class Encoder extends LinearOpMode {
    Robot robot = new Robot();
    public void runOpMode() {
        robot.initialize(this);
        waitForStart();
        while (opModeIsActive()) {
            robot.encoderDrive(25, this, 0.125);
            stop();
        }
    }
}
