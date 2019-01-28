package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;

@Autonomous(name="Auto", group="Auto")
public class Auto extends LinearOpMode {
    //this is a test
    Robot robot = new Robot();
    public void runOpMode() {
        //initialization
        robot.initialize(this);
        waitForStart();
        //movement
        while (opModeIsActive()) {
            double time = System.currentTimeMillis();
            robot.right.setPower(1);
            robot.left.setPower(1/* * 0.785*/);
            while(/*System.currentTimeMillis() - time < 2500 && */opModeIsActive());
            robot.right.setPower(0);
            robot.left.setPower(0);
//            robot.left.setPower(1);
//            time = System.currentTimeMillis();
//            while(System.currentTimeMillis() - time < 2500 && opModeIsActive() );
//            robot.left.setPower(0);
            stop();
        }

    }

}
