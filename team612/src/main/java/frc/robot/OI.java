/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.*;
//import frc.robot.Autonomous.StrafeLeft;
//import frc.robot.Autonomous.StrafeRight;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commterands and command groups that allow control of the robot.
 */
public class OI {

  // Booleans
  public static boolean ARM_PID                     = true;
  public static boolean CLIMB_PID                   = false;  // never change 
  public static boolean TOGGLE_SERVO_CLIMB_F        = true;
  public static boolean TOGGLE_SERVO_CLIMB_B        = true;
  public static boolean LOCK_DRIVETRAIN             = false;
  public static boolean KILL_CLIMB                  = true;

  // Controllers
  public static XboxController driver               = new XboxController(RobotMap.DRIVER_PORT);
  public static XboxController gunner               = new XboxController(RobotMap.GUNNER_PORT);

  // Driver buttons
  public static JoystickButton driver_button_A    	= new JoystickButton(driver,1);
  public static JoystickButton driver_button_B 		  = new JoystickButton(driver,2);
  public static JoystickButton driver_button_X    	= new JoystickButton(driver,3);
  public static JoystickButton driver_button_Y    	= new JoystickButton(driver,4);
  public static JoystickButton driver_button_LB   	= new JoystickButton(driver,5);
  public static JoystickButton driver_button_RB   	= new JoystickButton(driver,6);
  public static JoystickButton driver_button_BCK  	= new JoystickButton(driver,7);
  public static JoystickButton driver_button_STRT 	= new JoystickButton(driver,8);
  public static JoystickButton driver_button_LJ   	= new JoystickButton(driver,9);
  public static JoystickButton driver_button_RJ   	= new JoystickButton(driver,10);

  // Gunner buttons
  public static JoystickButton gunner_button_A    	= new JoystickButton(gunner,1);
  public static JoystickButton gunner_button_B    	= new JoystickButton(gunner,2);
  public static JoystickButton gunner_button_X    	= new JoystickButton(gunner,3);
  public static JoystickButton gunner_button_Y    	= new JoystickButton(gunner,4);
  public static JoystickButton gunner_button_LB   	= new JoystickButton(gunner,5);
  public static JoystickButton gunner_button_RB   	= new JoystickButton(gunner,6);
  public static JoystickButton gunner_button_BCK  	= new JoystickButton(gunner,7);
  public static JoystickButton gunner_button_STRT 	= new JoystickButton(gunner,8);
  public static JoystickButton gunner_button_LJ   	= new JoystickButton(gunner,9);
  public static JoystickButton gunner_button_RJ   	= new JoystickButton(gunner,10);

  // Servo position
  public static boolean isSideArm                   = false;

  public static boolean KILL_RUMBLE = false;

  // Boolean to tell if in autonomous period
  public static boolean isAutonomous                = false;

  public OI() {

    driver_button_RB.whenPressed(new DriveForward());
    driver_button_A.whenPressed(new ReverseRobot());
    //driver_button_B.whileHeld(new StrafeRight());
    //driver_button_X.whileHeld(new StrafeLeft());
    //driver_button_A.whileHeld(new AutoAlign());
    //driver_button_X.whenPressed(new AutoClimb());
    
  }

}
