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
import edu.wpi.first.wpilibj.GenericHID;

public class DefaultDrive extends Command {
  final double DEADZONE = 0.07;
  public static double magnitude;
  public static double angle;
  public static double rotation;
  double direction_x;
  double direction_y; 
  public DefaultDrive() {
    requires(Robot.drivetrain);

    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  protected  void doDead(){
    if(Math.abs(direction_x) < DEADZONE){
        direction_x =0;
    }
    if(Math.abs(direction_y) < DEADZONE){
        direction_y=0;
    }
    if(Math.abs(rotation) < DEADZONE){
        rotation =0;
    }
}
protected void getInput() {

    direction_y = OI.driver.getX(GenericHID.Hand.kLeft) ;
    direction_x = OI.driver.getY(GenericHID.Hand.kLeft) *-1;
    rotation    = OI.driver.getX(GenericHID.Hand.kRight);

}
protected void toPolar() {
    magnitude = Math.sqrt(direction_x * direction_x + direction_y * direction_y);
    if (magnitude > 1.0) {
        magnitude = 1.0;
    }
    angle = Math.atan2(direction_y, direction_x) * 180 / Math.PI;
}
  @Override
  protected void execute() {
    getInput();
    toPolar();
    doDead();
    Robot.drivetrain.getDriveTrain().drivePolar(magnitude, angle, rotation);
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
