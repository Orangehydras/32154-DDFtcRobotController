package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Turret {

    // Declare motors
    private DcMotor flyRight, flyLeft, intake;
    private Servo h_servo, t_servo;

    public void init(HardwareMap hwMap) {
        // Initialize the motors (the name must match the config in the Driver Station)
        // In this case we need "fly_motor", and "intake_motor"
        flyRight = hwMap.get(DcMotor.class, "flywheel_1");
        flyLeft = hwMap.get(DcMotor.class, "flywheel_2");
        intake = hwMap.get(DcMotor.class, "intake_motor");
        h_servo = hwMap.get(Servo.class, "hood_servo");

        // Set motor direction (reverse if needed)
        flyRight.setDirection(DcMotor.Direction.REVERSE);

        h_servo = hwMap.get(Servo.class, "hood_servo");
        t_servo = hwMap.get(Servo.class, "shoot_servo");
    }

    public void shooter(boolean spin, boolean shoot, boolean stop, boolean reverse, double hoodAngle) {
        // if the button to spin the flywheel is pressed(left bumper), sets the flywheel power from 0.5 to 1.0
        // if the button to shoot is pressed(right bumper), sets the intake power from 0.3 to 0.6

        if (spin) {
            flyRight.setPower(1.0);
            flyLeft.setPower(1.0);
        } else {
            flyRight.setPower(0);
            flyLeft.setPower(0);
        }
        if (shoot) {
            t_servo.setPosition(1.0);
        }
        else {
            t_servo.setPosition(0.0);
        }
        if (stop) {
            intake.setPower(0); // Stop the intake
        }
        else if (reverse) {
            intake.setPower(-0.4); // If reversed, set intake power to -0.4
        } else {
            intake.setPower(1);
        }
        h_servo.setPosition(hoodAngle);


    }
}

