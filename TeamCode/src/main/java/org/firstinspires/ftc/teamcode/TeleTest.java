package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="TeleOp", group="Tele")
public class TeleTest extends OpMode {

    private double maxArm;
    private boolean dPadUpDown;
    private boolean dPadDownDown;

    Robot robot = new Robot();
    public void init() {

        robot.initialize(this);
        maxArm = 0.5;
    }

    public void loop() {

        //storing joystick

        //storing joystick values in vars
        double ly = -gamepad1.left_stick_y;
        double ry = -gamepad1.right_stick_y;
        double lx = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x;


        //connecting left stick to the left motor
        robot.left.setPower(Math.pow((ly * ly + lx * lx), 0.5)  * 0.9625);

        //connecting right stick to the right motor
        robot.right.setPower(Math.pow((ly * ly - lx * lx), 0.5));

        double climbPow = 1 * (gamepad2.left_stick_y);

        robot.climber.setPower(climbPow);

        double armPow = gamepad2.right_bumper ? (gamepad2.left_bumper ? 0 : -0.25) : (gamepad2.left_bumper ? 0.25 : 0);



        if(gamepad2.dpad_up && !dPadUpDown) {
            dPadUpDown = true;
        }

        if(gamepad2.dpad_down && !dPadDownDown) {
            dPadDownDown = true;
        }

        if(dPadUpDown && !gamepad2.dpad_up) {
            maxArm = Math.min(1, maxArm + 0.25);
        }

        if(dPadDownDown && !gamepad2.dpad_down) {
            maxArm = Math.max(-1, maxArm - 0.25);
        }

        double intakePow = gamepad2.right_trigger - gamepad2.left_trigger;
        robot.intake.setPower(intakePow);








    }


}
