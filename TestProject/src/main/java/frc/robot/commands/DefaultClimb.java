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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.subsystems.Climb;

public class DefaultClimb extends Command {

  private final double DEADZONE = 0.2;  // Define joystick DEADZONE

  // Cimb side speeds
  private final double HATCH_CLIMB_SPEED = 1000;
  private final double ARM_CLIMB_SPEED = 1000;

  // Limit switch status booleans
  private boolean limit_switch_top_arm;
  private boolean limit_switch_bottom_arm;
  private boolean limit_switch_top_hatch;
  private boolean limit_switch_bottom_hatch;

  public static String HATCH_SIDE_CLIMB_STATUS = "DOWN"; 
  public static String ARM_SIDE_CLIMB_STATUS = "DOWN";


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
    double left_joytick_value = OI.gunner.getY(Hand.kRight) * -1;
    double right_joytick_value = OI.gunner.getY(Hand.kLeft) * -1;

    if (OI.CLIMB_PID) {  // If PID is enabled
      
      if (Math.abs(right_joytick_value) > DEADZONE) {  // PID code for hatch side climb

        if(limit_switch_top_hatch && right_joytick_value > 0){
            HATCH_SIDE_CLIMB_STATUS = "UP";
        } else if (limit_switch_bottom_hatch && right_joytick_value < 0) {
            HATCH_SIDE_CLIMB_STATUS = "DOWN";
        } else {
          Climb.target_hatch += right_joytick_value * HATCH_CLIMB_SPEED;
            if(right_joytick_value > 0){
              HATCH_SIDE_CLIMB_STATUS = "RISING";
            } else if (right_joytick_value < 0) {
              HATCH_SIDE_CLIMB_STATUS = "FALLING";
            } else {
              HATCH_SIDE_CLIMB_STATUS = "STATIONARY";
            }
        }

      }
      if (Math.abs(left_joytick_value) > DEADZONE) {  // PID code for arm side climb
          
        if (limit_switch_top_arm && left_joytick_value > 0 ) {
            ARM_SIDE_CLIMB_STATUS = "UP"; 
        } else if (limit_switch_bottom_arm && left_joytick_value < 0 ) {
            ARM_SIDE_CLIMB_STATUS = "DOWN";
        } else {
          Climb.target_arm += left_joytick_value * ARM_CLIMB_SPEED;
          if (right_joytick_value > 0){
            ARM_SIDE_CLIMB_STATUS = "RISING";
          } else if (right_joytick_value < 0) {
            ARM_SIDE_CLIMB_STATUS = "FALLING";
          } else {
            ARM_SIDE_CLIMB_STATUS = "STATIONARY";
          }
        }

      }

      // Set the PID climb targets
      Robot.climb.getTalon(0).set(ControlMode.Position, Climb.target_hatch);
      Robot.climb.getTalon(1).set(ControlMode.Position, Climb.target_arm);

    } else {  // If PID is not enabled

      // Left joystick button controls the HATCH side
      if (left_joytick_value > DEADZONE) {  // If the joystick value is positive
        Robot.climb.getTalon(1).set(ControlMode.PercentOutput, left_joytick_value);  // Set the motor to joystick value
      } else if (left_joytick_value < DEADZONE) {  // If the joystick value is negative
        Robot.climb.getTalon(1).set(ControlMode.PercentOutput, left_joytick_value);  // Set the motor to joystick value
      } else {
        Robot.climb.getTalon(1).set(ControlMode.PercentOutput, 0);  // Stop the motors if no joystick values
      }
      
      // Right joystick button controls the ARM side
      if (right_joytick_value > DEADZONE) {  // If the joystick value is positive
        Robot.climb.getTalon(0).set(ControlMode.PercentOutput, right_joytick_value);  // Set the motor to joystick value
      } else if (right_joytick_value < DEADZONE) {  // If the joystick value is negative
        Robot.climb.getTalon(0).set(ControlMode.PercentOutput, right_joytick_value);  // Set the motor to joystick value
      } else {
        Robot.climb.getTalon(0).set(ControlMode.PercentOutput, 0);  // Stop the motors if no joystick values
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