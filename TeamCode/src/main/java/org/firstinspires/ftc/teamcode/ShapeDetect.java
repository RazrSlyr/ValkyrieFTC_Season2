package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.ArrayList;
import java.util.List;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

@Autonomous(name = "Shape Detection", group = "Test")

public class ShapeDetect extends LinearOpMode {
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

        waitForStart();


        if (tfod != null) {
            tfod.activate();
        }

        while (opModeIsActive()) {
            if (tfod != null) {
                // getUpdatedRecognitions() will return null if no new information is available since
                // the last time that call was made.
                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    telemetry.addData("# Object Detected", updatedRecognitions.size());
                    int numGold = 0;
                    int numSilver = 0;
                    for(Recognition r : updatedRecognitions) {
                        if(r.getLabel().equals(LABEL_GOLD_MINERAL)) {
                            numGold++;
                        } else {
                            numSilver++;
                        }
                    }
                    telemetry.addData("Num Golds: ",  numGold);
                    telemetry.addData("Num Silvers: ", numSilver);
                    telemetry.update();
                }
            }
        }


        if (tfod != null) {
            tfod.shutdown();
        }
    }


    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;


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