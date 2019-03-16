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

public class LinetrackerHelper extends Command {

  private int TAPE_VALUE = 3700;

  public static boolean center_linetracker_triggered;
  public static  boolean right_linetracker_triggered;
  public static  boolean left_linetracker_triggered;


  public LinetrackerHelper() {
    requires(Robot.vision_sensors);
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
      right_linetracker_triggered = Robot.vision_sensors.rightLineTracker_ARM.getValue() < TAPE_VALUE;
      center_linetracker_triggered = Robot.vision_sensors.centerLineTracker_ARM.getValue() < TAPE_VALUE;
      left_linetracker_triggered = Robot.vision_sensors.leftLineTracker_ARM.getValue() < TAPE_VALUE;
     }else{
       right_linetracker_triggered = Robot.vision_sensors.rightLineTracker_HATCH.getValue() < TAPE_VALUE;
       center_linetracker_triggered = Robot.vision_sensors.centerLineTracker_HATCH.getValue() < TAPE_VALUE;
       left_linetracker_triggered = Robot.vision_sensors.leftLineTracker_HATCH.getValue() < TAPE_VALUE;
     }


     System.out.println(Robot.vision_sensors.leftLineTracker_ARM.getValue() + " | " + Robot.vision_sensors.centerLineTracker_ARM.getValue() + " | " + Robot.vision_sensors.rightLineTracker_ARM.getValue());
     System.out.println(Robot.vision_sensors.leftLineTracker_HATCH.getValue() + " | " + Robot.vision_sensors.centerLineTracker_HATCH.getValue() + " | " + Robot.vision_sensors.rightLineTracker_HATCH.getValue());
     System.out.println("---------");
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
