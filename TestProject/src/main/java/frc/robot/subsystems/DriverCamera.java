/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;


public class DriverCamera extends Subsystem {

  private Servo camera_servo = new Servo(RobotMap.SERVO_PORT);  // Define a new servo

  public Servo getServo(){
    return camera_servo;
  }

  @Override
  public void initDefaultCommand() {
  }
}
