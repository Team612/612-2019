/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.HashMap;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.profiles.GameController;

/**
 * Add your docs here.
 */
public class POVConvert {
    
    
    private GameController controller;  // Initialize Joystick
    private boolean preferredDir = true;  // If true, preference given to up/down. If false, preference given to left/right
    
    public HashMap<Integer, String> pov_values = new HashMap<Integer, String>();  // Dictionary to store direction values

    // Degree values of cardinal directions
    int NORTH = 0;
    int EAST  = 90;
    int SOUTH = 180;
    int WEST  = 270;

    // Degree of subcardinal directions
    int SOUTH_WEST = 225;
    int SOUTH_EAST = 135;
    int NORTH_WEST = 315;
    int NORTH_EAST = 45;

    POVConvert(GameController controller) {  // Constructor
        this.controller = controller;  // Store existing controller value to extend value
        populate_dictionary();  // Populate the dictionary with direction values
    }

    public void setPreference(boolean preferNorth) {//Set what direction to prefer if in the middle
        preferredDir = preferNorth;  // Set the prefered direction when dealing with diagonal inputs
    }

    private void populate_dictionary() {  // Function to populate the dictionary with values

        // Populate the dictionary with location values
        pov_values.put(NORTH, "North");
        pov_values.put(EAST, "East");
        pov_values.put(SOUTH, "South");
        pov_values.put(WEST, "West");

        // Diagonal D-Pad presses
        pov_values.put(SOUTH_WEST, "South West");
        pov_values.put(SOUTH_EAST, "South East");
        pov_values.put(NORTH_WEST, "North West");
        pov_values.put(NORTH_EAST, "North East");

        // Error value of dictionary
        pov_values.put(-1, "Invalid");

    }

    public String get_direction() {
        int currentPOV = controller.getController().getPOV();  // Get the current POV from the controller

        if (currentPOV == NORTH || currentPOV == SOUTH || currentPOV == EAST || currentPOV == WEST) {  // If a cardinal angle run normal loop
            return pov_values.get(currentPOV);
        } else {
            return pov_values.get(simplify(currentPOV));  // Else run, the simplified version
        }
        
    }

    public String get_exact_direction() {

        int currentPOV = controller.getController().getPOV();  // Get the current POV from the controller
        return pov_values.get(currentPOV);

    }

    private int simplify(int angle) {  //Simplifies if value is in the middle

        if (preferredDir) {  // Return North or South if preferred direction is true

            if (angle == NORTH_EAST || angle == NORTH_WEST) {  // If the value is north east or north west
                return NORTH;  // Return North
            } else if (angle == SOUTH_EAST || angle == SOUTH_WEST) {  // If the value is south east or south west
                return SOUTH;  // Return South
            }

        } else {  // Same logic but instead returns either east or west
            if (angle == NORTH_EAST || angle == NORTH_WEST) {
                return EAST;
            } else if (angle == SOUTH_EAST || angle == SOUTH_WEST) {
                return WEST;
            }
        }
        return -1;  // Else return the error value
    }

}
