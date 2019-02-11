/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;




import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;


public class Climb extends Subsystem {

  // Define the lift talons (front & back)
  public WPI_TalonSRX lift_talonFront = new WPI_TalonSRX(RobotMap.TALON_PORT_LIFT_FRONT);
  public WPI_TalonSRX lift_talonBack = new WPI_TalonSRX(RobotMap.TALON_PORT_LIFT_BACK);

  // Define the servo objects
  public Servo servo_front = new Servo(RobotMap.SERVO_PORT_CLIMB_FRONT);
  public Servo servo_back = new Servo(RobotMap.SERVO_PORT_CLIMB_BACK);

  // Variables to PID values
  private double kF = 0.2;
  private double kP = 0.2;
  private double kI = 0;
  private double kD = 0;

  private void configure_arm(WPI_TalonSRX talon) {

    talon.setNeutralMode(NeutralMode.Brake);  // Set talon arm to break mode
    talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 300);
    talon.selectProfileSlot(0, 0);  // Find out what THIS DOES
    talon.config_kF(0, kF, 100);  // The 100 is the time out 
    talon.config_kP(0, kP, 100);  // for setting  the configuration.(in milliseconds).
    talon.config_kI(0, kI, 100);
    talon.config_kD(0, kD, 100);
    
  }

  @Override
  public void initDefaultCommand() {

    configure_arm(lift_talonFront);
    configure_arm(lift_talonBack);

  }

}
