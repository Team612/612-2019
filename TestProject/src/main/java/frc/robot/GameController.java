/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

/**
 * Add your docs here.
 */
public class GameController {

    private XboxController controller;
    
    private final int XBOX=0, FLIGHTSTICK=1;

    private int type;

    public GameController(int port){
        controller = new XboxController(port);
        type = controller.getType().toString()=="kHIDJoystick"?XBOX:FLIGHTSTICK;
    }

    public double getX() {
        return type == FLIGHTSTICK ? controller.getRawAxis(0) : controller.getX(Hand.kLeft);
    }

    public double getY(){
        return type == FLIGHTSTICK ? controller.getRawAxis(1) : controller.getY(Hand.kLeft);
    }
    
    public double getRotation(){
        return type == FLIGHTSTICK ? controller.getRawAxis(2) : controller.getX(Hand.kRight);
    }

    public double[] getTrigger(){
        return type == FLIGHTSTICK ? new double[]{controller.getRawButton(0)?1:0} : new double[]{controller.getTriggerAxis(Hand.kLeft),controller.getTriggerAxis(Hand.kRight)};
    }

    public String getType() {
        return type == FLIGHTSTICK ? "Flightstick" : "Xbox";
    }


    
}
