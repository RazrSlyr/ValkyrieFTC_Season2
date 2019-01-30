package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "Auto Blue Up", group = "")
public class AutoBlueUpColor extends LinearOpMode {

    Robot robot = new Robot();

    @Override
    public void runOpMode() {
        robot.initialize(this);
        waitForStart();

        while (opModeIsActive()) {
            double[] blank = new double[]{robot.color.red(), robot.color.green(), robot.color.blue()};
            //  robot.raiseClimber();

            double distance = Math.pow(2, 0.5) * 3/2 * 12;
            robot.encoderDrive(distance,  0.125);

            robot.encoderTurn(-90, 0.125);
            distance = Math.pow(2, 0.5) * 12;
            robot.encoderDrive(distance, 0.125);
            robot.encoderTurn(180, 0.125);

            //left is cube
            if(robot.isOrange(blank)) {
                robot.encoderTurn(-90, 0.125);
                distance = Math.pow(2, 0.5) / 2;
                robot.encoderDrive(distance,  0.125);
                robot.encoderDrive(-distance, 0.125);

                robot.encoderTurn(-45, 0.125);
                robot.encoderDrive(1.9 * 12, 0.125);
                robot.encoderTurn(-90, 0.125);
                robot.encoderDrive(6 * 12, 0.125);
                stop();
            } else {
                distance = Math.pow(2, 0.5) * 12;
                robot.encoderDrive(distance, 0.125);
                //middle is cube
                if (robot.isOrange(blank)) {
                    robot.encoderTurn(-90, .125);
                    distance = Math.pow(2, 0.5) / 2;
                    robot.encoderDrive(distance, 0.125);
                    robot.encoderDrive(-2 * distance, 0.125);
                    robot.encoderTurn(-135, 0.125);
                    robot.encoderDrive(12, 0.125);
                    robot.encoderTurn(90, 0.125);
                    robot.encoderDrive(3 * 12, 0.125);
                    robot.encoderTurn(-90, 0.125);
                    robot.encoderDrive(6 * 12, 0.125);
                    stop();
                } else {
                    //right is cube
                    distance = Math.pow(2, 0.5) * 12;
                    robot.encoderTurn(-90, 0.125);
                    distance = Math.pow(2, 0.5) / 2;
                    robot.encoderDrive(distance, 0.125);
                    robot.encoderDrive(-distance, 0.125);
                    robot.encoderTurn(-135, 0.125);
                    robot.encoderDrive(2 * 12,0.125);
                    robot.encoderTurn(90, 0.125);
                    robot.encoderDrive(4 * 12, 0.125);
                    robot.encoderTurn(-90, 0.125);
                    robot.encoderDrive(6 * 12, 0.125);
                    stop();
                }
            }
        }
    }
}
