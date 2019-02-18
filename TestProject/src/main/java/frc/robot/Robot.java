/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.commands.DefaultDrive;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.*;
import frc.robot.POVConvert;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  // Drivetrain object
  public static Drivetrain drivetrain = new Drivetrain();

  // Driver servo camera objects
  public static POVConvert driverPOV = new POVConvert(OI.driver);
  public static POVConvert gunnerPOV = new POVConvert(OI.gunner);
  public static DriverCamera drivercamera = new DriverCamera(); 

  // Climb object
  public static Climb climb = new Climb();
  
  // Gunner object
  public static Hatch hatch = new Hatch();
  public static FlyWheel flyWheel = new FlyWheel();
  public static Arm arm = new Arm();

  // Auto Align object
  public static LineTracker linetracker = new LineTracker();

  // Limit switch helper object
  public static LimitSwitchHelper limit_switch = new LimitSwitchHelper();

  // OI object
  public static OI m_oi;

  // MISC
  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  @Override
  public void robotInit() {
    m_oi = new OI();  // Create an object of OI
  }


  @Override
  public void robotPeriodic() {
  }


  @Override
  public void disabledInit() {
  }


  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {

    OI.isAutonomous = true;  // Set is autonomous boolean to true

    m_autonomousCommand = m_chooser.getSelected();
    if (m_autonomousCommand != null) {  // Start autonomous (No autonomous this year)
      m_autonomousCommand.start();
    }

  }


  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }


  @Override
  public void teleopInit() {

    OI.isAutonomous = false;  // At beginning of teleop, set autonomous to false

    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }

  }

  
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();

    /* -- SHUFFLE BOARD DATA -- */

    // Hatch Limit Switches
    SmartDashboard.putBoolean("Hatch FAR",limit_switch.getHatchFar());
    SmartDashboard.putBoolean("Hatch CLOSE", limit_switch.getHatchFar());

    // Arm Limit Switches
    SmartDashboard.putBoolean("Arm TOP", limit_switch.getArmTop());
    SmartDashboard.putBoolean("Arm BOTTOM", limit_switch.getArmBottom());

    // Climb Limit Switches
    SmartDashboard.putBoolean("Climb ARM TOP", limit_switch.getClimbTopArm());
    SmartDashboard.putBoolean("Climb ARM BOTTOM", limit_switch.getClimbBottomArm());
    SmartDashboard.putBoolean("Climb HATCH TOP", limit_switch.getClimbTopHatch());
    SmartDashboard.putBoolean("Climb HATCH BOTTOM", limit_switch.getClimbBottomHatch());

    // NavX Data Values
    SmartDashboard.putNumber("NavX ANGLE ", Robot.climb.getNavX().getAngle());
    SmartDashboard.putNumber("NavX ROLL ", Robot.climb.getNavX().getRoll());
    SmartDashboard.putNumber("NavX PITCH ", Robot.climb.getNavX().getPitch());

    // Values For Arm Data
    SmartDashboard.putNumber("Arm PID Target", Robot.arm.target);
    SmartDashboard.putNumber("Arm Encoder Position", Robot.arm.getTalon().getSelectedSensorPosition(0));
    
    // Values For Climb Data
    SmartDashboard.putNumber("Climb Arm PID Target", Climb.target_arm);
    SmartDashboard.putNumber("Climb HATCH PID Target", Climb.target_hatch);
    SmartDashboard.putNumber("Climb HATCH Encoder Position", Robot.climb.getTalon(0).getSelectedSensorPosition(0));
    SmartDashboard.putNumber("Climb ARM Encoder Position", Robot.climb.getTalon(1).getSelectedSensorPosition(0));

    // Drivetrain Values
    SmartDashboard.putNumber("Drive Magnitude", DefaultDrive.magnitude);
    SmartDashboard.putNumber("Drive Angle", DefaultDrive.angle);
    SmartDashboard.putNumber("Drive Rotation", DefaultDrive.rotation);
    
    // Drive Joystick Values
    SmartDashboard.putNumber("Joystick X", DefaultDrive.direction_x);
    SmartDashboard.putNumber("Joystick Y", DefaultDrive.direction_y);

    //Line tracker values
    /*  
    SmartDashboard.putNumber("Line Tracker Arm Left", linetracker.leftLineTracker_ARM.getAverageVoltage());
    SmartDashboard.putNumber("Line Tracker Arm Middle", linetracker.centerLineTracker_ARM.getAverageVoltage());
    SmartDashboard.putNumber("Line Tracker Arm Right", linetracker.rightLineTracker_ARM.getAverageVoltage());
    SmartDashboard.putNumber("Line Tracker Hatch Left", linetracker.leftLineTracker_HATCH.getAverageVoltage());
    SmartDashboard.putNumber("Line Tracker Hatch Middle", linetracker.centerLineTracker_HATCH.getAverageVoltage());
    SmartDashboard.putNumber("Line Tracker Hatch Right", linetracker.rightLineTracker_HATCH.getAverageVoltage());
    */
  
  }



  @Override
  public void testPeriodic() {
  }

}
