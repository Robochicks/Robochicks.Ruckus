package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Blinker;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
//import com.qualcomm.robotcore.external.Telemetry;

@TeleOp(name="RuckusDriveJava")

public class RuckusDriveJava extends LinearOpMode {
    private Gyroscope imu;
    private Gyroscope imu_1;
    private DcMotor fl;
    private DcMotor fr;
    private DcMotor bl;
    private DcMotor br;
    private DcMotor arm;
    private Blinker expansion_Hub_2;
    private Blinker expansion_Hub_3;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        fl  = hardwareMap.get(DcMotor.class, "FL");
        fr = hardwareMap.get(DcMotor.class, "FR");
        bl = hardwareMap.get(DcMotor.class, "BL");
        br = hardwareMap.get(DcMotor.class, "BR");
        arm = hardwareMap.get(DcMotor.class, "Arm");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        fl.setDirection(DcMotor.Direction.REVERSE);
        fr.setDirection(DcMotor.Direction.FORWARD);
        bl.setDirection(DcMotor.Direction.REVERSE);
        br.setDirection(DcMotor.Direction.FORWARD);
        arm.setDirection(DcMotor.Direction.FORWARD);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Setup a variable for each drive wheel to save power level for telemetry
            double flPower;
            double frPower;
            double blPower;
            double brPower;
            double armPower;

            // Choose to drive using either Tank Mode, or POV Mode
            // Comment out the method that's not used.  The default below is POV.

            // POV Mode uses left stick to go forward, and right stick to turn.
            // - This uses basic math to combine motions and is easier to drive straight.
            double y1 = gamepad1.left_stick_y;
            double x1 = gamepad1.left_stick_x;
            double x2 = gamepad1.right_stick_x;
            double y1Operator = gamepad2.left_stick_y;
            flPower   = Range.clip(y1 + x1 + x2, -1.0, 1.0) ;
            frPower   = Range.clip(y1 - x1 + x2, -1.0, 1.0) ;
            blPower   = Range.clip(y1 - x1 - x2, -1.0, 1.0) ;
            brPower   = Range.clip(y1 + x1 - x2, -1.0, 1.0) ;
            armPower   = Range.clip( y1Operator, -1.0, 1.0);


            // Tank Mode uses one stick to control each wheel.
            // - This requires no math, but it is hard to drive forward slowly and keep straight.
            // leftPower  = -gamepad1.left_stick_y ;
            // rightPower = -gamepad1.right_stick_y ;
            SetDrivePower(flPower, frPower, blPower, brPower, armPower);
            // Send calculated power to wheels


            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "Front Left (%.2f), Front Right (%.2f), " +
                            "Back Left (%.2f), Back Right (%.2f)",
                    flPower, frPower, blPower, brPower);
            telemetry.update();
        }
    }

    private void SetDrivePower(double FrontLeftPower, double FrontRightPower, double BackLeftPower, double BackRightPower, double ArmPower){
        fl.setPower(FrontLeftPower);
        fr.setPower(FrontRightPower);
        bl.setPower(BackLeftPower);
        br.setPower(BackRightPower);
        arm.setPower(ArmPower);

    }

}