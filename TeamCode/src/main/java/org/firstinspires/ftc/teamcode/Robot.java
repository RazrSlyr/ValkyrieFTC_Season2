package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.*;

import java.sql.Time;
import java.util.Timer;

public class Robot {

    /*
        For auto: Mechs: Elevator, Hook, release marker
     */

    /*
        Intake: Arm thats got two parts that are connected side to side
        that extend outwards that goes up from ground 0 to 180
        inside each tube we have a servo
        each servo is attached to what basically is a wall that can block intake

     */

    private double NUM_TICKS = 100 * 5 / 3.25 * 5 / 6 * 5 / 3 * 10 / 9;
    private double NINETY_DEG = 10 * 90.0 / 100 * 95 / 100 * 90 / 85 * 180 / 140 * 180 / 250 * 180 / 160;
    private double CIRCUMFERENCE = 3.14961 * Math.PI;
    private double RISE_TIME = 100;

    public DcMotor left;
    public DcMotor right;

    public OpMode opMode;

    public ColorSensor cSensor;

    public CRServo intake;

    public DcMotor arm;

    public DcMotor climber;

    public GyroSensor gyro;

    public Servo hook;

    public ColorSensor color;

    public void initialize(OpMode opMode) {
        this.opMode = opMode;

        left = opMode.hardwareMap.dcMotor.get("left");
        right = opMode.hardwareMap.dcMotor.get("right");
        right.setDirection(DcMotorSimple.Direction.REVERSE);


//        color = opMode.hardwareMap.colorSensor.get("color");

        intake = opMode.hardwareMap.crservo.get("intake");

        climber = opMode.hardwareMap.dcMotor.get("climber");

//        gyro = opMode.hardwareMap.gyroSensor.get("gyro");

        arm = opMode.hardwareMap.dcMotor.get("arm");

//        hook = opMode.hardwareMap.servo.get("hook");
    }

    public void encoderDrive(double inches, double power) {
        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        double target = (inches / CIRCUMFERENCE) * NUM_TICKS;

        right.setTargetPosition((int) target);
        left.setTargetPosition((int) target);

        right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        left.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        left.setPower(power * 0.97);
        right.setPower(power);

        while ((left.isBusy() && right.isBusy()) && ((LinearOpMode) opMode).opModeIsActive()) {
            opMode.telemetry.addData("Left Distance Remaining: ", left.getTargetPosition() - left.getCurrentPosition());
            opMode.telemetry.addData("Right Distance Remaining:", right.getTargetPosition() - right.getCurrentPosition());
            opMode.telemetry.update();
        }
        ;

        left.setPower(0);
        right.setPower(0);

        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void gyroTurn(int degrees) {
        degrees = degrees % 360;
        gyro.calibrate();
        //waits for gyro to finish calibrating
        while (gyro.isCalibrating()) ;
        if (degrees <= 180) {
            while (gyro.getHeading() < degrees + 2 && gyro.getHeading() > degrees - 2) {
                left.setPower(0.25);
                right.setPower(-0.25);
            }
        } else {
            while (gyro.getHeading() < degrees + 2 && gyro.getHeading() > degrees - 2) {
                left.setPower(-0.25);
                right.setPower(0.25);
            }
        }

    }


    public void runIntake(double power, double time) {
        intake.setPower(power);
        double tiempo = System.currentTimeMillis();
        while (System.currentTimeMillis() - tiempo < time * 1000) ;
        intake.setPower(0);
    }

    public void encoderTurn(double angle, double speed) {
        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        angle = angle % 360;

        double newAng = (Math.abs(angle) / 90) * NINETY_DEG;
        int distance = (int) ((newAng / CIRCUMFERENCE) * NUM_TICKS);
        if (angle < 0) {
            left.setTargetPosition(-distance);
            right.setTargetPosition(distance);
        } else {
            left.setTargetPosition(distance);
            right.setTargetPosition(-distance);
        }


        left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        left.setPower(speed * 0.97);
        right.setPower(speed);

        while (left.isBusy() || right.isBusy() && ((LinearOpMode) (opMode)).opModeIsActive()) ;

        left.setPower(0);
        right.setPower(0);

        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }

    /*
    public void climberMove(double dPR, double max) {
        double dist =
    }
*/
    public void raiseClimber() {
        climber.setPower(-0.25);
        double timer = System.currentTimeMillis();
        while (System.currentTimeMillis() - timer < RISE_TIME) ;
        climber.setPower(0);
    }

    public void pullClimber() {
        climber.setPower(0.25);
        double timer = System.currentTimeMillis();
        while (System.currentTimeMillis() - timer < RISE_TIME) ;
        climber.setPower(0);
    }

    public boolean isOrange(double[] blank) {
        double red = color.red();
        double green = color.green();
        double blue = color.blue();

        if ((red > blank[0] * 0.7 && red < blank[0] * 1.3) && (green > blank[1] * 0.7 && green < blank[1] * 1.3) && (blue > blank[2] * 0.7 && blue < blank[2] * 1.3)) {
            opMode.telemetry.addData(">", "Empty Space");
            opMode.telemetry.update();
            return false;
        } else if (red > green * 0.7 && red < green * 1.3 && red > blue * 0.7 && red < blue * 1.3) {
            opMode.telemetry.addData(">", "Ball");
            opMode.telemetry.update();
            return false;
        } else {
            opMode.telemetry.addData(">", "Cube");
            opMode.telemetry.update();
            return true;
        }
    }

    public void dropMarker() {

    }


}
