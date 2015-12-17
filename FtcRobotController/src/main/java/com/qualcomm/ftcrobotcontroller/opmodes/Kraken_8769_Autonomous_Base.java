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

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * TeleOp Mode
 * <p>
 * Enables control of the robot via the gamepad
 */
public class Kraken_8769_Autonomous_Base extends LinearOpMode {

    static String MOTORLF = "motorLF";
    static String MOTORLB = "motorLB";
    static String MOTORRF = "motorRF";
    static String MOTORRB = "motorRB";


    static String MOTORTILT = "liftT";
    static String MOTORUPDOWN = "liftUD";
    static String CONTROLTILT = "controlT";
    static String CONTROLLIFT = "controlUD";


    static String MOTORSWEEPER = "sweep";
    static String MOTORCONVEYOR = "conveyor";

    final static double LIFT_MIN_RANGE  = 0.20;
    final static double LIFT_MAX_RANGE  = 0.90;
    final static double LIFT_PIVOT_MIN_RANGE  = -0.45;
    final static double LIFT_PIVOT_MAX_RANGE  = 0.45;

    DcMotor motorRF;
    DcMotor motorRB;

    DcMotor motorLF;
    DcMotor motorLB;

    DcMotor motorLiftTilt;
    DcMotor motorLiftUpDown;
    DcMotor motorSweeper;
    DcMotor motorConveyor;
    DcMotorController controlLift;

    float sweeper = 0;
    float liftUD = 0;

    public Kraken_8769_Autonomous_Base() {

    }
    public void runOpMode() {
        motorLF = hardwareMap.dcMotor.get(MOTORLF);
        motorLB = hardwareMap.dcMotor.get(MOTORLB);
        motorRF = hardwareMap.dcMotor.get(MOTORRF);
        motorRB = hardwareMap.dcMotor.get(MOTORRB);
//        motorConveyor = hardwareMap.dcMotor.get(MOTORCONVEYOR);
//        motorLiftTilt = hardwareMap.dcMotor.get(MOTORTILT);
//        motorLiftUpDown = hardwareMap.dcMotor.get(MOTORUPDOWN);
        motorSweeper = hardwareMap.dcMotor.get(MOTORSWEEPER);

        //controlLift = hardwareMap.dcMotorController.get(CONTROLLIFT);
        //setDriveMode(DcMotorController.RunMode.RUN_TO_POSITION);


        motorLF.setDirection(DcMotor.Direction.REVERSE);
        motorLB.setDirection(DcMotor.Direction.REVERSE);
        Sweeper(-1);
    }



    public void Sweeper(float direction)
    {
        motorSweeper.setPower(direction);
    }
}