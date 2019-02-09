/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import frc.robot.RobotMap;
import frc.robot.commands.DefaultDrive;

public class Drivetrain extends Subsystem {
  

  // Declare 4 talons for each drive talon
  WPI_TalonSRX talon_FL = new WPI_TalonSRX(RobotMap.TALON_PORT_FL);
  WPI_TalonSRX talon_FR = new WPI_TalonSRX(RobotMap.TALON_PORT_FR);
  WPI_TalonSRX talon_BL = new WPI_TalonSRX(RobotMap.TALON_PORT_BL);
  WPI_TalonSRX talon_BR = new WPI_TalonSRX(RobotMap.TALON_PORT_BR);
  
  public MecanumDrive drivetrain;  // Initialize the drivetrain object


  public Drivetrain() {

    
    // For Test Purposes Only
    /*
    talon_FR.setInverted(true);
    talon_BR.setInverted(true);
    talon_FL.setInverted(true);
    talon_BL.setInverted(true);
    */

    drivetrain = new MecanumDrive(talon_FL, talon_BL, talon_FR, talon_BR);  // Create the MechanumDrive object
  
  }


  public WPI_TalonSRX getTalon(int num) {  // Troubleshooting method to return talon object
    switch(num) {
        case 1:
            return talon_FL;
        case 2:
            return talon_FR;
        case 3:
            return talon_BL;
        case 4:
            return talon_BR;
        default:
            return null;
    }
  }

  public void initDefaultCommand() {
      setDefaultCommand(new DefaultDrive());  // Set Drive as default command
  }

}