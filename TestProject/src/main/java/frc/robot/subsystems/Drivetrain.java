
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import frc.robot.RobotMap;
import frc.robot.commands.DefaultDrive;

public class Drivetrain extends Subsystem {

  // Define talon objects for drivetrain
  WPI_TalonSRX talon_FL = new WPI_TalonSRX(RobotMap.TALON_PORT_FL);
  WPI_TalonSRX talon_FR = new WPI_TalonSRX(RobotMap.TALON_PORT_FR);
  WPI_TalonSRX talon_BL = new WPI_TalonSRX(RobotMap.TALON_PORT_BL);
  WPI_TalonSRX talon_BR = new WPI_TalonSRX(RobotMap.TALON_PORT_BR);

  MecanumDrive drivetrain;  // Initialize drivetrain object

  public Drivetrain() {
    drivetrain = new MecanumDrive(talon_FL, talon_BL, talon_FR, talon_BR);  // Construct new drivetrain object
  }

  public WPI_TalonSRX getTalon(int num){
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

  public MecanumDrive getDriveTrain() {
    talon_FL.setNeutralMode(NeutralMode.Brake);  // Set talon arm to break mode
    talon_FR.setNeutralMode(NeutralMode.Brake);  // Set talon arm to break mode
    talon_BL.setNeutralMode(NeutralMode.Brake);  // Set talon arm to break mode
    talon_BR.setNeutralMode(NeutralMode.Brake);  // Set talon arm to break mode


      return drivetrain;
  }

  public void initDefaultCommand() {
      setDefaultCommand(new DefaultDrive());
  }

}
