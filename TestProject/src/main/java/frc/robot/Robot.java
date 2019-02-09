/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.GenericHID.Hand;
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

  // Driver servo camera objects
  public static POVConvert dPov = new POVConvert(OI.driver, true);
  public static POVConvert gPov = new POVConvert(OI.gunner, true);
  public static DriverCamera drivercamera = new DriverCamera(); 

  // Climb object
  public static Climb climb = new Climb();
  
  // Gunner object
  public static Hatch hatch = new Hatch();
  public static FlyWheel flyWheel = new FlyWheel();
  public static Arm arm = new Arm();

  // Drivetrain object
  public static Drivetrain drivetrain = new Drivetrain();

  // Auto Align object
  public static LineTracker linetracker = new LineTracker();

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
    m_autonomousCommand = m_chooser.getSelected();

    // schedule the autonomous command (example)
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
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }


  @Override
  public void teleopPeriodic() {

    // ShuffleBoard Data
    SmartDashboard.putNumber("Encoder Value", Robot.arm.talon_arm.getSelectedSensorPosition(0));
    SmartDashboard.putNumber("Target", Arm.target);
    SmartDashboard.putNumber("Deadzone", OI.driver.getY(Hand.kLeft));
    
    Scheduler.getInstance().run();

  }


  @Override
  public void testPeriodic() {
  }

}
