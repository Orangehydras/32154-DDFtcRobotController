package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "RunFlyWheel", group = "Examples")
public class FlyWheel extends LinearOpMode {

    // Declare motor
    private DcMotor fly_wheel;

    @Override
    public void runOpMode() {
        // Initialize the motor (the name must match the config in the Driver Station)
        fly_wheel = hardwareMap.get(DcMotor.class, "fly_motor");

        // Set motor direction (reverse if needed)
        fly_wheel.setDirection(DcMotor.Direction.FORWARD);

        // Wait for the user to press PLAY
        waitForStart();

        while (opModeIsActive()) {
            // Sets motor power to 1 when bumper is pressed, and 0 when not
            if (gamepad1.left_bumper) {
                fly_wheel.setPower(1.0);
            } else {
                fly_wheel.setPower(0.0);
            }

            // Show telemetry data
            telemetry.addData("Shooting?", gamepad1.right_bumper);
            telemetry.addData("Motor Power", fly_wheel.getPower());
            telemetry.update();
        }
    }
}
