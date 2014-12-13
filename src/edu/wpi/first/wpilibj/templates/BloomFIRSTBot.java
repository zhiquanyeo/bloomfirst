/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.DigitalInput;
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
    
    /*==== Robot Components ====
    What parts the robot has
    */
    
    /*
    Drive train: this is what makes it move
    */
    RobotDrive drivetrain = new RobotDrive(4, 3);
    
    private DigitalInput frontSwitch = new DigitalInput(1);
    private DigitalInput backSwitch = new DigitalInput(2);
    
    
    
    /**
     * This function is called once each time the robot enters autonomous mode.
     */
    public void autonomous() {
        /* This code will run once the referees start the match */
        //Turn safety off since we won't be feeding the watchdog
        drivetrain.setSafetyEnabled(false);
        
        double speed = 0.2;
        
        while (isEnabled() && isAutonomous()) {
            //If we are driving forward AND the front switch is pressed
            //drive backwards
            if (speed > 0 && !frontSwitch.get()) {
                speed = -0.2; //Set reverse
            }
            else if (speed < 0 && !backSwitch.get()) {
                speed = 0.2;
            }
            
            drivetrain.drive(speed, 0);
        }
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {
        
    }
    
    /**
     * This function is called once each time the robot enters test mode.
     */
    public void test() {
    
    }
    
}
