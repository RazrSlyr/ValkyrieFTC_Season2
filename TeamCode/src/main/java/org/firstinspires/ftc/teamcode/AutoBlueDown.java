package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "AutoBlueDown", group = "")
public class AutoBlueDown extends LinearOpMode {

    Robot robot = new Robot();

    @Override
    public void runOpMode() {
        robot.initialize(this);
        waitForStart();

        while (opModeIsActive()) {

        }
    }
}
