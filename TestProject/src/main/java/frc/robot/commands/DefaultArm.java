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
import frc.robot.subsystems.Arm;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.buttons.JoystickButton;


public class DefaultArm extends Command {

  // Booleans for limit switches on arm
  private boolean top_limit_switch_hit;
  private boolean bottom_limit_switch_hit;

  // PID position variables
  private int MAX_POSITION = 500;  // Define to count at end of range of motion degrees
  private double MAX_ANGLE = 90;  // Degree value for unit conversion

  // Pre-defined positions for PID shooting
  private double SHOOT_ANGLE = 250;  // Optimal shooting position for arm

  // PID speed variables (Increments)
  private double PID_UP_SPEED = 35;
  private double PID_DOWN_SPEED = 35;

  private double ARM_SPEED = 0.75;  // Define the NON-PID motor speed
  
  public DefaultArm() {
    requires(Robot.arm);  // Require the arm object
  }

  @Override
  protected void initialize() {
    //Robot.arm.getTalon().getSensorCollection().setQuadraturePosition(0, 0);
    Arm.target = Robot.arm.getTalon().getSelectedSensorPosition(0);  // Set the arm target to lowest position at beginning of the round
  }

  private double countsToAngle() {  // Convert the PID count to an angle value
    double angle = Arm.target * (MAX_ANGLE/MAX_POSITION);  // Unit conversion to find angle value
    return angle;
  }

  private void setArmAngle(double target_position, JoystickButton button) {  // Set the arm to a certain position based on Joystick press of gunner
    if (button.get()) {  // If the button passed is clicked
      Arm.target = target_position;  // Set the target to the given angle
    }
  }

  @Override
  protected void execute() {
    
    // Booleans for limit switch results
    top_limit_switch_hit = Robot.limit_switch.getArmTop();
    bottom_limit_switch_hit = Robot.limit_switch.getArmBottom();

    String pov_position = Robot.gunnerPOV.get_direction();  // Get the position of the D-PAD before the logic, for effiency

    if (OI.gunner_button_BCK.get()) {  // Toggle button to disable arm PID
      OI.ARM_PID = false;
    }

    if (OI.ARM_PID) {  // Only run PID code if variable in OI is true
      setArmAngle(-2300, OI.gunner_button_Y);
      setArmAngle(-964, OI.gunner_button_B);
      setArmAngle(-500, OI.gunner_button_X);

      if (top_limit_switch_hit && pov_position.equals("North")) {  // If the gunner is trying to move the arm up while at the top position, do nothing
        Robot.arm.getTalon().getSensorCollection().setQuadraturePosition(0, 0);
        Arm.target =0; // set to zero
      } else if (bottom_limit_switch_hit && pov_position.equals("South")) {  // If the gunner is trying to move the arm down while at the bottom position, do nothing
      
      } else if (pov_position.equals("North")) {  // Else do normal PID increments
        Arm.target += PID_UP_SPEED;  // Increase the PID target value 
      } else if (pov_position.equals("South")) {  // If D-Pad is down, decrease arm target
        Arm.target -= PID_DOWN_SPEED;  // Decrease the PID target value 
      }

      Robot.arm.getTalon().set(ControlMode.Position, Arm.target);  // Set the ARM to a specific position value

    } else {  // Non PID version
      
      if (pov_position.equals("North")) {  // If the D-Pad is going up 
        Robot.arm.getTalon().set(ControlMode.PercentOutput, ARM_SPEED);  // Set the arm to specified arm speed
      } else if (pov_position.equals("South")) {  // If the D-Pad is going down 
        Robot.arm.getTalon().set(ControlMode.PercentOutput, ARM_SPEED * -1);
      } else {
        Robot.arm.getTalon().set(0);  // Don't move the Arm
      }

    }

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
