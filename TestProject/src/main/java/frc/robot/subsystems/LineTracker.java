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

  public AnalogInput leftLineTracker = new AnalogInput(RobotMap.LEFT_LINE_TRACKER_PORT);
  public AnalogInput centerLineTracker = new AnalogInput(RobotMap.CENTER_LINE_TRACKER_PORT);
  public AnalogInput rightLineTracker = new AnalogInput(RobotMap.RIGHT_LINE_TRACKER_PORT);
  public Ultrasonic ultrasonic = new Ultrasonic(RobotMap.PING_CHANNEL, RobotMap.ECHO_CHANNEL);

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
