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

public class StrafeLeft extends Command {
  private boolean center_linetracker_triggered = false;
  private boolean right_linetracker_triggered = false;
  private boolean left_linetracker_triggered = false;
  int TAPE_VALUE = 3750;
  double STRAFE_SPEED = .5;
  int direction = 1;

  public StrafeLeft() {
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

    /*System.out.println(Robot.vision_sensors.centerLineTracker_HATCH.getAverageValue());
    System.out.println(TAPE_VALUE);
    System.out.println("-------");*/
    if(OI.isSideArm){
       right_linetracker_triggered = Robot.vision_sensors.rightLineTracker_ARM.getValue() < TAPE_VALUE;
       center_linetracker_triggered = Robot.vision_sensors.centerLineTracker_ARM.getValue() < TAPE_VALUE;
       left_linetracker_triggered = Robot.vision_sensors.leftLineTracker_ARM.getValue() < TAPE_VALUE;
       direction = 1;
      }else{
        right_linetracker_triggered = Robot.vision_sensors.rightLineTracker_HATCH.getValue() < TAPE_VALUE;
        center_linetracker_triggered = Robot.vision_sensors.centerLineTracker_HATCH.getValue() < TAPE_VALUE;
        left_linetracker_triggered = Robot.vision_sensors.leftLineTracker_HATCH.getValue() < TAPE_VALUE;
        direction = -1;
      }
      System.out.println(Robot.vision_sensors.centerLineTracker_HATCH.getValue() + " | " + TAPE_VALUE);
    System.out.println(center_linetracker_triggered + " | " + direction);
    /*
    if (center_linetracker_triggered) {
      Robot.drivetrain.getDriveTrain().drivePolar(0, 0, 0);
    } else if (right_linetracker_triggered || left_linetracker_triggered) {
      Robot.drivetrain.getDriveTrain().drivePolar(.25, 90 * direction, 0);
    } else {
      Robot.drivetrain.getDriveTrain().drivePolar(.75, 90 * direction, 0);
    }
    */

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
