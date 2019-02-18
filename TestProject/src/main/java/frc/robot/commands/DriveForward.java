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

public class DriveForward extends Command {

  // Variables to control time and speed
  int SECONDS = 4;
  double DRIVE_MAGNITUDE = .99;

  public DriveForward() {
    requires(Robot.drivetrain);  // Require the drivetrain subsystem
  }

  @Override
  protected void initialize() {
    setTimeout(SECONDS);  // Set an X amount of timeout for drive forward
  }

  @Override
  protected void execute() {

    if (OI.isAutonomous) {  // Only run drive forward in autonomous
      Robot.drivetrain.getDriveTrain().drivePolar(DRIVE_MAGNITUDE, 0, 0);  // Drive forward for X amount of magnitude
    }

  }

  @Override
  protected boolean isFinished() {
    return true;  // Since there is a timeout, end after one iteration
  }

  @Override
  protected void end() {
    Robot.drivetrain.getDriveTrain().drivePolar(0,0,0);
  }

  @Override
  protected void interrupted() {
  }
  
}
