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

/**
 * Add your docs here.
 */
public class Lift extends Subsystem {

WPI_TalonSRX lift_talonFront=new WPI_TalonSRX(RobotMap.lift_talonFront);
WPI_TalonSRX lift_talonBack=new WPI_TalonSRX(RobotMap.lift_talonBack);
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
  public WPI_TalonSRX getlift_talonFront(){
    return lift_talonFront;
  }
  public WPI_TalonSRX getlift_talonBack(){
    return lift_talonBack;
  }
}
