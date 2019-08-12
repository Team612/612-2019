/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;

public class DefaultDrive extends Command {
  int TAPE_VALUE_ARM = 3840;


  private final double DEADZONE = 0.2;  // Define controller deadzone

  // Variables for mechanum drive
  public double magnitude;  // The power the of the drive system
  public double angle;  // The translation angle relative to the robot's body vector
  public double rotation;  // The rotation is the magnitude of the robot's rotation rate

  // Variables for Joystick movements 
  public double direction_x;
  public double direction_y; 

  public static int invert_robot = 1;  // Integer to invert the robot

  public DefaultDrive() {
    requires(Robot.drivetrain);  // Require the drivetrain object
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  protected void getInput() {  // Fetch the Joystick values, apply inversion if neccesary
    
    String joystick_type = OI.driver.getType().toString();
    
    switch (joystick_type) {
      
      case "kHIDJoystick":

        this.direction_y = OI.driver.getRawAxis(1) * invert_robot;
        this.direction_x = OI.driver.getRawAxis(0) * invert_robot;
        this.rotation = OI.driver.getRawAxis(2);
        break;

      default:

        this.direction_y = OI.driver.getY(Hand.kLeft) * invert_robot;
        this.direction_x = OI.driver.getX(Hand.kLeft) * invert_robot;
        this.rotation = OI.driver.getX(Hand.kRight);
        break;

    }

  }

  protected void doDead() {  // Calculate the DEADZONE of the direction values

    if(Math.abs(direction_x) < DEADZONE){
        direction_x = 0;
    }
    if(Math.abs(direction_y) < DEADZONE){
        direction_y = 0;
    }
    if(Math.abs(rotation) < DEADZONE){
        rotation = 0;
    }

  }

  protected void toPolar() {  // Calculate the Magnitude & Angle of the Mechanum drive

    magnitude = Math.sqrt((direction_x * direction_x) + (direction_y * direction_y)); // sqrt(X^2 + Y^2)
    if (magnitude > 1.0) {  // If Magnitude over 1 set it to 1
        magnitude = 1.0;
    }
    angle = Math.atan2(direction_x , direction_y ) * (180 / Math.PI);  // arctan(y/x) Calculates the angle of the y and x point then converts to radians

  }
 
  protected void set_servo_angle(double angle) {  // Function that will rotate camera servo to specified degree
    Robot.drivercamera.getServo().set(angle);;
  }

  @Override
  protected void execute() {

      if (OI.isSideArm) {
        set_servo_angle(180);  // Rotate the servo to hatch side
        invert_robot = -1;  // Invert the drivetrain
      } else {
        set_servo_angle(0);
        invert_robot = 1;  // Invert the drivetrain
      }
      getInput(); // Fetches Joystick values
      doDead(); // Sets the DEADZONE value
      toPolar(); // Does calculations with Joystick values to drivetrain

      Robot.drivetrain.getDriveTrain().drivePolar(this.magnitude, this.angle, this.rotation); // Pass the calculated drive data into the drivetrain
  
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
  }

  @Override
  protected void interrupted() {
  }
  
}