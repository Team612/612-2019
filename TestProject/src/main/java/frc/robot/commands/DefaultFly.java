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

  private final double  DEADZONE = 0.1;  // Define the controller DEADZONE
  private boolean bottom_limit_switch_hit;

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

    //System.out.println(bottom_limit_switch_hit);

    //One heck of an if-statement
    if (bottom_limit_switch_hit) { // If the bottom limit switch is hit
      if (!Robot.flyWheel.getButton().get()) { // If the ball has hit the button
        Robot.flyWheel.getTalon().set(0); //set the motor so that there is not option for the motor burning out
      } else if (Math.abs(OI.gunner.getY(Hand.kRight)) < DEADZONE) {  // Filter out the DEADZONE
        Robot.flyWheel.getTalon().set(0); 
      } else {  // Else, apply the the normal Joystick value, since the button hasn't hit the ball
        Robot.flyWheel.getTalon().set(OI.gunner.getY(Hand.kRight));
      }
      //System.out.println("HIT");
    } else { // If the bottom limit switch isn't hit
      if (Math.abs(OI.gunner.getY(Hand.kRight)) < DEADZONE) {  // Filter out the DEADZONE
        Robot.flyWheel.getTalon().set(0);
      } else if (OI.gunner.getY(Hand.kRight) > 0) {
        Robot.flyWheel.getTalon().set(OI.gunner.getY(Hand.kRight));
      } else {
        Robot.flyWheel.getTalon().set(0);
      }
     // System.out.println("NOT");
      //System.out.println("NOT");

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
