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
import frc.robot.subsystems.FlyWheel;


public class DefaultFly extends Command {

  private boolean bottom_limit_switch_hit;  // Initialize the bottom limit switch boolean for arm
  
  public static double INTAKE_SPEED = .6;  // Variable to store flywheel intake motor speed
  private double OUTTAKE_SPEED = -1;  // Variable to store flywheel outtake motor speed

  public DefaultFly() {
    requires(Robot.flyWheel);  // Requires FlyWheel Object
  }

  @Override
  protected void initialize() {
    
  }

  @Override
  protected void execute() {

    bottom_limit_switch_hit = Robot.limit_switch.getArmBottom();  // Boolean to store bottom arm limit switch

    if (OI.gunner_button_LB.get()) {  // If the button is pressed and the user presses LB, push the ball out
      INTAKE_SPEED = .4;
      Robot.flyWheel.getTalon().set(OUTTAKE_SPEED);  // Run the motors to push out the ball
      FlyWheel.BALL_IN_INTAKE = false;  // Sensor state of intake (Ball is not in)

    } else if (bottom_limit_switch_hit && OI.gunner_button_RB.get()) {  // Only allow intake when at bottom limit switch is hit

      if (Robot.flyWheel.getButton().get() && !FlyWheel.BALL_IN_INTAKE) {  // If the ball is not button is hit, only allow outtake
        Robot.flyWheel.getTalon().set(INTAKE_SPEED);
        INTAKE_SPEED = .4;
      } else {  // Else, dont allow intake
        INTAKE_SPEED = (INTAKE_SPEED * .95);
        Robot.flyWheel.getTalon().set(INTAKE_SPEED);
        FlyWheel.BALL_IN_INTAKE = true;

        if (INTAKE_SPEED < .1) {
          INTAKE_SPEED = 0;
        }

      }

    } else {  // If nothing is moved, set the speed to 0

      FlyWheel.BALL_IN_INTAKE = false;
      Robot.flyWheel.getTalon().set(0);

    }

    //System.out.println(INTAKE_SPEED);

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