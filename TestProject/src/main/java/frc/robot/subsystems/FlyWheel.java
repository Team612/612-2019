/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.DefaultFly;

public class FlyWheel extends Subsystem {

  private WPI_TalonSRX flyer = new WPI_TalonSRX(RobotMap.TALON_PORT_FLY);  // Create flywheel talon
  private DigitalInput push_button = new DigitalInput(RobotMap.DIO_PORT);

  public FlyWheel(){
    flyer.setInverted(true);  // Set the talon to run to reverse direction
  }

  public WPI_TalonSRX getTalon(){
    return flyer;
  }

  public DigitalInput getButton(){
    return push_button;
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new DefaultFly());  // Set the flywheel as a default command
  }

}
