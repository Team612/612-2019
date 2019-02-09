/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.Arrays;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.VisionListen;

public class AutoAlign extends Command {

  // Define the deadbands or range allowed for each of the movements
  int ANGLE_DEADBAND = 5;
  int POSITION_DEADBAND = 15;
  int TAPE_LENGTH = 15;  // Distance in inches to stop entire line following
  int WALL_STOP = 8;

  // Define local variables to store NetworkTable data
  double currentAngle;
  double currentP1Offset;
  double currentP2Offset;

  // Define KP values constants that slow down motors as intended value is reached
  double KP_POSITION = .02;
  double KP_ANGLE = .025;
  double KP_DISTANCE = .015;

  double KD_POSITION = 0.00003;
  double position_rate = 0;

  // Initialize variables that will be passed into drivetrain (Mecannum)
  double drive_magnitude = 0;
  double drive_angle = 0;
  double drive_rotation = 0;

  // Initialize the position and angle checker booleans
  boolean angle_check;
  boolean position_check;

  // Define a boolean to will control whether or not to follow the line when aligned
  boolean follow_line;

  // Initialize distance value from ultrasonic
  double distance;

  // Initialize the doubles that will the averaged line tracker values
  double leftLineTrackerAverage;
  double centerLineTrackerAverage;
  double rightLineTrackerAverage;
  
  // Initialize line tracker drive variables
  double linetracker_drive_magnitude = 0.5;  // Maintain constant forward movement
  double linetracker_drive_angle;
  double linetracker_drive_rotation;
  double linetracker_drive_correction = 50;  // The value added to the rotation to put robot back on path

  // Threshold to detect the line
  int linetracker_threshold = 3700;

  // Booleans for linetracker data
  boolean overLeftTracker;
  boolean overCenterTracker;
  boolean overRightTracker;

  // Timestamp objects
  Timer position_timer = new Timer();
  double[] first_point = new double[2];
  double[] second_point = new double[2];

  double average_offset = 0;
  double pre_time;
  double pre_offset;
  double delta_time = 0;
  double delta_position = 0;
  double rate = 0;

  boolean recordSecond = false;

  public AutoAlign() {
    
    requires(Robot.auto_align);  // Require the AutoAlign Subsystem
    requires(Robot.linetracker);  // Require the line tracker
    requires(Robot.drivetrain);  // Require the Drivetrain
    position_timer.start();

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.linetracker.ultrasonic.setAutomaticMode(true);  // Set the mode of the UltraSonic sensor
    angle_check = false;  // Initialize angle and position check to false
    position_check = false;  // (Will turn true when validated in functions)
    follow_line = false;

    pre_offset = average_offset;
    pre_time = position_timer.get();

  }

  protected void stop_robot() {  // A cleaner function to stop the robot
    Robot.drivetrain.drivetrain.drivePolar(0, 0, 0);
  }

  private void align_angle() {  // Function to calculate rotation value to align angle

    currentAngle = VisionListen.vision_array[0];  // Fetch the static array from VisionListen (Values from NetworkTables)
    double angle_offset = 90 - Math.abs(currentAngle);  // Calculate angle offset: Offset from 90 degrees
    int direction;  // Initialize direction variable (Either 1 or -1)
    System.out.println("Angle offset: " + angle_offset + " Range: " + ANGLE_DEADBAND);
    angle_check = (angle_offset <= ANGLE_DEADBAND) ? true : false;  // Ternary statement to validate angle range returns true if in range

    if (Math.abs(currentAngle) != 0) {  // Check if angle is not 0 (This is our error angle)
      
      if (currentAngle < 0) {  // Check if angle is negative (Rotate Right) or if its positive (Rotate Left)
        direction = 1;  // Set direction to 1 (Rotate Right)
        System.out.println("Rotate right!");
      } else {
        direction = -1;  // Set direction to -1 (Rotate Left)
        System.out.println("Rotate left!");
      }

      drive_rotation = (KP_ANGLE * angle_offset) * direction;  // Calculate rotation with new direction values

    } else {

      drive_rotation = 0;  // If angle is zero stop the motors

    }

  }

  private void align_position() {  // Function to calculate magnitude and angle value to align position

    // (offset, time)

    currentP1Offset = VisionListen.vision_array[1];  // Extract the two midpoint offsets from the NetworkTable
    currentP2Offset = VisionListen.vision_array[2];
    average_offset = (currentP1Offset+currentP2Offset)/2;  // Calculate the X value of the averages of the points (midpoint of the two points)

    System.out.println("calculate_data: " + VisionListen.calculate_delta);

    if (VisionListen.calculate_delta) {
      delta_time = position_timer.get() - pre_time;
      delta_position = average_offset - pre_offset;
      rate = delta_position/delta_time;

      pre_time = position_timer.get();
      pre_offset = average_offset;

      position_check = (Math.abs(average_offset) <= POSITION_DEADBAND) ? true : false;  // Ternary statement to validate position range returns true if in range

      System.out.println("Delta Time " + delta_time);
      System.out.println("Delta Position " + delta_position);
      System.out.println("D1 " + (KD_POSITION * rate));

      VisionListen.calculate_delta = false;
    } else {
      rate = 0;
    }

    if (Math.abs(average_offset) != 0) {  // Check if average offset is not zero
      
      if (average_offset > 0) {  // Check if average offset is positive (Strafe left!)

        drive_magnitude = (KP_POSITION * Math.abs(average_offset) + (KD_POSITION * rate));  // Calculate magnitude based on KP value
        drive_angle = -90;  // Strafe Left at -90 degrees
        System.out.println("Strafe left!");

      } else {

        drive_magnitude = (KP_POSITION * Math.abs(average_offset) + (KD_POSITION * rate));  // Calculate magnitude based on KP value
        drive_angle = 90;  // Strafe Right at 90 degrees
        System.out.println("Strafe right!");

      }

    }

  }

  protected void align_robot() {
    
    // Call the align angle and position functions to update drive variables
    align_angle();
    //align_position();
    boolean linetracker_triggered = (overLeftTracker || overCenterTracker || overRightTracker) ? true : false;  // If any of the line trackers are enabled return true

    if (linetracker_triggered && angle_check) {  // First Clause: if any line tracker is true and angle check is valid
      stop_robot();  // stop the robot and enable line following mode
      follow_line = true;  // Begin follow line
    } else if (angle_check && position_check && !linetracker_triggered) {  // Second clause: if both vision booleans return true by 
      Robot.drivetrain.drivetrain.drivePolar(.25, 0, 0);  // Drive slow super slowly to maybe trip off a sensor
    } else {
      Robot.drivetrain.drivetrain.drivePolar(drive_magnitude, drive_angle, drive_rotation);  // If nothing keep on aligning
    }

  }

  protected void follow_line() {  // Function to trace and follow the line free from vision support

    if (distance > WALL_STOP) {  // Check if out wall range

      if (overCenterTracker) {  // If center tracker dont apply rotation
        linetracker_drive_rotation = 0;
      }
      if (overLeftTracker) {  // If veering left, apply rotation to go right
        linetracker_drive_rotation = -.20;
      }

      if (overRightTracker) { // If veering right, apply rotation to go left
        linetracker_drive_rotation = .20;
      }

      linetracker_drive_magnitude = KP_DISTANCE * distance;  // Calculate drive_magnitude based on distance

      Robot.drivetrain.drivetrain.drivePolar(linetracker_drive_magnitude, linetracker_drive_angle, linetracker_drive_rotation); // Drive within line tracker data

    } else {
      Robot.drivetrain.drivetrain.drivePolar(0, 0, 0); // Stop the bot we are here!
    }

  }

  protected void update_linetrackers() {

    // Variables that store sampled linetracker data
    leftLineTrackerAverage = Robot.linetracker.leftLineTracker.getAverageValue();
    centerLineTrackerAverage = Robot.linetracker.centerLineTracker.getAverageValue();
    rightLineTrackerAverage = Robot.linetracker.rightLineTracker.getAverageValue();

    System.out.println(leftLineTrackerAverage);
    System.out.println(centerLineTrackerAverage);
    System.out.println(rightLineTrackerAverage);

    // Booleans for line tracker results
    overLeftTracker = (leftLineTrackerAverage <= linetracker_threshold) ? true : false;
    overCenterTracker = (centerLineTrackerAverage <= linetracker_threshold) ? true : false;
    overRightTracker = (rightLineTrackerAverage <= linetracker_threshold) ? true : false;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    align_robot();
    /*
    distance = Robot.Ultra.ultrasonic.getRangeInches();  // Update distance value from ultrasonic
    update_linetrackers(); // Update values from line tracker

    if (distance < TAPE_LENGTH) {  // Check if within range to follow tape
      if (follow_line) {
        follow_line();  // Call line follower code
      } else {
        align_robot();  // Call align_robot code
      }
    } else {
      Robot.drivetrain.getDriveTrain().drivePolar(.50, 0, 0);  // Drive forward until in tape range
    }
    */

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
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
