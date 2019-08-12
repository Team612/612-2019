package frc.robot.subsystems;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class RGBInterface extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private static I2C i2c = new I2C(I2C.Port.kOnboard,RobotMap.ARDUINO_ADDRESS);
      
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    //setDefaultCommand(new RGBControl());
  }
  public static I2C getI2C(){
    return i2c;
  }
}

