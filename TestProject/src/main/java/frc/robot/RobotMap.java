/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


public class RobotMap {

  // Drive Talons
  public static int TALON_PORT_FL = 7;
  public static int TALON_PORT_FR = 2;
  public static int TALON_PORT_BL = 6;
  public static int TALON_PORT_BR = 3;

  // Climb Talons
  public static int TALON_PORT_LIFT_F = 4;
  public static int TALON_PORT_LIFT_B = 8;

  // Gunner Talons
  public static int TALON_PORT_ARM = 5;
  public static int TALON_PORT_HATCH = 9;
  public static int TALON_PORT_FLY = 1;

  // Line tracker ports
  public static int leftLineTracker_port = 0;
  public static int centerLineTracker_port = 1;
  public static int rightLineTracker_port = 2;

  // Controller ports
  public static int driver_port  =0;
  public static int gunner_port = 1;

  // Ultrasonic ports
  public static int pingChannel =0;
  public static int echoChannel =1;

  // Servo ports
  public static int servo_angle =0;
  public static int servo_front =1;
  public static int servo_back =2;
  
}
