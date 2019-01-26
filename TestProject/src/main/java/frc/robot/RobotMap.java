/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  public static int TALON_PORT_FL=2;
  public static int TALON_PORT_FR=1;
  public static int TALON_PORT_BL=4;
  public static int TALON_PORT_BR=3;
  public static int talon_lift =5;
  public static int driver_port  =0;
  public static int gunner_port = 1;
  public static int PCM_solenoid_D =0;
  public static int Grabber_Solenoid_1 =0;
  public static int Grabber_Solenoid_2 =1;
  public static int PCM_solenoid_G =1;
  public static int Dropper_Solenoid_1 =0;
  public static int Dropper_Solenoid_2 =1;


  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;

  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;
}
