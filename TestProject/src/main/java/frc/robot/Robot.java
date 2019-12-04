/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.*;
import frc.robot.POVConvert;
//import frc.robot.commands.AutoAlign;
import frc.robot.commands.*;
import frc.robot.profiles.JSONMap;
import frc.robot.profiles.Loader;
import frc.robot.profiles.GameController;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  // Hatch Subsystem
  public static Hatch hatch;

  // Initialize endoscope camera server
  public static CameraServer endosocope;

  // Drivetrain object
  public static Drivetrain drivetrain = new Drivetrain();

  // Driver servo camera objects
  public static POVConvert driverPOV;
  public static POVConvert gunnerPOV;
  public static DriverCamera drivercamera = new DriverCamera(); 

  // Climb object
  public static Climb climb = new Climb();
  
  // Gunner object
  //public static Hatch hatch = new Hatch();
  public static FlyWheel flyWheel;
  public static Arm arm;

  // Auto Align object
  // public static VisionSensors vision_sensors = new VisionSensors();

  // Limit switch helper object
  public static LimitSwitchHelper limit_switch = new LimitSwitchHelper();

  // OI object
  private int choice = 99; 
  public static OI m_oi;
  
  public static RGBInterface rgbinterface = new RGBInterface();
  RealRGB RGB = new RealRGB(); 
  // Vision Listeners
  //public static VisionListen vision_listen_arm = new VisionListen("VisionTable_ARM");
  //public static VisionListen vision_listen_hatch = new VisionListen("VisionTable_HATCH");

  // MISC
  Command m_autonomousCommand;
  public static SendableChooser<Integer> m_chooser = new SendableChooser<>();
  private SendableChooser<String> driverProfiles = new SendableChooser<>();
  private SendableChooser<String> gunnerProfiles = new SendableChooser<>();

  @Override
  public void robotInit() {

    endosocope.getInstance().startAutomaticCapture();

    m_chooser.setDefaultOption("Fade", 1);
    m_chooser.addOption("Red", 2);
    m_chooser.addOption("Blue", 3);
    m_chooser.addOption("Purple", 4);
    m_chooser.addOption("random", 5);
    // chooser.addOption("My Auto", new MyAutoCommand());
    SmartDashboard.putData("Led ROB", m_chooser);
    m_oi = new OI();  // Create an object of OI

    Loader.loadJSON("profiles.json", new GameController(RobotMap.DRIVER_PORT), new GameController(RobotMap.GUNNER_PORT));

    // INIT ALL CLASSES THAT RELY ON CONTROLLERS AFTER THIS LINE
    hatch = new Hatch();
    flyWheel = new FlyWheel();
    arm = new Arm();

    driverPOV = new POVConvert(Loader.getDriverController());
    gunnerPOV = new POVConvert(Loader.getGunnerController());

    fillChooser(driverProfiles, JSONMap.getDriverProfileNames());
    fillChooser(gunnerProfiles, JSONMap.getGunnerProfileNames());
    SmartDashboard.putData("Driver Profile", driverProfiles);
    SmartDashboard.putData("Gunner Profile", gunnerProfiles);

    // Activate Vision Listeners
    /*
    vision_listen_hatch.read_vision();
    vision_listen_arm.read_vision();
    */

    System.out.println("********************DRIVER CONTROLLER: <"+Loader.getDriverController()+"> **********");
    System.out.println("********************GUNNER CONTROLLER: <"+Loader.getGunnerController()+"> **********");

    
  }

  private void fillChooser(SendableChooser<String> choose, String[] profiles){
    if(profiles.length==0)return;
    for(String profile:profiles){
        choose.addOption(profile, profile);
    }
    choose.setDefaultOption(profiles[0], profiles[0]);
}


  @Override
  public void robotPeriodic() {
    //shuffleMain();
    // shuffleTest();
  }


  @Override
  public void disabledInit() {
  }


  @Override
  public void disabledPeriodic() {
    shuffleTest();
    Scheduler.getInstance().run();
    if(choice != m_chooser.getSelected().intValue()){
      RGB.run();
    }
    choice = m_chooser.getSelected().intValue();

  }

  @Override
  public void autonomousInit() {

    Arm.target = Robot.arm.getTalon().getSelectedSensorPosition(0);  // Set the arm target to lowest position at beginning of the round
    OI.isAutonomous = true;  // Set is autonomous boolean to true

   // m_autonomousCommand = m_chooser.getSelected();
    if (m_autonomousCommand != null) {  // Start autonomous (No autonomous this year)
      m_autonomousCommand.start();
    }

  }


  @Override
  public void autonomousPeriodic() {
    shuffleMain();
    Scheduler.getInstance().run();
    if(choice != m_chooser.getSelected().intValue()){
      RGB.run();
    }
    choice = m_chooser.getSelected().intValue();
  }


  @Override
  public void teleopInit() {
    //Arm.target = Robot.arm.getTalon().getSelectedSensorPosition(0);  // Set the arm target to lowest position at beginning of the round

    Loader.loadJSON("profiles.json", new GameController(RobotMap.DRIVER_PORT), new GameController(RobotMap.GUNNER_PORT));

    OI.isAutonomous = false;  // At beginning of teleop, set autonomous to false

    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }

  }

  
  @Override
  public void teleopPeriodic() {
    shuffleTest();
    Scheduler.getInstance().run();
    if(choice != m_chooser.getSelected().intValue()){
      RGB.run();
    }
    choice = m_chooser.getSelected().intValue();
  }

  private void shuffleMain() {  // ShuffleBoard setup for competition
   
    SmartDashboard.putBoolean("Ball In Intake", FlyWheel.BALL_IN_INTAKE);
    SmartDashboard.putBoolean("ARM PID", OI.ARM_PID); 
    //SmartDashboard.putString("AUTO ALIGNMENT", AutoAlign.AUTO_ALIGNMENT_STATUS); // LOGIC NEEDS TO BE WRITTEN IN AutoAlign.java
    // SmartDashboard.putString("ROBOT ORIENTATION", ReverseRobot.ROBOT_ORIENTATION);

    try {                                                                                                                       
      // SmartDashboard.putBoolean("ROCKET LEVEL 1",);
      // SmartDashboard.putBoolean("ROCKET LEVEL 2",);
      // SmartDashboard.putBoolean("CARGO SHIP",);
    } catch(Exception e) {

    }

  }

  private void shuffleTest() {  // Shuffleboard setup for testing

   /* SmartDashboard.putNumber("ARM GET VALUE", arm.getTalon().getMotorOutputPercent());
    SmartDashboard.putBoolean("Ball", FlyWheel.push_button.get());
    SmartDashboard.putNumber("INTAKE SPEED ", DefaultFly.INTAKE_SPEED);
    */
    /* -- SHUFFLE BOARD DATA -- */

    // Hatch Limit Switches
    /*
    SmartDashboard.putBoolean("Hatch FAR", limit_switch.getHatchFar());
    SmartDashboard.putBoolean("Hatch CLOSE", limit_switch.getHatchClose());
    
    SmartDashboard.putBoolean("Ball", FlyWheel.push_button.get());
    
    // Arm Limit Switches
    SmartDashboard.putBoolean("Arm TOP", limit_switch.getArmTop());
    SmartDashboard.putBoolean("Arm BOTTOM", limit_switch.getArmBottom());
    */
    // Climb Limit Switches
    SmartDashboard.putNumber("voltage moter FL",drivetrain.getTalon(1).getMotorOutputVoltage());
    SmartDashboard.putNumber("voltage moter FR",drivetrain.getTalon(2).getMotorOutputVoltage());
    SmartDashboard.putNumber("voltage moter BL",drivetrain.getTalon(3).getMotorOutputVoltage());
    SmartDashboard.putNumber("voltage moter BR",drivetrain.getTalon(4).getMotorOutputVoltage());
    SmartDashboard.putBoolean("Climb ARM TOP", limit_switch.getClimbTopArm());
    SmartDashboard.putBoolean("Climb ARM BOTTOM", limit_switch.getClimbBottomArm());
    SmartDashboard.putBoolean("Climb HATCH TOP", limit_switch.getClimbTopHatch());
    SmartDashboard.putBoolean("Climb HATCH BOTTOM", limit_switch.getClimbBottomHatch());
    /*
    // NavX Data Values
    SmartDashboard.putNumber("NavX ANGLE ", Robot.climb.getNavX().getAngle());
    SmartDashboard.putNumber("NavX ROLL ", Robot.climb.getNavX().getRoll());
    SmartDashboard.putNumber("NavX PITCH ", Robot.climb.getNavX().getPitch());
    */
    // Values For Arm Data
   /* SmartDashboard.putNumber("Arm PID Target", Arm.target);
      SmartDashboard.putNumber("Arm Encoder Position", Robot.arm.getTalon().getSelectedSensorPosition(0));
    //ultrasonics 
    SmartDashboard.putNumber("ULTRASONIC ARM", vision_sensors.ultrasonic_ARM.getRangeInches());
    SmartDashboard.putNumber("ULTRASONIC HATCH", vision_sensors.ultrasonic_ARM.getRangeInches());
    // */
    // Values For Climb Data
    //SmartDashboard.putNumber("PHASE ", Climb.phase);
    SmartDashboard.putNumber("Climb Arm PID Target", Climb.target_arm);
    SmartDashboard.putNumber("Climb HATCH PID Target", Climb.target_hatch);
    SmartDashboard.putNumber("Climb HATCH Encoder Position", Robot.climb.getTalon(0).getSelectedSensorPosition(0));
    SmartDashboard.putNumber("Climb ARM Encoder Position", Robot.climb.getTalon(1).getSelectedSensorPosition(0));
    //linetracker.setUltrasonic(1);
    //linetracker.setActiveUltrasonic(1);
    /*vision_sensors.ultrasonic_ARM.setAutomaticMode(true);
    vision_sensors.ultrasonic_ARM.setEnabled(false);
    vision_sensors.ultrasonic_HATCH.setAutomaticMode(true);
    vision_sensors.ultrasonic_HATCH.setEnabled(true);*/
    //SmartDashboard.putNumber("Ultrasonic Hatch", linetracker.ultrasonic_HATCH.getRangeInches());
   // SmartDashboard.putNumber("Ultrasonic Arm", vision_sensors.ultrasonic_ARM.getRangeInches());
    //SmartDashboard.putNumber("Ultrasonic Hatch", vision_sensors.ultrasonic_HATCH.getRangeInches());

    //SmartDashboard.putNumber("Vision Mag", AutoAlign.drive_magnitude);
    //SmartDashboard.putNumber("Vision Rot", AutoAlign.drive_rotation);
    //SmartDashboard.putNumber("Vision Offset", AutoAlign.average_offset);
    /*
    SmartDashboard.putBoolean("EnabledA",  linetracker.getUltrasonic(0).isEnabled());
    SmartDashboard.putBoolean("EnabledH",  linetracker.getUltrasonic(0).isEnabled());
    SmartDashboard.putBoolean("IsValid", linetracker.getUltrasonic(0).isRangeValid());
    SmartDashboard.putNumber("Static", linetracker.getUltrasonic(0).getRangeInches());
    
    /*
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
