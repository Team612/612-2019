/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.subsystems.Climb;

public class DefaultClimb extends Command {

  private final double DEADZONE = 0.1;  // Define joystick DEADZONE

  // Limit switch status booleans
  private boolean limit_switch_top_arm;
  private boolean limit_switch_bottom_arm;
  private boolean limit_switch_top_hatch;
  private boolean limit_switch_bottom_hatch;


  public DefaultClimb() {
    requires(Robot.climb);  // Require the climb subsystem
  }

  @Override
  protected void initialize() {

    // Initialize target values with current positions
    Climb.target_arm = Robot.climb.getTalon(1).getSelectedSensorPosition(0);
    Climb.target_hatch = Robot.climb.getTalon(0).getSelectedSensorPosition(0);

  }

  @Override
  protected void execute() {

    // Update the status of limit switches
    limit_switch_top_arm = Robot.limit_switch.getClimbTopArm();
    limit_switch_bottom_arm = Robot.limit_switch.getClimbBottomArm();
    limit_switch_top_hatch = Robot.limit_switch.getClimbTopHatch();
    limit_switch_bottom_hatch = Robot.limit_switch.getClimbBottomHatch();

    // Store the values for the joysticks before the logic, for effiency
    double left_joytick_value = OI.gunner.getY(Hand.kLeft);
    double right_joytick_value = OI.gunner.getY(Hand.kLeft);

    if (OI.CLIMB_PID) {  // If PID is enabled

      // PID Code for HATCH Side

      if (limit_switch_top_hatch) {  // If at top limit switch and trying to go up, only allow to go down

        if (left_joytick_value < DEADZONE) {
          Climb.target_hatch -= 10;  // Decrease the hatch PID target
        }
        
      } else if (limit_switch_bottom_hatch) {  // If at bottom limit switch and trying to go up, only allow to go up

        if (left_joytick_value > DEADZONE) {
          Climb.target_hatch += 10;  // Increase the hatch PID target
        }

      } else if (left_joytick_value > DEADZONE) {  // If the joystick value is positive
        Climb.target_hatch += 10;  // Increase the hatch PID target
      } else if (left_joytick_value < DEADZONE) {  // If the joystick value is negative
        Climb.target_hatch -= 10;  // Decrease the hatch PID target
      }
      
      // PID Code for ARM Side

      if (limit_switch_top_arm) {  // If at top limit switch and trying to go up, only allow to go down

        if (right_joytick_value < DEADZONE) {
          Climb.target_arm -= 10;  // Decrease the arm PID target
        }
        
      } else if (limit_switch_bottom_arm) {  // If at bottom limit switch and trying to go up, only allow to go up

        if (right_joytick_value > DEADZONE) {
          Climb.target_arm += 10;  // Increase the arm PID target
        }

      } else if (right_joytick_value > DEADZONE) {  // If the joystick value is positive
        Climb.target_arm += 10;  // Increase the arm PID target
      } else if (right_joytick_value < DEADZONE) {  // If the joystick value is negative
        Climb.target_arm -= 10;  // Decrease the arm PID target
      }

      // Apply the PID target values
      Robot.climb.getTalon(0).set(ControlMode.Position, Climb.target_arm);
      Robot.climb.getTalon(1).set(ControlMode.Position, Climb.target_arm);

    } else {  // If PID is not enabled

      // Left joystick button controls the HATCH side
      if (left_joytick_value > DEADZONE) {  // If the joystick value is positive
        Robot.climb.getTalon(0).set(ControlMode.PercentOutput, left_joytick_value);  // Set the motor to joystick value
      } else if (left_joytick_value < DEADZONE) {  // If the joystick value is negative
        Robot.climb.getTalon(0).set(ControlMode.PercentOutput, left_joytick_value);  // Set the motor to joystick value
      } else {
        Robot.climb.getTalon(0).set(ControlMode.PercentOutput, 0);  // Stop the motors if no joystick values
      }
      
      // Right joystick button controls the ARM side
      if (right_joytick_value > DEADZONE) {  // If the joystick value is positive
        Robot.climb.getTalon(1).set(ControlMode.PercentOutput, right_joytick_value);  // Set the motor to joystick value
      } else if (right_joytick_value < DEADZONE) {  // If the joystick value is negative
        Robot.climb.getTalon(1).set(ControlMode.PercentOutput, right_joytick_value);  // Set the motor to joystick value
      } else {
        Robot.climb.getTalon(1).set(ControlMode.PercentOutput, 0);  // Stop the motors if no joystick values
      }

    }

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
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