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

public class DefaultHatch extends Command {

  private final double DEADZONE = 0.1;  // Define a trigger axis deadzone

  public DefaultHatch() {
    requires(Robot.hatch);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {

    // Variables to store trigger data, for effiency
    double left_trigger_axis = OI.gunner.getTriggerAxis(Hand.kLeft);
    double right_trigger_axis = OI.gunner.getTriggerAxis(Hand.kRight);

    if (left_trigger_axis > DEADZONE) {  // If the left trigger value is greater than the deadzone
      Robot.hatch.getTalon().set(left_trigger_axis);  // Push out the hatch talon, based on the trigger value
    } else if (right_trigger_axis > DEADZONE) {  // If the right trigger value is greater than the deadzone
      Robot.hatch.getTalon().set(right_trigger_axis * -1);  // Push in the hatch talon, based on the trigger value
    } else {
      Robot.hatch.getTalon().set(0);  // Stop the hatch talon
    }

    // While held logic for hatch servo
    if (OI.gunner_button_X.get()) {  // While the X button is held, open the hatch
      Robot.hatch.getServo().setAngle(100);
    } else {  // Else, reset the hatch back to closed position
      Robot.hatch.getServo().setAngle(0);
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
