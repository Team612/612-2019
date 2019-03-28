/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Autonomous;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.commands.LinetrackerHelper;

public class StrafeRight extends Command {
  double STRAFE_SPEED = .5;
  int direction = 1;

  public StrafeRight() {
    requires(Robot.drivetrain);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    if(OI.isSideArm){
      
       direction = -1;
      }else{
        
        direction = 1;
      }
      if(LinetrackerHelper.center_linetracker_triggered) {
        Robot.drivetrain.getDriveTrain().drivePolar(0, 0, 0);
      } else {
        Robot.drivetrain.getDriveTrain().drivePolar(STRAFE_SPEED, 90 * direction, 0);
      }
     
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
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
