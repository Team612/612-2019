/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class VisionSensors extends Subsystem {
  
  // Arm side Line Trackers
  public AnalogInput leftLineTracker_ARM = new AnalogInput(RobotMap.LEFT_LINE_TRACKER_PORT_ARM);
  public AnalogInput centerLineTracker_ARM = new AnalogInput(RobotMap.CENTER_LINE_TRACKER_PORT_ARM);
  public AnalogInput rightLineTracker_ARM = new AnalogInput(RobotMap.RIGHT_LINE_TRACKER_PORT_ARM);
  
  // Hatch side Line Trackers
  public AnalogInput leftLineTracker_HATCH = new AnalogInput(RobotMap.LEFT_LINE_TRACKER_PORT_HATCH);
  public AnalogInput centerLineTracker_HATCH = new AnalogInput(RobotMap.CENTER_LINE_TRACKER_PORT_HATCH);
  public AnalogInput rightLineTracker_HATCH = new AnalogInput(RobotMap.RIGHT_LINE_TRACKER_PORT_HATCH);
  // Ultrasonic Sensors
  public Ultrasonic ultrasonic_HATCH = new Ultrasonic(RobotMap.PING_CHANNEL_HATCH, RobotMap.ECHO_CHANNEL_HATCH);
  public Ultrasonic ultrasonic_ARM = new Ultrasonic(RobotMap.PING_CHANNEL_ARM, RobotMap.ECHO_CHANNEL_ARM);
  

  // NAVX 
  AHRS navx = new AHRS(I2C.Port.kMXP);

  public void setActiveUltrasonic(int num){//1 for hatch
   if(num == 1){
    ultrasonic_ARM.setAutomaticMode(false);
    ultrasonic_HATCH.setAutomaticMode(true);
   } else{
    ultrasonic_HATCH.setAutomaticMode(false);
    ultrasonic_ARM.setAutomaticMode(true);
   }
  }
  @Override
  public void initDefaultCommand() {
    //ultrasonic_ARM.setAutomaticMode(true);
   //ultrasonic_HATCH.setAutomaticMode(true);
  }
}