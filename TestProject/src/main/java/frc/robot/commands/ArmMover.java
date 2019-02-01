/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
public class ArmMover extends Command {
  public static int MAX = 500;
  private int DEADZONE = 100;
  private int VEL_DEADZONE = 10;
  private double  MOTOR_DEADZONE = 0.1;
  public ArmMover() {
    requires(Robot.arm);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(OI.LIFT_PID) {
      if(Math.abs(Robot.m_oi.gunner.getY(Hand.kLeft)) > 0.1 ){
        if (Robot.arm.getTalon().getSensorCollection().isFwdLimitSwitchClosed() || Robot.arm.getTalon().getSensorCollection().isRevLimitSwitchClosed()) {
          if(Robot.arm.getTalon().getSensorCollection().isFwdLimitSwitchClosed() && Robot.m_oi.gunner.getY(Hand.kLeft) > 0) {
              Robot.arm.target = 0;
              Robot.arm.getTalon().getSensorCollection().setQuadraturePosition(0, 0);
          }
          else if(Robot.arm.getTalon().getSensorCollection().isRevLimitSwitchClosed() && Robot.m_oi.gunner.getY(Hand.kLeft) < 0) {
              
            Robot.arm.target = Robot.arm.target;
          }
          else {
            Robot.arm.target += (Robot.m_oi.gunner.getY(Hand.kLeft)*320) ;
            //Robot.lift.target += (Robot.oi.gunner.getY(Hand.kLeft)*200) * Robot.encoder_multi;
          }
          }
        else {
          Robot.arm.target += (Robot.m_oi.gunner.getY(Hand.kLeft)*320);
          //Robot.lift.target += (Robot.oi.gunner.getY(Hand.kLeft)*200) * Robot.encoder_multi;
        }
      } else {
        Robot.arm.target = Robot.arm.target;
      }
      
      if(Math.abs(Robot.arm.getTalon().getSelectedSensorPosition(0) - Robot.arm.target) > DEADZONE) {
        Robot.arm.getTalon().set(ControlMode.Position, Robot.arm.target);
      } else {
        Robot.arm.getTalon().set(ControlMode.Position, Robot.arm.getTalon().getSelectedSensorPosition(0));
      
    }
    }else {
      Robot.arm.getTalon().set(ControlMode.PercentOutput, OI.gunner.getY( Hand.kLeft));
    }
    // Check if motor is stalled
    /*if(Math.abs(Robot.lift.getTalon().getSelectedSensorVelocity(0)) < VEL_DEADZONE) {
      // If the lift isn't moving
      if(Math.abs(Robot.lift.getTalon().get()) > MOTOR_DEADZONE) {
        OI.IS_MOTOR_STALLED = true;
      }
      
    } else {
      OI.IS_MOTOR_STALLED = false;
    }*/

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
