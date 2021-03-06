package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import static org.firstinspires.ftc.teamcode.Autonomous2OpMode.DETACH_FROM_LANDER_TICKS;

@Autonomous(name = "Autonomous Meet1 V1")
public class AutonomousMeet1V1 extends Teapot {

    DcMotor arm1Motor = null;
    ImuDrive imuDrive;

    @Override
    public void runOpMode() throws InterruptedException {
        arm1Motor =initializeArmMotor(1);
        imuDrive = new ImuDrive(this);

        telemetry.addLine("Hardware initialized");
        telemetry.update();
        waitForStart();

        lowerFromLatch();

        pause();
        imuDrive.straight(-2);

        pause();
        double currentAngle = imuDrive.turn(-45);
        telemetry.addData("Current angle is ", currentAngle);
        telemetry.update();

        pause();
        imuDrive.straight(52);
        waitForDriveDone(imuDrive.leftMotor, imuDrive.rightMotor);
        pause();

        imuDrive.turn(45);
        pause();

        imuDrive.straight(70);
        waitForDriveDone(imuDrive.leftMotor, imuDrive.rightMotor);

        telemetry.addLine("Done");
        telemetry.update();
    }

    private void pause() {
        sleep(10);
    }

    private void lowerFromLatch() {
        int currentPosition = arm1Motor.getCurrentPosition();

        int determinedPosition = currentPosition + DETACH_FROM_LANDER_TICKS;
        telemetry.addData("Current is", currentPosition);
        telemetry.addData("Desired is", determinedPosition);
        telemetry.update();
        //Move motor
        arm1Motor.setTargetPosition(determinedPosition);
        while (arm1Motor.isBusy() && !isStopRequested()) {
            telemetry.addData("Position", arm1Motor.getCurrentPosition());
            telemetry.update();
        }

    }

    private DcMotor initializeArmMotor(double power) {
        DcMotor arm1Motor = hardwareMap.dcMotor.get(RobotPart.ARM_1_MOTOR);
        arm1Motor.setPower(0);
        arm1Motor.setDirection(DcMotor.Direction.REVERSE);
        arm1Motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm1Motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm1Motor.setTargetPosition(arm1Motor.getCurrentPosition());
        arm1Motor.setPower(power);
        telemetry.addLine("Arm initialized");
        return arm1Motor;
    }

}


