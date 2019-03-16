/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

public class DefaultBrotate extends Command {

  private final double DEADZONE = 0.1;  // Define a trigger axis deadzone

  public DefaultBrotate() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.brotate);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    // Variables to store trigger data, for effiency
    double left_trigger_axis = OI.gunner.getTriggerAxis(Hand.kLeft);
    double right_trigger_axis = OI.gunner.getTriggerAxis(Hand.kRight);
    if (left_trigger_axis > DEADZONE) {  // If the left trigger value is greater than the deadzone
      Robot.brotate.hatchTalon.set(left_trigger_axis);  // Push out the hatch talon, based on the trigger value
      //Robot.hatch.getServo().set(180);
    } else if (right_trigger_axis > DEADZONE) {  // If the right trigger value is greater than the deadzone
      Robot.brotate.hatchTalon.set(right_trigger_axis * -1);  // Push in the hatch talon, based on the trigger value
      //Robot.hatch.getServo().set(180);
    } else {
      Robot.brotate.hatchTalon.set(0);  // Stop the hatch talon
    }

    if (OI.gunner_button_X.get()) {
      Robot.brotate.b_servo.setAngle(100);
    } else {
      Robot.brotate.b_servo.setAngle(0);
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
