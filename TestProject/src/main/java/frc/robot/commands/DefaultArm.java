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
import frc.robot.subsystems.Arm;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.GenericHID.Hand;


public class DefaultArm extends Command {

  public static int MAX_POSITION = 500;  // Define to count at end of range of motion degrees
  private double DEADZONE = 0.1;  // Define the joystick deadzone of the gunner
  private double MAX_ANGLE = 90;  // Degree value for unit conversion
  private double ARM_SPEED = 25;

  private double SHOOT_ANGLE = 250;
  
  public DefaultArm() {
    requires(Robot.arm);  // Require the arm object
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  private double countsToAngle() {  // Convert the PID count to an angle value
    double angle = Arm.target * (MAX_ANGLE/MAX_POSITION);  // Unit conversion to find angle value
    return angle;
  }

  private void setArmAngle() {  // Set the arm to a certain position based on Joystick press of gunner

    if (OI.gunner.getYButton()) {
      Arm.target = 0;
    } else if (OI.gunner.getAButton()) {
      Arm.target = MAX_POSITION;
    } else if (OI.gunner.getBButton()) {
      Arm.target = SHOOT_ANGLE;
    }

  }

  @Override
  protected void execute() {

    // Booleans for limit switch results
    boolean top_limit_switch_hit = Robot.arm.talon_arm.getSensorCollection().isFwdLimitSwitchClosed();
    boolean bottom_limit_switch_hit = Robot.arm.talon_arm.getSensorCollection().isRevLimitSwitchClosed();

    if(OI.LIFT_PID) {  // Troubleshooting: Enable or Disable PID

      if (top_limit_switch_hit || bottom_limit_switch_hit) {  // Check if one of the Limit Switches is hit

        if (top_limit_switch_hit) {  // Check if top is triggered
          
        } else if (bottom_limit_switch_hit) {  // Check if bottom is triggered

        }

      } else if(Math.abs(OI.gunner.getY(Hand.kLeft)) > DEADZONE) {  // Filter out the DEADZONE
        Arm.target += (OI.gunner.getY(Hand.kLeft)*ARM_SPEED);  // Increase the PID target value
      }

      Robot.arm.talon_arm.set(ControlMode.Position, Arm.target);  // Set the ARM to a specific position value

    } else {
      System.out.println(Robot.arm.talon_arm.getSelectedSensorPosition(0));
      Robot.arm.talon_arm.set(ControlMode.PercentOutput, OI.gunner.getY(Hand.kLeft));
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
