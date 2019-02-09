/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.Robot;

// WHEN WE ADD AN INTERNAL CLIMB TARGET WE NEED TO HAVE IT SET IN THIS FILE

public class DefaultClimb extends Command {

  // Endgame time variables
  private final double END_GAME = 30;
  private final double MATCH_LENGTH = 150;

  // The specific values for the encoders (Where we are trying to go)
  private double TOP_ENCODER_POSITION = 400;
  private double BOTTOM_ENCODER_POSITION = 0;

  private double UNLATCH_ANGLE = 0;  // Angle at which the chassis is free

  private boolean IN_PROGRESS = true;  // Boolean to determine when performing a function
  private boolean END = false;  // Boolean to end the command

  private int PHASE = 0;  // Specify what phase of the case functions we are in

  private String PHASE_STRING = "Waiting for next step";  // The string of what phase we are currently

  public DefaultClimb() {
    requires(Robot.climb);  // Require the climb object
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

  }

  // Unlock the chassis to allow it to be lifted
  private void release_servo() {

    Robot.climb.servo_front.setAngle(UNLATCH_ANGLE);  // Set the angle of the servo to the unlatch angle
    if (Robot.climb.servo_front.getAngle() == UNLATCH_ANGLE) {  // If the angle of the servo is at unlatch angle
      IN_PROGRESS = false;  // Finish the release servo function
    }

  }

  private void lift_frame() {  // Set the climb talons to an elevated position at level to the HAB platform

    // Set the lift talons to target apex position
    Robot.climb.lift_talonBack.set(ControlMode.Position, TOP_ENCODER_POSITION);
    Robot.climb.lift_talonFront.set(ControlMode.Position, TOP_ENCODER_POSITION);

    // Ternary statements to determine lift talon positions
    boolean front_lifted = (Robot.climb.lift_talonFront.getSelectedSensorPosition(0) == TOP_ENCODER_POSITION) ? true : false;
    boolean back_lifted = (Robot.climb.lift_talonBack.getSelectedSensorPosition(0) == TOP_ENCODER_POSITION) ? true : false;

    if (front_lifted && back_lifted) {  // Check if the front and back are at the optimal positions
      IN_PROGRESS = false;  // Finish the lift frame function
    }

  }

  private void lift_front_wheels() {  // Returns to wheels back from the elevated position back to the original position

    Robot.climb.lift_talonFront.set(ControlMode.Position, BOTTOM_ENCODER_POSITION);  // Set the talon position back to the bottom

    // Ternary statement to determine if the position of talon is back to normal position
    boolean front_wheel_lifted = (Robot.climb.lift_talonFront.getSelectedSensorPosition(0) == BOTTOM_ENCODER_POSITION) ? true : false; 
    
    if (front_wheel_lifted) {  // Check if the boolean is true
      IN_PROGRESS = false;  // Finish the lift front wheels function
    }

  }

  private void lift_back_wheels() {  // Returns the back wheels back from the elevated position to the original position
    
    Robot.climb.lift_talonBack.set(ControlMode.Position, BOTTOM_ENCODER_POSITION);  // Set the talon position back to the bottom
    
    // Ternary statement to determine if the position of talon is back to normal position
    boolean back_wheel_lifted = (Robot.climb.lift_talonBack.getSelectedSensorPosition(0) == BOTTOM_ENCODER_POSITION) ? true : false;
    
    if (back_wheel_lifted) {  // Check if the boolean is true
      IN_PROGRESS = false;  // Finish the lift back wheels function
    }

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    if (!OI.KILL_CLIMB) {  // Only run if climb is enabled

      if (Timer.getMatchTime() < (MATCH_LENGTH - END_GAME)) {  // Only run this execute loop if the match time is in the endgame
        
        if (IN_PROGRESS) {  // Only run this case statement if a phase function is called
          
          switch( PHASE ) {  // Switch case for the phase step
            case 0:
              PHASE_STRING = "Releasing the Servo";
              release_servo();  // At step 0, firstly, rotate the servo to release the chassis
            case 1:
              PHASE_STRING = "Lifting the Chassis";
              lift_frame();  // At step 1, secondly, lift the entire robot frame up except for the wheels
            case 2:
              PHASE_STRING = "Lifting the Front Wheels";
              lift_front_wheels();  // At step 2, thirdly, lift the front wheels of the robot to drive onto the HAB
            case 3:
              PHASE_STRING = "Lifting the Back Wheels";
              lift_back_wheels();  // At step 3, fourth, lift the back wheels to elevate the entire robot
            default:
              PHASE_STRING = "Climb is Currently not Running";
              System.out.println("Climb has ended");
              END = true;  // Exit the command
          }

        }

        if (!IN_PROGRESS && OI.driver.getAButton()) {  // If we are not in progress and the driver clicks the A button
          
          OI.LOCK_DRIVETRAIN = true;  // Lock the drivetrain
          IN_PROGRESS = true;  // Set in progress to true
          PHASE++;  // Go to the next step
        
        } else {
          OI.LOCK_DRIVETRAIN = false;
        }

        SmartDashboard.putString("Climb Phase", PHASE_STRING);  // Put the current phase string to SmartDashboard

      }

    }

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return END;  // End the command based on the boolean
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
