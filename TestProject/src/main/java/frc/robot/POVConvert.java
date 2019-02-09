/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

/**
 * Add your docs here.
 */
public class POVConvert {
    
    public static final int POV_UP=0;
    public static final int POV_UR_DIAG=1;
    public static final int POV_RIGHT=2;
    public static final int POV_DR_DIAG=3;
    public static final int POV_DOWN=4;
    public static final int POV_DL_DIAG=5;
    public static final int POV_LEFT=6;
    public static final int POV_UL_DIAG=7;
    private XboxController controller;
    private boolean preferredDir;//If true,preference given to up/down. If false, preference given to left/right
    POVConvert(XboxController controller,boolean preferNorth){
        this.controller=controller;
        preferredDir=preferNorth;
    }

    public void setPreference(boolean preferNorth){//Set what direction to prefer if in the middle
        preferredDir=preferNorth;
    }

    public int getCardinal(){//Only can return 5 values: -1, 0, 2, 4, and 6
        if(controller.getPOV()==-1)return -1;
        switch(controller.getPOV()){
            case 0:return 0;
            case 90:return 2;
            case 180:return 4;
            case 270:return 6;
            default:return simplify();
        }
    }

    private int simplify(){//Simplifies if value is in the middle
        int p=controller.getPOV()/45;
        if(preferredDir){
            if(p==1||p==7)return 0;
            else if(p==3||p==5)return 4;
        }else{
            if(p==1||p==7)return 2;
            else if(p==3||p==5)return 6;
        }
        return -1;
    }

    public int getExtended(){//Returns normal value
        if(controller.getPOV()==-1)return -1;
        return controller.getPOV()/45;
    }
}
