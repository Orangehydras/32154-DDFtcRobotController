package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@Autonomous(name="3 Artifact Near Auto", group="Auto")

public class NearAuto extends LinearOpMode {
    private DcMotor frontLeft, frontRight, backLeft, backRight, flyRight, flyLeft, intake;

    private IMU imu;

    boolean is_red_alliance = false;

    private Servo t_servo;
    @Override
    public void runOpMode() throws InterruptedException {

        // Map motors
        frontLeft  = hardwareMap.get(DcMotor.class, "top_left_motor");
        frontRight = hardwareMap.get(DcMotor.class, "top_right_motor");
        backLeft   = hardwareMap.get(DcMotor.class, "bottom_left_motor");
        backRight  = hardwareMap.get(DcMotor.class, "bottom_right_motor");
        flyRight = hardwareMap.get(DcMotor.class, "flywheel_1");
        flyLeft = hardwareMap.get(DcMotor.class, "flywheel_2");
        intake = hardwareMap.get(DcMotor.class, "intake_motor");

        // Reverse the right side so forward is forward
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        // reverse one side of the flywheel so both motors spin the correct
        flyRight.setDirection(DcMotor.Direction.REVERSE);

        t_servo = hardwareMap.get(Servo.class, "shoot_servo");

        imu = hardwareMap.get(IMU.class, "imu");

        // Sets the Orientation of the robot so it knows whats the front of the robot and stuff
        // This assumes the hub is mounted on the left side with the logo facing the right, and the USB ports face up.
        RevHubOrientationOnRobot RevOrientation = new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.RIGHT, RevHubOrientationOnRobot.UsbFacingDirection.UP);

        imu.initialize(new IMU.Parameters(RevOrientation));
        imu.resetYaw();

        if (is_red_alliance) {
            telemetry.addData("Alliance", "RED");
        } else {
            telemetry.addData("Alliance", "BLUE");
        }

        telemetry.update();

        waitForStart();

        t_servo.setPosition(1.0);
        // Drive forward for 0.8 seconds
        driveForward(0.3);
        sleep(800);

        // Stop
        driveForward(0);

        //shoots the preloaded balls
        shoot();


        if (is_red_alliance) {
            // turn right 45 degrees
            turn(-45);
        } else {
            // turn left 45 degrees
            turn(45);
        }

        // Drive forward for 0.8 seconds
        driveForward(0.3);
        sleep(800);

        // Stop
        driveForward(0);
    }

    private void driveForward(double power) {
        // All motors same power = forward on mecanum
        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);
    }

    private void turn(double angle) {
        double currentAngle = imu.getRobotYawPitchRollAngles().getYaw();
        double targetAngle = currentAngle + angle;

        while (currentAngle != targetAngle) {
            currentAngle = imu.getRobotYawPitchRollAngles().getYaw();

            // Turn Right
            if (currentAngle < targetAngle) {
                frontLeft.setPower(0.1);
                frontRight.setPower(-0.1);
                backLeft.setPower(0.1);
                backRight.setPower(-0.1);

            // Turn Left
            } else if (currentAngle > targetAngle) {
                frontLeft.setPower(-0.1);
                frontRight.setPower(0.1);
                backLeft.setPower(-0.1);
                backRight.setPower(0.1);
            }
        }

        // Stop
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);

    }

    private void shoot() {
        // spin up flywheel
        flyRight.setPower(0.9);
        flyLeft.setPower(0.9);
        sleep(1500);
        // move stopper out of the way
        t_servo.setPosition(0.0);
        sleep(300);
        //push balls into the flywheel.
        intake.setPower(-1);
        // Waits for 5 seconds to guarantee all balls actually shoot(Can probably be shortened.)
        sleep(5000);
        // Stops flywheel and intake to make sure we don't get a foul for moving after auto ends.
        flyLeft.setPower(0);
        flyRight.setPower(0);
        intake.setPower(0);
    }
}
