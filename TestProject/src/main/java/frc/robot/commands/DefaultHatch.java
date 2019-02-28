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
  public static String HATCH_STATUS;

  public DefaultHatch() {
    requires(Robot.hatch);  // Require the Hatch object
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
  

    // Variables to store trigger data, for effiency
    double left_trigger_axis = OI.gunner.getTriggerAxis(Hand.kLeft);
    double right_trigger_axis = OI.gunner.getTriggerAxis(Hand.kRight);
    if(OI.gunner_button_X.get()){
      Robot.hatch.getServo().set(180);
    }
    if(OI.gunner_button_Y.get()){
      Robot.hatch.getServo().set(0);
    }
    if (left_trigger_axis > DEADZONE) {  // If the left trigger value is greater than the deadzone
      Robot.hatch.hatchTalon.set(left_trigger_axis);  // Push out the hatch talon, based on the trigger value
      HATCH_STATUS = "EXTENDING";
      //Robot.hatch.getServo().set(180);
    } else if (right_trigger_axis > DEADZONE) {  // If the right trigger value is greater than the deadzone
      Robot.hatch.hatchTalon.set(right_trigger_axis * -1);  // Push in the hatch talon, based on the trigger value
      HATCH_STATUS = "RETRACTING";
      //Robot.hatch.getServo().set(180);
    } else {
      Robot.hatch.hatchTalon.set(0);  // Stop the hatch talon
      HATCH_STATUS = "STATIONARY";
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
