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
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;


public class Climb extends Subsystem {

  // Define the lift talons (front & back)
  private WPI_TalonSRX lift_talonHatch = new WPI_TalonSRX(RobotMap.TALON_PORT_LIFT_FRONT);//Front
  private WPI_TalonSRX lift_talonArm = new WPI_TalonSRX(RobotMap.TALON_PORT_LIFT_BACK);//Back

  // Define an object that stores the NavX on the roboRio as an object
  private static AHRS navX = new AHRS(I2C.Port.kOnboard);

  // Define the servo objects
  private Servo servo_hatch = new Servo(RobotMap.SERVO_PORT_CLIMB_FRONT);
  private Servo servo_arm = new Servo(RobotMap.SERVO_PORT_CLIMB_BACK);

  // Variables to PID values
  private double kF = 0.2;
  private double kP = 0.2;
  private double kI = 0;
  private double kD = 0;

  // Getter for the Talons
  public WPI_TalonSRX getTalon(int talon){
    switch(talon){
      case 0:return lift_talonHatch;
      case 1:return lift_talonArm;
      default:return null;
    }
  }

  // Getter for the Servo objects
  public Servo getServo(int servo){
    switch(servo){
      case 0:return servo_hatch;
      case 1:return servo_arm;
      default:return null;
    }
  }

  // Getter for the NavX object
  public AHRS getNavX(){
    return navX;  
  }

  // Configures the Talon settings for the climbn
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
    configure_arm(lift_talonHatch);
    configure_arm(lift_talonArm);
  }

}
