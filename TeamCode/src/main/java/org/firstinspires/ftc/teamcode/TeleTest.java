package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="TeleOp", group="Tele")
public class TeleTest extends OpMode {

    boolean rising;
    boolean risen;
    boolean falling;
    boolean fallen;

    double climbTime;

    Robot robot = new Robot();
    public void init() {

        robot.initialize(this);
        rising = false;
        risen = false;
        fallen = true;
        falling = true;
        climbTime = System.currentTimeMillis();
    }

    public void loop() {

        //storing joystick

        //storing joystick values in vars
        double ly = -gamepad1.left_stick_y;
        double ry = -gamepad1.right_stick_y;


        //connecting left stick to the left motor
        robot.left.setPower(ly * 0.9625);

        //connecting right stick to the right motor
        robot.right.setPower(ry);

        double climbPow = 1 * (-gamepad2.right_trigger + gamepad2.left_trigger);

        robot.climber.setPower(climbPow);

        double armPow = gamepad2.right_bumper ? (gamepad2.left_bumper ? 0 : -0.25) : (gamepad2.left_bumper ? 0.25 : 0);

        robot.arm.setPower(armPow);






    }


}
