/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.profiles;

import java.util.HashMap;

import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * Container class
 */
public class ControllerProfile {
    private String name;
    private HashMap<String,JoystickButton> xbox, joystick;
    public ControllerProfile(String name, HashMap<String, JoystickButton> xbox, HashMap<String, JoystickButton> joystick){
        this.joystick=joystick;
        this.name=name;
        this.xbox=xbox;
    }

    public String getName() {
        return name;
    }

    public HashMap<String, JoystickButton> getJoystick() {
        return joystick;
    }

    public HashMap<String, JoystickButton> getXbox() {
        return xbox;
    }
}
