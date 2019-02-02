package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

@Autonomous(name = "Auto Face Pit ID", group = "")
public class AutoFacePitID extends LinearOpMode {

    Robot robot = new Robot();

    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";


    private static final String VUFORIA_KEY = "Afq+yuP/////AAABmeZ83F3WkEfsvNsZpkWDuMAkbwV98FS4qnZkD/UPm2XFeyZsr8MZAHtSciVwBezKiPjOzgfU7nl/b5V/nonY1K7w0Rl56cDT+Hc4bNUsglQUKArgnX8wwufVbtDDR5SDjlkvR6IG5dXkmI0YLubJremFOdRtOTbjQ48CKiPlPo7Od6mZ2kUU1fULqUDjQUK1rWaS3HMeDp9LdoqKJ+oyuHdGnFrkv32hYOFPEuk5arcwClZqzM6nlcpJH8NxvfeccMwpwrTJlI4tNA0h7tSHrSUeBf5L+M6//4Qoq0vQOHjquOMB5DL+J3tQVnnULQsC6LGpMqn7IniXYe4ZhtwKX+T78/OHi2kcLEeebbHT2scU";


    private VuforiaLocalizer vuforia;


    private TFObjectDetector tfod;


    @Override
    public void runOpMode() {

        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
            telemetry.update();
        }


        robot.initialize(this);
        waitForStart();

        if (tfod != null) {
            tfod.activate();
        }


        while (opModeIsActive()) {
            //  robot.raiseClimber();

            double distance = (Math.pow(2, 0.5) * 2 - 2) * 12;
            robot.encoderDrive(distance, 0.125);

            int orange = -1;

            //turn some direction depending on location of phone mount
            robot.encoderTurn(-90, 0.125);
            while (orange == -1 && opModeIsActive()) {
                if (tfod != null) {

                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();

                    float goldX = -1;
                    float silver1X = -1;
                    float silver2X = -1;
                    if (updatedRecognitions != null && updatedRecognitions.size() == 3) {
                        for (Recognition r : updatedRecognitions) {
                            if (r.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                goldX = r.getLeft();
                            } else if (silver1X == -1) {
                                silver1X = r.getLeft();
                            } else {
                                silver2X = r.getLeft();
                            }
                        }
                        if (goldX < silver1X && goldX < silver2X) {
                            orange = 0;
                        } else if (goldX > silver1X && goldX < silver2X) {
                            orange = 1;
                        } else if (goldX < silver1X && goldX > silver2X) {
                            orange = 1;
                        } else {
                            orange = 2;
                        }
                    }
                }
            }

            robot.encoderTurn(90, 0.125);

            distance = (2 - Math.pow(2, 0.5)) * 12;

            robot.encoderTurn(-90, 0.125);
            distance = Math.pow(2, 0.5) * 12;
            robot.encoderDrive(distance, 0.125);
            robot.encoderTurn(180, 0.125);


            //left is cube
            if (orange == 0) {
                robot.encoderTurn(-90, 0.125);
                distance = Math.pow(2, 0.5) / 2;
                robot.encoderDrive(distance, 0.125);
                robot.encoderDrive(-distance, 0.125);

                robot.encoderTurn(-45, 0.125);
                robot.encoderDrive(1.9 * 12, 0.125);
                robot.encoderTurn(-90, 0.125);
                robot.encoderDrive(6 * 12, 0.125);
                robot.dropMarker();
            } else {
                distance = Math.pow(2, 0.5) * 12;
                robot.encoderDrive(distance, 0.125);
                //middle is cube
                if (orange == 1) {
                    robot.encoderTurn(-90, .125);
                    distance = Math.pow(2, 0.5) / 2;
                    robot.encoderDrive(distance, 0.125);
                    robot.encoderDrive(-2 * distance, 0.125);
                    robot.encoderTurn(-135, 0.125);
                    robot.encoderDrive(12, 0.125);
                    robot.encoderTurn(90, 0.125);
                    robot.encoderDrive(3.5 * 12, 0.125);
                    robot.encoderTurn(-90, 0.125);
                    robot.encoderDrive(6 * 12, 0.125);
                    robot.dropMarker();
                } else {
                    //right is cube
                    distance = Math.pow(2, 0.5) * 12;
                    robot.encoderTurn(-90, 0.125);
                    distance = Math.pow(2, 0.5) / 2;
                    robot.encoderDrive(distance, 0.125);
                    robot.encoderDrive(-distance, 0.125);
                    robot.encoderTurn(-135, 0.125);
                    robot.encoderDrive(2 * 12, 0.125);
                    robot.encoderTurn(90, 0.125);
                    robot.encoderDrive(3.5 * 12, 0.125);
                    robot.encoderTurn(-90, 0.125);
                    robot.encoderDrive(6 * 12, 0.125);
                    robot.dropMarker();
                }
            }
            robot.encoderTurn(-90, 0.125);
            robot.encoderDrive(7.5 * 12, 0.125);
        }


    }

    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;


        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }


    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }
}
