/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.Arrays;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.VisionListen;

public class AutoAlign extends Command {

  static int ANGLE_DEADBAND = 2;
  static int LOCATION_DEADBAND = 8;
  static double KP_POSITION = .035;
  static double KP_ANGLE = .01;

  static double drive_magnitude;
  static double drive_angle;
  static double drive_rotation;

  static Joystick joy = new Joystick(0);

  public AutoAlign() {
    requires(Robot.auto_align);
    //requires(Robot.drivetrain);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  static void align_angle() {
    double currentAngle = VisionListen.vision_array[0];
    double angle_offset = 90 - Math.abs(currentAngle);

    int direction = -1;

    System.out.println("Run time");
    if (currentAngle != 0) {
      if (angle_offset > ANGLE_DEADBAND) {
        if (currentAngle < 0) {
          direction = 1;
        }
        drive_rotation = (KP_ANGLE * angle_offset) * direction;
      }
      //System.out.println(Arrays.toString());
    } else {
      drive_rotation = 0;
    }

    //System.out.println("Angle: " + currentAngle + " Offset: " + angle_offset + " Direction: " + direction);

  }

  static void align_position() {
    double currentP1Offset = VisionListen.vision_array[1];
    double currentP2Offset = VisionListen.vision_array[2];

    double average = (currentP1Offset+currentP2Offset)/2;
    
    if (average > 0) {
      drive_magnitude = KP_POSITION * Math.abs(average);
      drive_angle = -90;
    } else {
      drive_magnitude = KP_POSITION * Math.abs(average);
      drive_angle = 90;
    }

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    align_angle();
    align_position();
    
    System.out.println(drive_magnitude +" , "+ drive_angle +" , "+ drive_rotation);

    if (joy.getRawButton(1)) {
      Robot.drivetrain.getDriveTrain().drivePolar(drive_magnitude, drive_angle, drive_rotation);
    }
    /*
    if (joy.getRawButton(1)) {
      align_position();
    }

    if (joy.getRawButton(2)) {
      align_angle();
    }
    */

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
