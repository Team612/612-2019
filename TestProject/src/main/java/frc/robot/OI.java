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

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  public static boolean DropperPosition = false;
  public static boolean GrabberPosition = false;
  public static boolean LIFT_PID = true ;

  public static XboxController driver = new XboxController(RobotMap.driver_port);
  public static XboxController gunner = new XboxController(RobotMap.gunner_port);

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

    public static JoystickButton gunner_button_A    	= new JoystickButton(gunner, 1);
    public static JoystickButton gunner_button_B    	= new JoystickButton(gunner, 2);
    public static JoystickButton gunner_button_X    	= new JoystickButton(gunner,3);
    public static JoystickButton gunner_button_Y    	= new JoystickButton(gunner,4);
    public static JoystickButton gunner_button_LB   	= new JoystickButton(gunner,5);
    public static JoystickButton gunner_button_RB   	= new JoystickButton(gunner,6);
    public static JoystickButton gunner_button_BCK  	= new JoystickButton(gunner,7);
    public static JoystickButton gunner_button_STRT 	= new JoystickButton(gunner,8);
    public static JoystickButton gunner_button_LJ   	= new JoystickButton(gunner,9);
    public static JoystickButton gunner_button_RJ   	= new JoystickButton(gunner,10);

  public OI(){
    gunner_button_BCK.whenPressed(new DefaultDropper());
    gunner_button_BCK.whenReleased(new DisableDropper());
    gunner_button_RB.whenPressed(new openGrabber());
    gunner_button_RB.whenReleased(new DisableGrabber());
    gunner_button_LB.whenPressed(new closeGrabber());
    gunner_button_LB.whenReleased(new DisableGrabber());
    //driver_button_A.whileHeld(new AutoAlign());

  }

  //// CREATING BUTTONS
  // One type of button is a joystick button which is any button on a
  //// joystick.
  // You create one by telling it which joystick it's on and which button
  // number it is.
  // Joystick stick = new Joystick(port);
  // Button button = new JoystickButton(stick, buttonNumber);

  // There are a few additional built in buttons you can use. Additionally,
  // by subclassing Button you can create custom triggers and bind those to
  // commands the same as any other Button.

  //// TRIGGERING COMMANDS WITH BUTTONS
  // Once you have a button, it's trivial to bind it to a button in one of
  // three ways:

  // Start the command when the button is pressed and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenPressed(new ExampleCommand());

  // Run the command while the button is being held down and interrupt it once
  // the button is released.
  // button.whileHeld(new ExampleCommand());

  // Start the command when the button is released and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenReleased(new ExampleCommand());
}
