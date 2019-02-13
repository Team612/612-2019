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
import frc.robot.commands.DefaultHatch;;


public class Hatch extends Subsystem {

  public WPI_TalonSRX hatchTalon = new WPI_TalonSRX(RobotMap.TALON_PORT_HATCH);  // Creates the Hatch Talon Object

  public WPI_TalonSRX getTalon(){
    return hatchTalon;
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new DefaultHatch());
  }
  
}
