/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class ReverseRobot extends Command {

  public ReverseRobot() {
  }

  @Override
  protected void initialize() {
  }

  private void set_servo_angle(double angle) {  // Function that will rotate camera servo to specified degree
    System.out.println(angle);
    Robot.drivercamera.getServo().set(angle);;
  }

  @Override
  protected void execute() {

    if (OI.isSideArm) {  // If on arm side

      DefaultDrive.invert_robot = -1;  // Invert the drivetrain
      set_servo_angle(180);  // Rotate the servo to hatch side

    } else {

      DefaultDrive.invert_robot = 1;
      set_servo_angle(0);
      
    }

    OI.isSideArm = !OI.isSideArm;  // Set side arm variable to opposite

  }

  @Override
  protected boolean isFinished() {
    return true;
  }

  @Override
  protected void end() {
  }

  @Override
  protected void interrupted() {
  }

}
