/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.DefaultHatch;

/**
 * Add your docs here.
 */
public class Hatch extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private Servo hatchServo = new Servo(RobotMap.SERVO_PORT_HATCH);

  private WPI_TalonSRX hatchTalon = new WPI_TalonSRX(RobotMap.TALON_PORT_HATCH);  // Creates the Hatch Talon Object


  public Servo getServo() {
    return hatchServo;
  }

  public WPI_TalonSRX getTalon() {
    return hatchTalon;
  }

  @Override
  public void initDefaultCommand() {
    hatchTalon.setNeutralMode(NeutralMode.Coast);  // Set talon arm to break mode
    // Set the default command for a subsystem here.
    setDefaultCommand(new DefaultHatch());
  }
}
