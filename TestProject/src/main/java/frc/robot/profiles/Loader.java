/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.profiles;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * Loads JSON file that contains the controller mappings
 */
public final class Loader {
    private static GameController driver, gunner;

    public static void loadJSON(String src, GameController driverC, GameController gunnerC){
        driver=driverC;
        gunner=gunnerC;
        Object obj;
        System.out.println("START PATH: "+System.getProperty("user.dir"));
        try{ // Loads file if it exists
            File f=new File("/home/lvuser/"+src);
            obj=new JSONParser().parse(new FileReader(f));
        } catch (IOException | ParseException e) {
            System.out.println("[ERROR] COULDN'T LOAD "+src.toUpperCase());
            e.printStackTrace();
            return;
        }
        System.out.println("********FOUND "+src.toUpperCase()+", NOW LOADING********");
        // Init json objects for driver
        JSONObject topObj=(JSONObject) obj;
        JSONObject driver=(JSONObject) topObj.get("driver");
        String[] driverProfs= setToString(driver.keySet()); // Some random stuff
        // Loads all driver info into ControllerProfile[]
        ControllerProfile[] drivers=new ControllerProfile[driverProfs.length];
        for(int i=0;i<drivers.length;i++){
            drivers[i]=loadProfile((JSONObject)driver.get(driverProfs[i]),driverProfs[i],"driver");
        }
        // Does the same as driver but for gunner
        JSONObject gunner=(JSONObject) topObj.get("gunner");
        String[] gunnerProfs=setToString(gunner.keySet());
        ControllerProfile[] gunners=new ControllerProfile[gunnerProfs.length];
        for(int i=0;i<gunners.length;i++){
            gunners[i]=loadProfile((JSONObject)gunner.get(gunnerProfs[i]),gunnerProfs[i],"gunner");
        }

        // Loads them all into one JSONMap
        JSONMap.loadProfiles(drivers,gunners);
        setupProfileManager();
        System.out.println("********CONTROLLER SETUP COMPLETE********");
    }

    //Sets up ProfileManager to be queried
    private static void setupProfileManager(){
        ProfileManager.setDriverType(driver.getType()=="XboxController"?ProfileManager.XBOX:ProfileManager.JOYSTICK);
        ProfileManager.setGunnerType(gunner.getType()=="XboxController"?ProfileManager.XBOX:ProfileManager.JOYSTICK);
        ProfileManager.setCurrentDriver(JSONMap.getDriverProfileNames()[0]);
        ProfileManager.setCurrentGunner(JSONMap.getGunnerProfileNames()[0]);
    }

    //Converts Set of Objects to String array
    private static String[] setToString(Set<Object> source){
        String[] dest = new String[source.size()];
        int iter=0;
        for (Object o : source) {
            dest[iter]=(String)o;
            iter++;
        }
        return dest;
    }

    //Loads a profile from a JSONObject
    private static ControllerProfile loadProfile(JSONObject profile,String name, String pilot){
        HashMap<String, JoystickButton> xboxMaps=new HashMap<>();
        fillMap(profile,xboxMaps,"xbox",pilot);
        HashMap<String,JoystickButton> joystickMaps=new HashMap<>();
        fillMap(profile,joystickMaps,"joystick",pilot);
        return new ControllerProfile(name,xboxMaps,joystickMaps);
    }

    //Fills a HasMap from a JSONObject
    private static void fillMap(JSONObject source, HashMap<String, JoystickButton> dest, String key, String pilot){
        JSONObject object=(JSONObject)source.get(key);
        Set keys=object.keySet();
        for(Object o:keys){
            JSONArray arr=(JSONArray)object.get(o);
            try{
                JoystickButton j=new JoystickButton(pilot.equals("driver")?driver.getController():gunner.getController() ,Integer.parseInt((String)arr.get(1)));
                dest.put((String)arr.get(0),j);
            }catch(NumberFormatException e){
                e.printStackTrace();
            }
        }
    }

    public static GameController getDriverController(){
        return driver;
    }

    public static GameController getGunnerController(){
        return gunner;
    }
}
