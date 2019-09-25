/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


public class RobotMap {

  // Drive Talons
  public static int TALON_PORT_FL                         = 6;
  public static int TALON_PORT_FR                         = 3;
  public static int TALON_PORT_BL                         = 7;
  public static int TALON_PORT_BR                         = 2;

  // Climb Talons
  public static final int TALON_PORT_CLIMB_HATCH          = 4;
  public static final int TALON_PORT_CLIMB_ARM            = 8;

  // Gunner Talons
  public static final int TALON_PORT_ARM                  = 5;
  public static final int TALON_PORT_HATCH                = 9;
  public static final int TALON_PORT_FLY                  = 1;

  // Servo ports
  public static final int SERVO_PORT_DRIVER               = 9;
  public static int SERVO_PORT_HATCH                      = 6;

  // Line tracker ports
  public static final int LEFT_LINE_TRACKER_PORT_ARM      = 1;
  public static final int CENTER_LINE_TRACKER_PORT_ARM    = 0;
  public static final int RIGHT_LINE_TRACKER_PORT_ARM     = 3;

  // NOTE: FOLLOWING PORT NUMBERS ARE SUBJECT TO CHANGE
  public static final int LEFT_LINE_TRACKER_PORT_HATCH    = 5;
  public static final int CENTER_LINE_TRACKER_PORT_HATCH  = 6;
  public static final int RIGHT_LINE_TRACKER_PORT_HATCH   = 7;

  // Controller ports
  public static final int DRIVER_PORT                     = 0;
  public static final int GUNNER_PORT                     = 1;

  // Ultrasonic ports
  public static final int PING_CHANNEL_ARM                = 7; // The ping port of the ultrasonic sensor on the arm side of the robot
  public static final int ECHO_CHANNEL_ARM                = 6; // The echo port of the ultrasonic sensor on the arm side of the robot
  public static final int PING_CHANNEL_HATCH              = 1; // The ping port of the ultrasonic sensor on the hatch side of the robot
  public static final int ECHO_CHANNEL_HATCH              = 0; // The echo port of the ultrasonic sensor on the hatch side of the robot
  
  // Push Button 
  public static final int DIO_PORT                        = 5;

  // LED Variables 
  public static final int ARDUINO_ADDRESS                 = 1;
  
}