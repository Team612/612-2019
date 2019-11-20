/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.profiles;

import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * Main class that determines what profile is currently in use, also
 * queries profiles when a JoystickButton object is requested
 */
public final class ProfileManager {
    public static final int XBOX=0,JOYSTICK=1;
    private static String currentDriver, currentGunner;
    private static int driverType, gunnerType;

    public static void setDriverType(int driverType) {
        ProfileManager.driverType = driverType;
    }

    public static void setGunnerType(int gunnerType) {
        ProfileManager.gunnerType = gunnerType;
    }

    public static void setCurrentDriver(String driver){
        currentDriver=driver;
    }

    public static void setCurrentGunner(String gunner){
        currentGunner=gunner;
    }

    public static JoystickButton getDriverButton(String key){
        ControllerProfile p = JSONMap.getDriverProfile(currentDriver);
        return p!=null?(driverType==XBOX?p.getXbox():p.getJoystick()).get(key):null;
    }

    public static JoystickButton getGunnerButton(String key){
        ControllerProfile p = JSONMap.getGunnerProfile(currentGunner);
        return p!=null?(gunnerType==XBOX?p.getXbox():p.getJoystick()).get(key):null;
    }
}
