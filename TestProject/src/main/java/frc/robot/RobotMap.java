/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


public class RobotMap {

  // Drive Talons
  public static final int TALON_PORT_FL             = 7;
  public static final int TALON_PORT_FR             = 2;
  public static final int TALON_PORT_BL             = 6;
  public static final int TALON_PORT_BR             = 3;

  // Climb Talons
  public static final int TALON_PORT_LIFT_FRONT     = 4;
  public static final int TALON_PORT_LIFT_BACK      = 8;

  // Gunner Talons
  public static final int TALON_PORT_ARM            = 5;
  public static final int TALON_PORT_HATCH          = 9;
  public static final int TALON_PORT_FLY            = 1;

  // Servo ports
  public static final int SERVO_PORT                = 0;
  public static final int SERVO_PORT_CLIMB_FRONT    = 1;
  public static final int SERVO_PORT_CLIMB_BACK     = 2;

  // Line tracker ports
  public static final int LEFT_LINE_TRACKER_PORT    = 0;
  public static final int CENTER_LINE_TRACKER_PORT  = 1;
  public static final int RIGHT_LINE_TRACKER_PORT   = 2;

  // Controller ports
  public static final int DRIVER_PORT               = 0;
  public static final int GUNNER_PORT               = 1;

  // Ultrasonic ports
  public static final int PING_CHANNEL_ARM          = 9; // The ping port of the ultrasonic sensor on the arm side of the robot
  public static final int ECHO_CHANNEL_ARM          = 8; // The echo port of the ultrasonic sensor on the arm side of the robot
  public static final int PING_CHANNEL_HATCH        = 7; // The ping port of the ultrasonic sensor on the hatch side of the robot
  public static final int ECHO_CHANNEL_HATCH        = 6; // The echo port of the ultrasonic sensor on the hatch side of the robot
  // Push Buttion 
  public static final int DIO_PORT                  = 5;
  
}
