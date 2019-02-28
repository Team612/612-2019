/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.subsystems.FlyWheel;


public class DefaultFly extends Command {


  private final double DEADZONE = 0;  // Define the controller DEADZONE

  private boolean bottom_limit_switch_hit;  // Initialize the bottom limit switch boolean for arm
  
  private double flywheel_speed = 1;  // Variable to store flywheel motor speed

  public DefaultFly() {
    requires(Robot.flyWheel);  // Requires FlyWheel Object
  }

  @Override
  protected void initialize() {
    
  }

  @Override
  protected void execute() {
  // System.out.println(Robot.flyWheel.getButton().get());

    bottom_limit_switch_hit = Robot.limit_switch.getArmBottom();  // Boolean to store bottom arm limit switch

    if (OI.gunner_button_LB.get() ){  // If the button is pressed and the user presses RB, push the ball out
      Robot.flyWheel.getTalon().set(flywheel_speed);  // Run the motors to push out the ball
      FlyWheel.BALL_IN_INTAKE = false;
    } else if(bottom_limit_switch_hit && OI.gunner_button_RB.get() ){
      if(FlyWheel.push_button.get() && !FlyWheel.BALL_IN_INTAKE){
        //BALL_IN_FLY = false;
        Robot.flyWheel.getTalon().set(flywheel_speed * -1);
      } else{
        Robot.flyWheel.getTalon().set(0);
        FlyWheel.BALL_IN_INTAKE = true;
      }
    } else{
      FlyWheel.BALL_IN_INTAKE = false;
      Robot.flyWheel.getTalon().set(0);
    }

    /*if (!Robot.flyWheel.getButton().get()) {  // If the the ball is not touching the button allow for intake
      if (bottom_limit_switch_hit && OI.gunner_button_LB.get() && !BALL_IN_FLY) {  // If the arm is at the bottom position and the intake button is pressed enable intake
        Robot.flyWheel.getTalon().set(flywheel_speed);  // Run the motors to intake the ball
      }
    } else if (OI.gunner_button_RB.get() ){  // If the button is pressed and the user presses RB, push the ball out
      Robot.flyWheel.getTalon().set(flywheel_speed * -1);  // Run the motors to push out the ball
      BALL_IN_FLY = true;
    } else {
      BALL_IN_FLY = false;
      if (OI.gunner_button_LB.get()) {  // If the ball is in the intake and gunner attempts to pull ball in, rumble
        OI.gunner.setRumble(RumbleType.kLeftRumble, 1);
        OI.gunner.setRumble(RumbleType.kRightRumble, 1); 
      } else {
        OI.gunner.setRumble(RumbleType.kLeftRumble, 0);
        OI.gunner.setRumble(RumbleType.kRightRumble, 0); 
      }
      Robot.flyWheel.getTalon().set(0);  // Don't allow for motors to run
    }*/

  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
  }

  @Override
  protected void interrupted() {
  }

}