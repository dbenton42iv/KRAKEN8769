/* Copyright (c) 2014 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * TeleOp Mode
 * <p>
 * Enables control of the robot via the gamepad
 */
public class Kraken_8769 extends OpMode {

    /*
     * Note: the configuration of the servos is such that
     * as the arm servo approaches 0, the arm position moves up (away from the floor).
     * Also, as the claw servo approaches 0, the claw opens up (drops the game element).
     */
    // TETRIX VALUES.

    static String MOTORLF = "mlf"; //Motor left front
    static String MOTORLB = "mlr"; //Motor left rear
    static String MOTORRF = "mrf"; //Motor right front
    static String MOTORRB = "mrr"; //Motor right rear

    static String MOTORARM = "swx";  //Motor arm

    static String SERVOBUCKET = "bkt";

    DcMotor motorRF;
    DcMotor motorRB;

    DcMotor motorLF;
    DcMotor motorLB;

    DcMotor motorArm;

    Servo servoBucket;

    float arm = 0;

    /**
     * Constructor
     */
    public Kraken_8769() {

    }

    /*
     * motor2 move 0.20; loop Code to run when the op mode is first enabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
     */
    @Override
    public void init() {


		/*
		 * Use the hardwareMap to get the dc motors and servos by name. Note
		 * that the names of the devices must match the names used when you
		 * configured your robot and created the configuration file.
		 */

		/*
		 * For the demo Tetrix K9 bot we assume the following,
		 *   There are two motors "motor_1" and "motor_2"
		 *   "motor_1" is on the right side of the bot.
		 *   "motor_2" is on the left side of the bot and reversed.
		 *
		 * We also assume that there are two servos "servo_1" and "servo_6"
		 *    "servo_1" controls the arm joint of the manipulator.
		 *    "servo_6" controls the claw joint of the manipulator.
		 */
        motorLF = hardwareMap.dcMotor.get(MOTORLF);
        motorLB = hardwareMap.dcMotor.get(MOTORLB);
        motorRF = hardwareMap.dcMotor.get(MOTORRF);
        motorRB = hardwareMap.dcMotor.get(MOTORRB);
        servoBucket = hardwareMap.servo.get(SERVOBUCKET);

        motorArm = hardwareMap.dcMotor.get(MOTORARM);

        motorLF.setDirection(DcMotor.Direction.REVERSE);
        motorLB.setDirection(DcMotor.Direction.REVERSE);
    }

    /*
     * This method will be called repeatedly in a loop
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#run()
     */
    @Override
    public void loop() {

		/*
		 * Gamepad 1
		 *
		 * Gamepad 1 controls the motors via the left stick, and it controls the
		 * wrist/claw via the a,b, x, y buttons
		 */

        // throttle: left_stick_y ranges from -1 to 1, where -1 is full up, and
        // 1 is full down
        // direction: left_stick_x ranges from -1 to 1, where -1 is full left
        // and 1 is full right
        float throttle = (float)scaleThrottle(-gamepad1.left_stick_y, gamepad1.right_stick_y);
        float direction = (float)scaleThrottle(gamepad1.left_stick_x, gamepad1.right_stick_y);
        float rightFront = throttle - direction;
        float leftFront = throttle + direction;

        // clip the right/left values so that the values never exceed +/- 1
        rightFront = Range.clip(rightFront, -1, 1);
        leftFront = Range.clip(leftFront, -1, 1);

        motorRF.setPower(rightFront);
        motorRB.setPower(rightFront);
        motorLF.setPower(leftFront);
        motorLB.setPower(leftFront);

       servoBucket.setPosition( (1+ gamepad2.left_stick_y )/ 2 );

        arm = 0;
//      //  conveyor = 0;

        if (gamepad2.left_bumper && (arm >= -1 && arm < 1))
            arm = arm + 1;

        if (gamepad2.right_bumper && (arm <= 1 && arm > -1))
            arm = arm - 1;


        Arm(arm);


		/*
		 * Send telemetry data back to driver station. Note that if we are using
		 * a legacy NXT-compatible motor controller, then the getPower() method
		 * will return a null value. The legacy NXT-compatible motor controllers
		 * are currently write only.
		 */



       // int position = Math.abs (motorArm.getCurrentPosition ());



/*        if (arm == 0)
            telemetry.addData("Sweeper", "Sweeper: Stopped");
        else if (arm > 0)
            telemetry.addData("Sweeper", "Sweeper: Forward");
        else
            telemetry.addData("Sweeper", "Sweeper: Reverse");

        telemetry.addData("Left",  String.format("%.2f", leftFront));
        telemetry.addData("Right", String.format("%.2f", rightFront));
        telemetry.addData("Team:", "Kraken 8769");*/

    }

    public void Arm(float direction)
    {
        motorArm.setPower(direction);
    }

    /*
     * Code to run when the op mode is first disabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
     */
    @Override
    public void stop() {
        motorArm.setPower(0);
    }

    double scaleThrottle(double dStick1, double dStick2)
    {
        double dReturnVal = 0.0;
        double dInputScale = 0.0;

        dInputScale = (dStick2 + 1) / 2;

        if (dInputScale == 0) dInputScale = 0.1;

        dReturnVal = dStick1 * dInputScale;

        return dReturnVal;
    }

    /*
    * This method calculates the exponential value for the stick input
     * such that
     * half stick = 0.50 * 0.50 = 0.25  // half stick = quarter forward
      * quater stick = 0.25 * 0.25 = 0.064 // quarter stick = less than 7 percent forward
      * 3/4 stick = 0.75 * 0.75 = 0.5625 // 3/4 stick == about half throttle
      * full stick = 1 * 1 = 1  // full forward equals full forward
     *
    */
    double getExponential(double dStick1, double dStick2)
    {
        double dReturnVal = 0.0;
        dReturnVal = dStick1 * Math.abs( dStick1 );
        return dReturnVal;
    }

    /*
     * This method scales the joystick input so for low joystick values, the
     * scaled value is less than linear.  This is to make it easier to drive
     * the robot more precisely at slower speeds.
     */
    double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }

}
