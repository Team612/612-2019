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

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.DefaultArm;


public class Arm extends Subsystem {

  private WPI_TalonSRX talon_arm = new WPI_TalonSRX(RobotMap.TALON_PORT_ARM);  // Create the ARM talon object
  public static double target = 0;  // Define the target position of the arm
  
  // Variables to PID values
  private double kF = 0.2;
  private double kP = 0.2;
  private double kI = 0;
  private double kD = 0;

  public WPI_TalonSRX getTalon(){
    return talon_arm;
  }

  private void configure_arm() {
    talon_arm.setInverted(true);
    talon_arm.setNeutralMode(NeutralMode.Brake);  // Set talon arm to break mode
    talon_arm.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 300);
    talon_arm.selectProfileSlot(0, 0);  // Find out what THIS DOES
    talon_arm.config_kF(0, kF, 100);  // The 100 is the time out 
    talon_arm.config_kP(0, kP, 100);  // for setting  the configuration.(in milliseconds).
    talon_arm.config_kI(0, kI, 100);
    talon_arm.config_kD(0, kD, 100);

  }

  @Override
  public void initDefaultCommand() {

    configure_arm();  // Call the configure arm method
    setDefaultCommand(new DefaultArm());  // Set the ArmMover as a default command

  }

}
