/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.subsystems.I2CInterface;

public class I2CControl extends Command {
  private final double DEADZONE=0.1;
  public I2CControl() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.i2cInterface);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    //Turns off the speaker
    I2CInterface.getI2C().writeBulk(convert("SET_SOUND"));
    I2CInterface.getI2C().writeBulk(convert("0"));
  }
  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //Sends joystick value to RGBs
    int y=(int)(Math.abs(OI.driver.getY(Hand.kLeft)*255));
    if(Math.abs(OI.driver.getY(Hand.kLeft))<DEADZONE){//Deals with joystick drift
      y=0;
    }
    sendRGB(0, y, y, y);
  }
  private final double BRIGHTNESS=0.5;//0 to 1, controls brightness of LEDs
  //These 3 methods send individual color values to the Arduino. These are faster than sendRGB().
  private void sendRed(int strip, int red){
    //Voltage protection on Arduino
    I2CInterface.getI2C().writeBulk(convert("SET_STRIP"));
    I2CInterface.getI2C().writeBulk(convert(strip+""));
    I2CInterface.getI2C().writeBulk(convert("SET_COLOR"));
    I2CInterface.getI2C().writeBulk(convert("0"));
    I2CInterface.getI2C().writeBulk(convert("SET_BRIGHT"));
    I2CInterface.getI2C().writeBulk(convert(red+""));
  }
  private void sendGreen(int strip,int green){
    //Voltage protection on Arduino
    I2CInterface.getI2C().writeBulk(convert("SET_STRIP"));
    I2CInterface.getI2C().writeBulk(convert(strip+""));
    I2CInterface.getI2C().writeBulk(convert("SET_COLOR"));
    I2CInterface.getI2C().writeBulk(convert("1"));
    I2CInterface.getI2C().writeBulk(convert("SET_BRIGHT"));
    I2CInterface.getI2C().writeBulk(convert(green+""));
    
  }
  private void sendBlue(int strip,int blue){
    //Voltage protection on Arduino
    if(blue>255)blue=255;
    else if(blue<0)blue=0;
    blue=(int)(BRIGHTNESS*blue);
    I2CInterface.getI2C().writeBulk(convert("SET_STRIP"));
    I2CInterface.getI2C().writeBulk(convert(strip+""));
    I2CInterface.getI2C().writeBulk(convert("SET_COLOR"));
    I2CInterface.getI2C().writeBulk(convert("2"));
    I2CInterface.getI2C().writeBulk(convert("SET_BRIGHT"));
    I2CInterface.getI2C().writeBulk(convert(blue+""));
  }


  //Use this method if you want to send a completely different RGB value. This method is slower.
  private void sendRGB(int strip,int red,int green,int blue){//Strip should be 0 unless there are multiple strips connected
    sendRed(strip, red);
    sendGreen(strip, green);
    sendBlue(strip, blue);
  }

  //Converts a string into a byte array to be sent over I2C
  private byte[] convert(String in){
    byte[] out=new byte[in.length()];
    for(int i=0;i<out.length;i++){
      out[i]=(byte)in.charAt(i);
    }
    return out;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
