/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.TalonHelper;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.*;
import frc.robot.commands.AutoClimb;
import frc.robot.POVConvert;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  //talon helper 
  public static TalonHelper talonHelper = new TalonHelper();

  // Driver servo camera objects
  public static POVConvert driverPOV = new POVConvert(OI.driver);
  public static POVConvert gunnerPOV = new POVConvert(OI.gunner);
  //public static DriverCamera drivercamera = new DriverCamera(); 

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

  //Soft Robotics
  //public static SoftRobotics softrobotics = new SoftRobotics();

  //I2C Interface
  //public static I2CInterface i2cInterface=new I2CInterface();
  

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
    Robot.linetracker.ultrasonic_ARM.setAutomaticMode(true);  // Set the mode of the UltraSonic sensor
    Robot.linetracker.ultrasonic_HATCH.setAutomaticMode(true);  // Set the mode of the UltraSonic sensor
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();

    // ShuffleBoard Data
    /*
    SmartDashboard.putNumber("Arm Encoder Value", Robot.arm.getTalon().getSelectedSensorPosition(0));
    SmartDashboard.putNumber("Climb Encoder A", Robot.climb.getTalon(0).getSelectedSensorPosition(0));
    SmartDashboard.putNumber("ultra -- ARM", linetracker.ultrasonic_ARM.getRangeInches());
    SmartDashboard.putNumber("ultra -- HATCH", linetracker.ultrasonic_HATCH.getRangeInches());
    
    */
    //HATCH LIMIT SWITCHES 
    SmartDashboard.putBoolean("Hatch FAR",talonHelper.getHatchFar());
    SmartDashboard.putBoolean("Hatch CLOSE", talonHelper.getHatchFar());
    //ARM LIMIT SWITCHES 
    SmartDashboard.putBoolean("Arm TOP", talonHelper.getArmTop());
    SmartDashboard.putBoolean("Arm BOTTOM", talonHelper.getArmBottom());
    //CLIMB LIMIT SWITCHES 
    SmartDashboard.putBoolean("ClimbA Top", talonHelper.getClimbTopArm());
    SmartDashboard.putBoolean("ClimbA Bottom", talonHelper.getClimbBottomArm());
    SmartDashboard.putBoolean("ClimbH Top", talonHelper.getClimbTopHatch());
    SmartDashboard.putBoolean("ClimbH Bottonm", talonHelper.getClimbBottomHatch());
    SmartDashboard.putBoolean("Tilt Status", AutoClimb.getIs_Tilted())
    //NAVX values 
   /* SmartDashboard.putNumber("NAVX VALUE ", Robot.climb.getNavX().getAngle());
    SmartDashboard.putNumber("NAVX VALUE ", Robot.climb.getNavX().getRoll());
    SmartDashboard.putNumber("NAVX VALUE ", Robot.climb.getNavX().getPitch());
*/


    //encoder values and Targets 
    SmartDashboard.putNumber("Arm Target", Arm.target);
    SmartDashboard.putNumber("Arm Encoder", Robot.arm.getTalon().getSelectedSensorPosition(0));
    SmartDashboard.putNumber("Climb Hatch Encoder", climb.getTalon(0).getSelectedSensorPosition(0));
    SmartDashboard.putNumber("Climb Hatch Target", Climb.target_C_H);
    SmartDashboard.putNumber("Climb Arm  Encoder", climb.getTalon(1).getSelectedSensorPosition(0));
    SmartDashboard.putNumber("Climb Arm Target", Climb.target_C_A);
    //Line tracker values    
   /* SmartDashboard.putNumber("Line Tracker Arm Left", linetracker.leftLineTracker_ARM.getAverageVoltage());
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
