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

  public AnalogInput leftLineTracker = new AnalogInput(RobotMap.leftLineTracker_port);
  public AnalogInput centerLineTracker = new AnalogInput(RobotMap.centerLineTracker_port);
  public AnalogInput rightLineTracker = new AnalogInput(RobotMap.rightLineTracker_port);
  public Ultrasonic ultrasonic = new Ultrasonic(RobotMap.pingChannel, RobotMap.echoChannel);

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
