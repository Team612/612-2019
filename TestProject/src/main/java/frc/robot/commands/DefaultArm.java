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
  public boolean top_limit_switch_hit;
  public boolean bottom_limit_switch_hit;

  public static int MAX_POSITION = 500;  // Define to count at end of range of motion degrees
  private double DEADZONE = 0.1;  // Define the joystick deadzone of the gunner
  private double MAX_ANGLE = 90;  // Degree value for unit conversion
  private double ARM_SPEED = 100;
  private double SHOOT_ANGLE = 250;
  
  public DefaultArm() {
    requires(Robot.arm);  // Require the arm object
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Arm.target = Robot.arm.getTalon().getSelectedSensorPosition(0);
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
    top_limit_switch_hit = Robot.talonHelper.getArmTop();
    bottom_limit_switch_hit = Robot.talonHelper.getArmBottom();

    

    if (OI.ARM_PID) {  // Troubleshooting: Enable or Disable PID
      if (top_limit_switch_hit && (OI.gunner.getY(Hand.kLeft) > 0)) {  // Check if top is triggered
        System.out.println("We bads");
        // Arm.target = Arm.target; Don't change the target value, stay still
      } else if (bottom_limit_switch_hit && (OI.gunner.getY(Hand.kLeft) < 0)) {  // Check if bottom is triggered
        // Arm.target = Arm.target; Don't change the target value, stay still
        System.out.println("We bads2");
      } else if (Math.abs(OI.gunner.getY(Hand.kLeft)) > DEADZONE) {  // Filter out the DEADZONE
        System.out.println("We goods");
        Arm.target += (OI.gunner.getY(Hand.kLeft)*ARM_SPEED);  // Increase the PID target value
      }
      Robot.arm.getTalon().set(ControlMode.Position,Arm.target);  // Set the ARM to a specific position value

    } else {  // Non PID version

      //System.out.println(Robot.arm.getTalon().getSelectedSensorPosition(0));
      
      if(Math.abs(OI.gunner.getY(Hand.kLeft)) < DEADZONE){  // Filter out the DEADZONE
        Robot.arm.getTalon().set(0);
      } else {  // Else, apply the the normal Joystick value
        Robot.arm.getTalon().set(ControlMode.PercentOutput, OI.gunner.getY(Hand.kLeft));
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
