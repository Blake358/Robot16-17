package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by blake_shafer on 11/19/16.
 */

@Autonomous(name = "Red Corner- Launch, Ball, Park Center")


public class AutonomousRedCornerLaunchBallParkCenter extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    DcMotor leftDriveMotor;
    DcMotor rightDriveMotor;

    DcMotor leftLauncherMotor;
    DcMotor rightLauncherMotor;

    double leftLaunchPower = 0;
    double rightLaunchPower = 0;
    double launchStartPowerIncrement = 0.004;
    double launchStopPowerIncrement = 0.001;
    double maximumLauncherPower = 0.6;
    double minimumLauncherPower = 0;

    DcMotor beltMotor;

    double beltPower = 0;
    double beltStartPowerIncrement = 0.05;
    double beltStopPowerIncrement = 0.01;
    double maximumForwardBeltPower = 0.3;
    double minimumBeltPower = 0;
    double maximumReverseBeltPower = -1.0;
    double forwardRunningBeltPower = 0.1;
    double reverseRunningBeltPower = -0.1;

    Servo leftArm;
    Servo rightArm;

    final static double leftArmDownPosition = 0.898;
    final static double leftArmUpPosition = 0.349;
    final static double rightArmDownPosition = 0.0;
    final static double rightArmUpPosition = 0.64;

    @Override
    public void runOpMode() throws InterruptedException {

        leftDriveMotor = hardwareMap.dcMotor.get("left_drive");
        rightDriveMotor = hardwareMap.dcMotor.get("right_drive");
        leftDriveMotor.setDirection(DcMotor.Direction.REVERSE);

        leftLauncherMotor = hardwareMap.dcMotor.get("left_launcher");
        rightLauncherMotor = hardwareMap.dcMotor.get("right_launcher");
        rightLauncherMotor.setDirection(DcMotor.Direction.REVERSE);

        beltMotor = hardwareMap.dcMotor.get("conveyor_belt");

        leftArm = hardwareMap.servo.get("left_arm");
        rightArm = hardwareMap.servo.get("right_arm");
        leftArm.setPosition(leftArmDownPosition);
        rightArm.setPosition(rightArmDownPosition);

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {

            leftDriveMotor.setPower(-.5);
            rightDriveMotor.setPower(-.5);
//distance to first line to launch
            sleep(2100);

            leftDriveMotor.setPower(0);
            rightDriveMotor.setPower(0);

            while (leftLaunchPower < maximumLauncherPower || rightLaunchPower < maximumLauncherPower) {
                if (leftLaunchPower < maximumLauncherPower) {
                    leftLaunchPower = leftLaunchPower + launchStartPowerIncrement;
                }
                if (rightLaunchPower < maximumLauncherPower) {
                    rightLaunchPower = rightLaunchPower + launchStartPowerIncrement;
                }
                leftLauncherMotor.setPower(leftLaunchPower);
                rightLauncherMotor.setPower(rightLaunchPower);
            }

//time for launcher to start
            sleep(1000);

            beltMotor.setPower(maximumForwardBeltPower);

// time for belt to run
            sleep(7000);

            beltMotor.setPower(0);

            while (leftLaunchPower > 0 || rightLaunchPower > 0) {
                if (leftLaunchPower > 0) {
                    leftLaunchPower = leftLaunchPower - launchStopPowerIncrement;
                }
                if (rightLaunchPower > 0) {
                    rightLaunchPower = rightLaunchPower - launchStopPowerIncrement;
                }
                leftLauncherMotor.setPower(leftLaunchPower);
                rightLauncherMotor.setPower(rightLaunchPower);
            }

            leftDriveMotor.setPower(-.5);
            rightDriveMotor.setPower(-.5);
//time to go forward from launch point to the yoga ball / center vortex
            sleep(1500);

            leftDriveMotor.setPower(0);
            rightDriveMotor.setPower(0);

            leftArm.setPosition(leftArmUpPosition);
            rightArm.setPosition(rightArmUpPosition);

//time to stop and raise arms to knock yoga ball off center vortex
            sleep(1600);

            leftDriveMotor.setPower(-.3);
            rightDriveMotor.setPower(.3);
//time to turn LEFT
            sleep(300);
            leftDriveMotor.setPower(0);
            rightDriveMotor.setPower(0);
//sits still after turning before moving forward
            sleep(300);


            leftDriveMotor.setPower(-.5);
            rightDriveMotor.setPower(-.5);
//time to go forward on to the center vortex
            sleep (900);

            leftDriveMotor.setPower(0);
            rightDriveMotor.setPower(0);

//stops OpMode
            requestOpModeStop();
        }
    }
}

