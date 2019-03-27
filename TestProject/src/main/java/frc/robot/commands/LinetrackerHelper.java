/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

public class LinetrackerHelper extends Command {

  private int TAPE_VALUE_ARM = 3840;  // Integer value to determine if over tape
  private int TAPE_VALUE_HATCH = 311;  // Integer value to determine if over tape

  // Initialize line tracker booleans for current state
  public static boolean center_linetracker_triggered;
  public static  boolean right_linetracker_triggered;
  public static  boolean left_linetracker_triggered;


  public LinetrackerHelper() {
    requires(Robot.vision_sensors);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {

    // Swap the linetracker feedback side depending on orientation
    if (OI.isSideArm) {

      right_linetracker_triggered = Robot.vision_sensors.rightLineTracker_ARM.getValue() < TAPE_VALUE_ARM;
      center_linetracker_triggered = Robot.vision_sensors.centerLineTracker_ARM.getValue() < TAPE_VALUE_ARM;
      left_linetracker_triggered = Robot.vision_sensors.leftLineTracker_ARM.getValue() < TAPE_VALUE_ARM;

    } else {

      right_linetracker_triggered = Robot.vision_sensors.rightLineTracker_HATCH.getValue() < TAPE_VALUE_HATCH;
      center_linetracker_triggered = Robot.vision_sensors.centerLineTracker_HATCH.getValue() < TAPE_VALUE_HATCH;
      left_linetracker_triggered = Robot.vision_sensors.leftLineTracker_HATCH.getValue() < TAPE_VALUE_HATCH;

    }
    
    System.out.println(Robot.vision_sensors.leftLineTracker_ARM.getValue() + " | " + Robot.vision_sensors.centerLineTracker_ARM.getValue() + " | " + Robot.vision_sensors.rightLineTracker_ARM.getValue());
    System.out.println(Robot.vision_sensors.leftLineTracker_HATCH.getValue() + " | " + Robot.vision_sensors.centerLineTracker_HATCH.getValue() + " | " + Robot.vision_sensors.rightLineTracker_HATCH.getValue());
    System.out.println(center_linetracker_triggered);
    System.out.println("---------");
    
    if (!OI.KILL_RUMBLE) {
      if(center_linetracker_triggered) {
        OI.driver.setRumble(RumbleType.kLeftRumble, 0.1);
        OI.driver.setRumble(RumbleType.kRightRumble, 0.1);     
      } else{
        OI.driver.setRumble(RumbleType.kLeftRumble, 0);
        OI.driver.setRumble(RumbleType.kRightRumble, 0);  
      }
      
    }

    if (OI.driver_button_STRT.get()) {
      OI.driver.setRumble(RumbleType.kLeftRumble, 0);
      OI.driver.setRumble(RumbleType.kRightRumble, 0);
      OI.KILL_RUMBLE = true;
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
