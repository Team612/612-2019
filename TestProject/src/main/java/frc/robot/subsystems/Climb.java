/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;




import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.DefaultClimb;
import frc.robot.RobotMap;


public class Climb extends Subsystem {

  // Define the lift talons (front & back)
  public WPI_TalonSRX lift_talonFront = new WPI_TalonSRX(RobotMap.TALON_PORT_LIFT_F);
  public WPI_TalonSRX lift_talonBack = new WPI_TalonSRX(RobotMap.TALON_PORT_LIFT_B);

  // Define the servo objects
  public Servo servo_front = new Servo(RobotMap.servo_front);
  public Servo servo_back = new Servo(RobotMap.servo_back);

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new DefaultClimb());
  }

}
