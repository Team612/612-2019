/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class LimitSwitchHelper {

    public LimitSwitchHelper(){}
    public boolean getArmBottom(){
        return Robot.arm.getTalon().getSensorCollection().isRevLimitSwitchClosed();
    }
    public boolean getArmTop(){
        return Robot.arm.getTalon().getSensorCollection().isFwdLimitSwitchClosed();
    }
    /*
    public boolean getHatchFar(){//Guess
        return Robot.hatch.getTalon().getSensorCollection().isFwdLimitSwitchClosed();
    }
    public boolean getHatchClose(){//Guess
        return Robot.hatch.getTalon().getSensorCollection().isRevLimitSwitchClosed();
    }
    */
    public boolean getClimbTopArm(){
        return Robot.climb.getTalon(1).getSensorCollection().isFwdLimitSwitchClosed();
    }
    public boolean getClimbBottomArm(){
        return Robot.climb.getTalon(1).getSensorCollection().isRevLimitSwitchClosed();
    }
    public boolean getClimbTopHatch(){
        return Robot.climb.getTalon(0).getSensorCollection().isFwdLimitSwitchClosed();
    }
    public boolean getClimbBottomHatch(){
        return Robot.climb.getTalon(0).getSensorCollection().isRevLimitSwitchClosed();
    }
    public boolean getBallStatus(){
        return Robot.flyWheel.getButton().get();
    }
   
    
}
