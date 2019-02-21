/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.VisionListen;

public class AutoAlign extends Command {

  // Variables to store current vision devices
  private VisionListen current_vision_listen;  // Initialize the array to store the current vision data from NetworkTables
  private double[] current_vision_array;
  private Ultrasonic current_ultrasonic;  // Initialize the ultrasonic object to reference current ultrasonic (Hatch or Arm side)
  private AnalogInput current_left_line_tracker, current_center_line_tracker, current_right_line_tracker;  // Initialize the linetracker objects to reference current linetrackers (Hatch or Arm side)

  public static double drive_magnitude, drive_angle, drive_rotation;  // Initialize doubles to store the calulcated drive values from the offsets 
  private double linetracker_drive_magnitude, linetracker_drive_rotation;
  
  // Booleans to store position and angle state
  private boolean ANGLE_CHECK;
  private boolean POSITION_CHECK;
  private boolean FOLLOW_LINE;

  private double leftLineTrackerAverage, centerLineTrackerAverage, rightLineTrackerAverage;  // Initialize variables to store line tracker values
  private boolean overLeftTracker, overCenterTracker, overRightTracker;  // Initialize booleans to store linetracker states

  private double distance;  // Double to store the current distance from the ultrasonics

  // Deadband values for angle, position and line trackers
  private int ANGLE_DEADBAND = 5;
  private int POSITION_DEADBAND = 100;
  private double LINETRACKER_DEADBAND = 3700;

  // Define KP & KD values for PID
  private double KP_ANGLE = .0095;  // KP value for speed of angle displacement
  private double KP_POSITION = .0025;  // KP value for speed of position displacement
  private double KP_DISTANCE = .015;  // KP value for speed of distance from wall (Slow down as approaching wall)
  private double KD_POSITION = 0; // KD value for slope over time of position offset

  // Derivative function values for position offset
  private double pre_time;
  private double pre_offset;
  private double delta_time;
  private double delta_position;
  private double rate;
  
  // Offset values computed through NetworkTables
  public static double average_offset;  // Offset for position
  public static double angle_offset;  // Offset for angle

  private double LINE_RANGE = 18;  // The range in which the linetrackers could be over a tape
  private double WALL_STOP = 2;  // The distance in which the robot should stop to prevent hitting the wall

  private Timer position_timer = new Timer();  // Timer object to measure derivative rate

  public AutoAlign() {
    
    requires(Robot.vision_sensors);  // Require the line tracker subsystem
    requires(Robot.drivetrain);  // Require the Drivetrain subsystem

    position_timer.start();  // Start the position timer (for derivative)

  }

  @Override
  protected void initialize() {

    update_devices();  // Update the variables references of devices

    // Reset the state values every iteration
    ANGLE_CHECK = false;
    POSITION_CHECK = false;
    FOLLOW_LINE = false;

    pre_offset = average_offset;
    pre_time = position_timer.get();  // Assign the initial value from the timer to double

  }

  private void stop_robot() {  // A cleaner function to stop the robot
    Robot.drivetrain.getDriveTrain().drivePolar(0, 0, 0);
  }

  public void update_devices() {  // Function to switch references of device objects

    if (OI.isSideArm) {   // If the current orientation is the arm side

      current_vision_listen = Robot.vision_listen_arm;  // Assign the current vision listen to the network table data from the arm side
      current_vision_array = Robot.vision_listen_arm.vision_array;
      current_ultrasonic = Robot.vision_sensors.ultrasonic_ARM;  // Assign the current ultrasonic to the arm side

      // Assign the current line trackers to the arm side trackers
      current_left_line_tracker = Robot.vision_sensors.leftLineTracker_ARM;
      current_center_line_tracker = Robot.vision_sensors.centerLineTracker_ARM;
      current_right_line_tracker = Robot.vision_sensors.rightLineTracker_ARM;

    } else {  // If the current orientation is the hatch side

      current_vision_listen = Robot.vision_listen_hatch;  // Assign the current vision listen to the network table data from the hatch side
      current_vision_array = Robot.vision_listen_hatch.vision_array;
      current_ultrasonic = Robot.vision_sensors.ultrasonic_HATCH;  // Assign the current ultrasonic to the hatch side

      // Assign the current line trackers to the hatch side trackers
      current_left_line_tracker = Robot.vision_sensors.leftLineTracker_HATCH;
      current_center_line_tracker = Robot.vision_sensors.centerLineTracker_HATCH;
      current_right_line_tracker = Robot.vision_sensors.rightLineTracker_HATCH;

    }

  }

  private void update_linetrackers() {  // Void function to update the values of linetrackers

    // Store averaged linetracker values
    leftLineTrackerAverage = current_left_line_tracker.getAverageValue();
    centerLineTrackerAverage = current_center_line_tracker.getAverageValue();
    rightLineTrackerAverage = current_right_line_tracker.getAverageValue();

    // Update the state of linetracker values using a ternary statement
    overLeftTracker = (leftLineTrackerAverage <= LINETRACKER_DEADBAND) ? true : false;
    overCenterTracker = (centerLineTrackerAverage <= LINETRACKER_DEADBAND) ? true : false;
    overRightTracker = (rightLineTrackerAverage <= LINETRACKER_DEADBAND) ? true : false;

  }

  private void follow_line() {

    if (distance > WALL_STOP) {  // Check if not about to run into the wall range

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

      Robot.drivetrain.getDriveTrain().drivePolar(linetracker_drive_magnitude, 0, linetracker_drive_rotation);  // Drive with line tracker data

    } else {  // If within the wall range, stop the robot!
      stop_robot();
      FOLLOW_LINE = false;  // Reset follow line boolean
    }

  }

  private void align_angle() {

    double current_angle = current_vision_array[0];  // Fetch the angle value from the NetworkTable array
    angle_offset = 90 - Math.abs(current_angle);  // Calculate angle offset: Offset from 90 degrees

    ANGLE_CHECK = (angle_offset <= ANGLE_DEADBAND) ? true : false;  // Ternary statement to validate angle range returns true if in range

    int direction;  // Initialize direction variable (Either 1 or -1)

    if (Math.abs(current_angle) != 0) {  // Check if angle is not 0 (This is our error angle)

      if (angle_offset >= ANGLE_DEADBAND) {  // If the angle is not within the deadband range

        if (current_angle < 0) {  // Check if angle is negative (Rotate Right) or if its positive (Rotate Left)

          direction = 1;  // Set direction to 1 (Rotate Right)
          System.out.println("Rotate right!");

        } else {

          direction = -1;  // Set direction to -1 (Rotate Left)
          System.out.println("Rotate left!");

        }

        drive_rotation = (KP_ANGLE * angle_offset) * direction;  // Calculate rotation with new direction values
      
      } else {
        drive_rotation = 0;  // Stop strafing
        System.out.println("Angle is aligned!");
      }

    } else {
      drive_rotation = 0;  // Stop strafing
    }

  }


  private void align_position() {
    
    double currentP1Offset = current_vision_array[1];  // Extract the x offset of midpoint 1 from the NetworkTable
    double currentP2Offset = current_vision_array[2];  // Extract the x offset of midpoint 2 from the NetworkTable
    average_offset = (currentP1Offset+currentP2Offset)/2;  // Calculate the X value of the averages of the points (midpoint of the two points)

    POSITION_CHECK = (Math.abs(average_offset) <= POSITION_DEADBAND) ? true : false;  // Ternary statement to validate position range returns true if in range

    if (current_vision_listen.calculate_delta) {  // Only run this code if there is a change in the position offset

      delta_time = position_timer.get() - pre_time;  // Get the difference in time
      delta_position = average_offset - pre_offset;  // Get the difference in position offsets

      rate = delta_position / delta_time;  // Calulcate the rate of change (slope)
      
      pre_time = position_timer.get();  // Reassign, pre-time to restart the loop
      pre_offset = average_offset;  // Reassign, pre-offset to restart the loop

      current_vision_listen.calculate_delta = false;  // Set the change boolean to false

    } else {
      rate = 0;  // If there is no change, set the rate to 0
    }

    if (Math.abs(average_offset) >= POSITION_DEADBAND) {  // If the robot is not within the position range

      if (average_offset > 0) {  // Check if average offset is positive (Strafe left!)

        drive_magnitude = (KP_POSITION * Math.abs(average_offset) + (KD_POSITION * rate));  // Calculate magnitude based on KP value
        drive_angle = -90;  // Strafe Left at -90 degrees
        System.out.println("Strafe left!");

      } else {  // If the average offset is negative (Strafe right!)

        drive_magnitude = (KP_POSITION * Math.abs(average_offset) + (KD_POSITION * rate));  // Calculate magnitude based on KP value
        drive_angle = 90;  // Strafe Right at 90 degrees
        System.out.println("Strafe right!");

      }
  
    } else {

      drive_magnitude = 0;
      drive_angle = 0;
      System.out.println("Position is aligned!");

    }

  }

  private void align_robot() {

    // Update the drive values in order to succesfully transform robot
    align_angle();
    align_position();

    boolean linetracker_triggered = (overLeftTracker || overCenterTracker || overRightTracker) ? true : false;  // If any of the line trackers are enabled return true

    if (linetracker_triggered && ANGLE_CHECK) {  // First Clause: if any line tracker is true and angle check is valid
      stop_robot();  // stop the robot and enable line following mode
      FOLLOW_LINE = true;  // Begin follow line
    } else if (ANGLE_CHECK && POSITION_CHECK && !linetracker_triggered) {  // Second clause: if both vision booleans return true by 
      Robot.drivetrain.getDriveTrain().drivePolar(.30, 0, 0);  // Drive slow super slowly to maybe trip off a sensor
    } else {
      Robot.drivetrain.getDriveTrain().drivePolar(drive_magnitude, drive_angle, drive_rotation);  // If nothing keep on aligning
    }

  }


  @Override
  protected void execute() {

    distance = current_ultrasonic.getRangeInches();  // Update the distance from the ultrasonic
    update_linetrackers();  // Update the values from the line trackers

    if (!FOLLOW_LINE) {
      align_robot();
    } else {
      follow_line();
    }
    

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
