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

public class DefaultClimb extends Command {

  private final static double CLIMB_SPEED = 0.75;

  public DefaultClimb() {
    requires(Robot.climb);  // Require the climb object
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    // Control the front climb system
    if (OI.driver_button_RB.get()) {

      if (OI.TOGGLE_SERVO_CLIMB_F) {
        Robot.climb.servo_front.setAngle(0);
      } else {
        Robot.climb.servo_front.setAngle(0);
      }
      OI.TOGGLE_SERVO_CLIMB_F = !OI.TOGGLE_SERVO_CLIMB_F;

    } else if(OI.driver_button_RB.get()) {

      if (OI.TOGGLE_SERVO_CLIMB_B) {
        Robot.climb.servo_back.setAngle(180);
      } else {
        Robot.climb.servo_back.setAngle(180);
      }

    } else {

      if (OI.driver_button_Y.get()) {  // Up (Front)
        Robot.climb.lift_talonFront.set(CLIMB_SPEED);
      } else if (OI.driver_button_B.get()) {  // Down (Front)
        Robot.climb.lift_talonFront.set(CLIMB_SPEED * -1);
      } else {
        Robot.climb.lift_talonFront.set(0);
      }

      // Control the back climb system
      if (OI.driver_button_X.get()) {  // Up (Back)
        Robot.climb.lift_talonBack.set(CLIMB_SPEED);
      } else if (OI.driver_button_A.get()) {  // Down (Back)
        Robot.climb.lift_talonBack.set(CLIMB_SPEED * -1);
      } else {
        Robot.climb.lift_talonBack.set(0);
      }
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
