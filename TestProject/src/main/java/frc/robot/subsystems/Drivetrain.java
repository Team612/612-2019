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
  private WPI_TalonSRX talon_HL = new WPI_TalonSRX(RobotMap.TALON_PORT_FL);
  private WPI_TalonSRX talon_HR = new WPI_TalonSRX(RobotMap.TALON_PORT_FR);
  private WPI_TalonSRX talon_AL = new WPI_TalonSRX(RobotMap.TALON_PORT_BL);
  private WPI_TalonSRX talon_AR = new WPI_TalonSRX(RobotMap.TALON_PORT_BR);

  private double talonArray[] = {talon_HL.get(), talon_AL.get(), talon_HR.get(), talon_AR.get()};

  public double[] getTalonArray(){
      return talonArray;
  }
  
  public MecanumDrive drivetrain;  // Initialize the drivetrain object


  public Drivetrain() {

    
    // For Test Purposes Only
    /*
    talon_FR.setInverted(true);
    talon_BR.setInverted(true);
    talon_FL.setInverted(true);
    talon_BL.setInverted(true);
    */

    drivetrain = new MecanumDrive(talon_HL, talon_AL, talon_HR, talon_AR);  // Create the MechanumDrive object
  
  }


  public WPI_TalonSRX getTalon(int num) {  // Troubleshooting method to return talon object
    switch(num) {
        case 1:
            return talon_HL;
        case 2:
            return talon_HR;
        case 3:
            return talon_AL;
        case 4:
            return talon_AR;
        default:
            return null;
    }
  }

  public void initDefaultCommand() {
      setDefaultCommand(new DefaultDrive());  // Set Drive as default command
  }

}