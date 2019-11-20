/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.profiles;

/**
 * Stores all of the profiles imported from the JSON file
 */
public final class JSONMap {
    private static ControllerProfile[] drivers,gunners;

    public static void loadProfiles(ControllerProfile[] drivers, ControllerProfile[] gunners){
        if(drivers!=null){
            JSONMap.drivers =drivers;
            ProfileManager.setCurrentDriver(getDriverProfileNames()[0]);
        }
        if(gunners!=null){
            JSONMap.gunners=gunners;
            ProfileManager.setCurrentGunner(getGunnerProfileNames()[0]);
        }
    }

    public static ControllerProfile getDriverProfile(String name){
        for(ControllerProfile driver:drivers){
            if(driver.getName().equals(name))return driver;
        }
        return null;
    }

    public static ControllerProfile getGunnerProfile(String name){
        for(ControllerProfile gunner:gunners){
            if(gunner.getName().equals(name))return gunner;
        }
        return null;
    }

    public static String[] getDriverProfileNames(){
        String[] names=new String[drivers.length];
        for(int i=0;i<names.length;i++){
            names[i]=drivers[i].getName();
        }
        return names;
    }

    public static String[] getGunnerProfileNames(){
        String[] names=new String[gunners.length];
        for(int i=0;i<names.length;i++){
            names[i]=gunners[i].getName();
        }
        return names;
    }
}
