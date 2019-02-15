/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.I2CControl;

/**
 * Add your docs here.
 */
public class I2CInterface extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private static I2C i2c=new I2C(I2C.Port.kOnboard,RobotMap.ARDUINO_ADDRESS);

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new I2CControl());
  }

  public static I2C getI2C(){
    return i2c;
  }
}
