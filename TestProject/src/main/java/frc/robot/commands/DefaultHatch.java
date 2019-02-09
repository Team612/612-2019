/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.POVConvert;


public class DefaultHatch extends Command {
  
  private final double HATCH_TALON_SPEED = 0.5; // Set the speed of the talon

  public DefaultHatch() {
    requires(Robot.hatch);  // Require the Hatch object
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    if (Robot.gPov.getCardinal() == POVConvert.POV_UP) {  // If the value from the D-Pad is up
      Robot.hatch.hatchTalon.set(HATCH_TALON_SPEED);  // Set the talon to .5 speed
    } else if (Robot.gPov.getCardinal() == POVConvert.POV_DOWN){
      Robot.hatch.hatchTalon.set(HATCH_TALON_SPEED * -1);  // Set the talon to .5 speed in opposite direction
    } else {
      Robot.hatch.hatchTalon.set(0);  // Else, set the talon speed to 0
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
