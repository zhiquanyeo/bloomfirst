/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Timer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class BloomFIRSTBot extends SimpleRobot {
    //Some constants
    private final double RANGEFINDER_SCALE_FACTOR = 0.977; 
    
    /*==== Robot Components ====
    What parts the robot has
    */
    
    /*
    Drive train: this is what makes it move
    */
    RobotDrive drivetrain = new RobotDrive(4, 3);
    
    //Rangefinder
    private AnalogChannel rangefinder = new AnalogChannel(7);
    
    //Simple limit switch
    private DigitalInput limitSwitch = new DigitalInput(1);
    
    //Accelerometer
    private ADXL345_I2C accelerometer = new ADXL345_I2C(1, ADXL345_I2C.DataFormat_Range.k2G);
    
    /*==== Human Input Components ====
    
    */
    Joystick leftJoystick = new Joystick(1);
    Joystick rightJoystick = new Joystick(2);
    
    /**
     * This function is called once each time the robot enters autonomous mode.
     */
    public void autonomous() {
        /* This code will run once the referees start the match */
        //Turn safety off since we won't be feeding the watchdog
        drivetrain.setSafetyEnabled(false);
        
        //Let's set the robot driving forward for a little bit (at a safe speed!)
        drivetrain.drive(0.2, 0);
        
        while (isEnabled() && isAutonomous()) {
            //drive forward until we are 50cm (500mm) away from a target then backup and spin around
            if (getRangefinderDistance() < 500) {
                //Backup for 2 seconds
                drivetrain.drive(-0.1, 0);
                Timer.delay(2.0);
                //Turn for 5 seconds
                drivetrain.drive(0, 0.2);
                Timer.delay(5.0);
                //Reset the driving speed
                drivetrain.drive(0.2, 0);
            }
        }
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {
        drivetrain.setSafetyEnabled(true);
        double accelX, accelY, accelZ;
        while (isEnabled() && isOperatorControl()) {
            //1) Sense
            accelX = accelerometer.getAcceleration(ADXL345_I2C.Axes.kX);
            accelY = accelerometer.getAcceleration(ADXL345_I2C.Axes.kY);
            accelZ = accelerometer.getAcceleration(ADXL345_I2C.Axes.kZ);
        }
    }
    
    /**
     * This function is called once each time the robot enters test mode.
     */
    public void test() {
    
    }
    
    /***** Helper Functions ******/
    private double getRangefinderDistance() {
        //scaling factor is ~0.977mV/mm
        //multiply voltage by 1000 to get mV, then divide by scale factor
        return rangefinder.getVoltage() * 1000 / RANGEFINDER_SCALE_FACTOR;
    }
}
