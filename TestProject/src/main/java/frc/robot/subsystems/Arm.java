/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.ArmMover;

/**
 * Add your docs here.
 */
public class Arm extends Subsystem {
  WPI_TalonSRX talon_arm=new WPI_TalonSRX(RobotMap.talon_ball);
  public static int target =0;

  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  @Override
  public void initDefaultCommand() {


    talon_arm.selectProfileSlot(0, 0);
    talon_arm.config_kF(0, 0.2, 100); //The 100 is the time out 
    talon_arm.config_kP(0, 1.3, 100);//for setting  the configuration.(in milliseconds).
    talon_arm.config_kI(0, 0, 100);
    talon_arm.config_kD(0, 0,100);
    setDefaultCommand(new ArmMover());
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
  public WPI_TalonSRX getTalon(){
    return talon_arm;
  }
}
