/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DefaultSoftRobotics extends Command {

  
  private double soft_speed = 0.5;  // Define speed of soft talon
  private boolean END = false;  // Define boolean to control when program finishes

  // Define boolean for limit switch state
  boolean close_limit_switch_hit;  
  boolean far_limit_switch_hit;
  
  public DefaultSoftRobotics() {
    //requires(Robot.softrobotics);  // Require the soft robotics object
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() { 
  
  //TODO: THINK ABOUT USING SENSOR STATES (LIKE FOR THE ARM)
  // Update limit switch state
  //close_limit_switch_hit = Robot.softrobotics.getTalon().getSensorCollection().isFwdLimitSwitchClosed();
  //far_limit_switch_hit = Robot.softrobotics.getTalon().getSensorCollection().isRevLimitSwitchClosed();

  if (close_limit_switch_hit) {  // If closest limit switch is true
    soft_speed = -.5;  // Deflate the soft
  } else if (far_limit_switch_hit) {  // If the further limit switch is true
    soft_speed = .5;  // Inflate the soft
  } 
  
  if ((soft_speed == .5 && close_limit_switch_hit) || (soft_speed == -.5 && far_limit_switch_hit)) {
    soft_speed = 0;  // Set soft-speed to 0
    END = true;  // Set program to finished
  } 
   Robot.hatch.hatchTalon.set(soft_speed);  // Apply soft speed to talon
  
   /*

    THIS IS CODE WE COULD TRY FOR SOFT ROBOTICS -- ESSENTIALLY, IT SAYS TO MOVE THE ARM ACCORDING TO JOYSTIC INPUT
    AND WHEN IT HITS A LIMIT SWITCH, SETS TALON VALUE TO 0

  if(close_limit_switch_hit || far_limit_switch_hit){
    Robot.softrobotics.getTalon().set(0);
  } else{
    Robot.softrobotics.getTalon().set(Robot.m_oi.gunner.getY(Hand.kLeft));
  }
  */
  }

  @Override
  protected boolean isFinished() {

    Robot.hatch.hatchTalon.set(0);  // Set the talon to 0 speed
    return END;

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
