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


public class DefaultFly extends Command {

  private final double  DEADZONE = 0;  // Define the controller DEADZONE
  private boolean bottom_limit_switch_hit;
  private boolean state_ball_in_intake  = false;

  public DefaultFly() {
    requires(Robot.flyWheel);  // Requires FlyWheel Object
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    bottom_limit_switch_hit = Robot.arm.getTalon().getSensorCollection().isRevLimitSwitchClosed();
//LB is in
//RB is out

    if (OI.gunner.getY(Hand.kRight) > DEADZONE ) { 
      state_ball_in_intake = false;
      Robot.flyWheel.getTalon().set(1);
    }else if(!bottom_limit_switch_hit){ // If trying to go in and arm is up dont allow flywheel to move in
      Robot.flyWheel.getTalon().set(0);
    } else { //Arm is down
      if(Math.abs(OI.gunner.getY(Hand.kRight)) < DEADZONE){ // Filters out the deadzone
        Robot.flyWheel.getTalon().set(0);
        state_ball_in_intake = false;
      } else if (!Robot.flyWheel.getButton().get()){// trying to pull in and button not pushed
        Robot.flyWheel.getTalon().set(0);
        state_ball_in_intake = true;// ball inside
           // if ball is in and trying to intake
          /*if(state_ball_in_intake &&  OI.gunner_button_LB.get()){
            OI.gunner.setRumble(GenericHID.RumbleType.kLeftRumble, 1); //rumble
            OI.gunner.setRumble(GenericHID.RumbleType.kRightRumble, 1); 
          } else {
            OI.gunner.setRumble(GenericHID.RumbleType.kLeftRumble, 0); //stop rumbling
            OI.gunner.setRumble(GenericHID.RumbleType.kRightRumble, 0); 
          }*/
      } else if (!state_ball_in_intake){//if ball is not in
        Robot.flyWheel.getTalon().set(OI.gunner.getY(Hand.kRight));
        state_ball_in_intake = true;
      } else if (!state_ball_in_intake){
        Robot.flyWheel.getTalon().set(OI.gunner.getY(Hand.kRight)* -1);
      } // else do not change state_ball_in_intake 
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
