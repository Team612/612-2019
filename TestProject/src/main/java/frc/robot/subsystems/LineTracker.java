/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class LineTracker extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public AnalogInput leftLineTracker_ARM = new AnalogInput(RobotMap.LEFT_LINE_TRACKER_PORT_ARM);
  public AnalogInput centerLineTracker_ARM = new AnalogInput(RobotMap.CENTER_LINE_TRACKER_PORT_ARM);
  public AnalogInput rightLineTracker_ARM = new AnalogInput(RobotMap.RIGHT_LINE_TRACKER_PORT_ARM);
  public AnalogInput leftLineTracker_HATCH = new AnalogInput(RobotMap.LEFT_LINE_TRACKER_PORT_HATCH);
  public AnalogInput centerLineTracker_HATCH = new AnalogInput(RobotMap.CENTER_LINE_TRACKER_PORT_HATCH);
  public AnalogInput rightLineTracker_HATCH = new AnalogInput(RobotMap.RIGHT_LINE_TRACKER_PORT_HATCH);
  public Ultrasonic ultrasonic_ARM = new Ultrasonic(RobotMap.PING_CHANNEL_ARM, RobotMap.ECHO_CHANNEL_ARM);
  public Ultrasonic ultrasonic_HATCH = new Ultrasonic(RobotMap.PING_CHANNEL_HATCH, RobotMap.ECHO_CHANNEL_HATCH);


  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
