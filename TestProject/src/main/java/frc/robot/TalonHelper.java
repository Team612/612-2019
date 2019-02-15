/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class TalonHelper {
    TalonHelper(){}
    public boolean getArmLimitBottom(){
        return Robot.arm.getTalon().getSensorCollection().isRevLimitSwitchClosed();
    }
    public boolean getArmLimitTop(){
        return Robot.arm.getTalon().getSensorCollection().isFwdLimitSwitchClosed();
    }

    public boolean getHatchLimitOut(){//Guess
        return Robot.hatch.getTalon().getSensorCollection().isFwdLimitSwitchClosed();
    }
    public boolean getHatchLimitIn(){//Guess
        return Robot.hatch.getTalon().getSensorCollection().isRevLimitSwitchClosed();
    }

    /*public boolean getClimbLimitTopArm(){
        return Robot.hatch.getTalon().getSensorCollection().isRevLimitSwitchClosed();
    }
    public boolean getClimbLimitBottomArm(){
        return Robot.hatch.getTalon().getSensorCollection().isRevLimitSwitchClosed();
    }
    public boolean getClimbLimitTopHatch(){
        return Robot.hatch.getTalon().getSensorCollection().isRevLimitSwitchClosed();
    }
    public boolean getClimbLimitBottomHatch(){
        return Robot.hatch.getTalon().getSensorCollection().isRevLimitSwitchClosed();
    }*/
    
}
