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

  private final double DEADZONE = 0.2;  // Define joystick DEADZONE

  // Cimb side speeds
  private final double HATCH_PID_SPEED = 1900;
  private final double ARM_PID_SPEED = 1900;

  private final int target_deadband = 50;

  // Limit switch status booleans
  private boolean limit_switch_top_arm;
  private boolean limit_switch_bottom_arm;
  private boolean limit_switch_top_hatch;
  private boolean limit_switch_bottom_hatch;

  boolean NO_JOYSTICK_PRESS_HATCH = false;
  boolean NO_JOYSTICK_PRESS_ARM = false;

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

    int arm_encoder_position = Robot.climb.getTalon(1).getSelectedSensorPosition(0);
    int hatch_encoder_position = Robot.climb.getTalon(0).getSelectedSensorPosition(0);

    if (OI.CLIMB_PID) {  // If PID is enabled
      
      if (Math.abs(right_joytick_value) > DEADZONE) {  // PID code for hatch side climb
        NO_JOYSTICK_PRESS_HATCH = true;

        if(limit_switch_top_hatch && right_joytick_value > 0) {  // If at top limit switch and still trying to go up, don't change target
          Climb.target_hatch = hatch_encoder_position;
          Robot.climb.getTalon(0).set(ControlMode.Position, Climb.target_hatch);
          System.out.println("TOP AND UP");
        } else if (limit_switch_bottom_hatch && right_joytick_value < 0) {  // If at bottom limit switch and still trying to go down, don't change target
          Climb.target_hatch = hatch_encoder_position;
          Robot.climb.getTalon(0).set(ControlMode.Position, Climb.target_hatch);
          System.out.println("BOTTOM AND DOWN");

        } else {
          Climb.target_hatch  += right_joytick_value * HATCH_PID_SPEED;  // Adjust climb target based on trigger value
          Robot.climb.getTalon(0).set(ControlMode.Position, Climb.target_hatch);
          System.out.println("REGULER");
        }

      } else if (NO_JOYSTICK_PRESS_HATCH) {
        System.out.println("ONE TIME");
        Climb.target_hatch = hatch_encoder_position;
        Robot.climb.getTalon(0).set(ControlMode.Position, Climb.target_hatch);
        NO_JOYSTICK_PRESS_HATCH = false;
      }

      if (Math.abs(left_joytick_value) > DEADZONE) {  // PID code for arm side climb (Same as above)
        NO_JOYSTICK_PRESS_ARM = true;
        if(limit_switch_top_arm && left_joytick_value > 0) {  // If at top limit switch and still trying to go up, don't change target
        Climb.target_arm = arm_encoder_position;
        Robot.climb.getTalon(1).set(ControlMode.Position, Climb.target_arm);
        } else if (limit_switch_bottom_arm && left_joytick_value < 0) {  // If at bottom limit switch and still trying to go down, don't change target
          Climb.target_arm = arm_encoder_position;
          Robot.climb.getTalon(1).set(ControlMode.Position, Climb.target_arm);
        } else {
          Climb.target_arm += left_joytick_value * ARM_PID_SPEED;  // Adjust climb target based on trigger value
          Robot.climb.getTalon(1).set(ControlMode.Position, Climb.target_arm);

        }

      } else if (NO_JOYSTICK_PRESS_ARM) {
        Climb.target_arm = arm_encoder_position;
        Robot.climb.getTalon(1).set(ControlMode.Position, Climb.target_arm);
        NO_JOYSTICK_PRESS_ARM = false;
      }
      
      
      /*
      double difference_arm = Math.abs(Math.abs(Climb.target_arm) - Math.abs(arm_encoder_position));
      double difference_hatch =  Math.abs(Math.abs(Climb.target_hatch) - Math.abs(hatch_encoder_position));

      System.out.println(difference_arm);
      System.out.println(difference_hatch);
      System.out.println();

      if (Math.abs(difference_arm) > target_deadband) {
        Robot.climb.getTalon(1).set(ControlMode.Position, Climb.target_arm);
      }

      if (difference_hatch > target_deadband) {
        Robot.climb.getTalon(0).set(ControlMode.Position, Climb.target_hatch);
      }
      */

    } else {  // If PID is not enabled

      // Left joystick button controls the HATCH side
      if (Math.abs(left_joytick_value) > DEADZONE) {  // If the joystick value is positive
        Robot.climb.getTalon(1).set(ControlMode.PercentOutput, left_joytick_value);  // Set the motor to joystick value
      } else {
        Robot.climb.getTalon(1).set(ControlMode.PercentOutput, 0);  // Stop the motors if no joystick values
      }
      
      // Right joystick button controls the ARM side
      if (Math.abs(right_joytick_value) > DEADZONE) {  // If the joystick value is positive
        Robot.climb.getTalon(0).set(ControlMode.PercentOutput, right_joytick_value);  // Set the motor to joystick value
      } else {
        Robot.climb.getTalon(0).set(ControlMode.PercentOutput, 0);  // Stop the motors if no joystick values
      }

    }

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