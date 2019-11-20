package frc.robot.commands;

import edu.wpi.first.wpilibj.I2C;
import frc.robot.Robot;
import frc.robot.subsystems.RGBInterface;

public class RealRGB {
  private final double DEADZONE=0.1;
  public static int choice; // 1 = Fade, 2 = Red, 3 = Blue
  private boolean end = false; 
  

    public RealRGB(){
    
    }


  public void run() {
    I2C i2c=RGBInterface.getI2C();
    i2c.writeBulk(convert("SET_SOUND"));
    i2c.writeBulk(convert("0"));
    choice = Robot.m_chooser.getSelected().intValue();
    System.out.println(choice);
    switch(choice){
    case 1: // Fade
        sendRGB(0, 0, 0, 0);
      end = true;
      System.out.println("[RGB] Color invalid, setting to RAINBOW");
      sendCommand("SET_RAIN");
      sendCommand("1");
      System.out.println("fade");
      break;
    case 2:  // Red
      end = false;
      sendRGB(0, 0, 0, 0);
      System.out.println("[RGB] Set color to RED");
      sendRGB(0, 255, 0, 0);
      System.out.println("red");
      break;
    case 3:  // Blue
        sendRGB(0, 0, 0, 0);
      end = false;
      System.out.println("[RGB] Set color to BLUE");
      sendRGB(0, 0, 0, 255);
      System.out.println("blue");
      break;
    case 4:
        sendRGB(0, 0, 0, 0);
        System.out.println("[RGB] Set color to PURPLE");
        sendRGB(0, 255, 0, 255);
        break;
    case 5:
        sendRGB(0, 0, 0, 0);
        System.out.println("RANDOM");
        sendRGB(0, (int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256));
        break;
    }
  }
  private void sendCommand(String command){//Sends general I2C command
    RGBInterface.getI2C().writeBulk(convert(command));
  }
  private final double BRIGHTNESS = 1;//0 to 1, controls brightness of LEDs
  //These 3 methods send individual color values to the Arduino. These are faster than sendRGB().
  private void sendRed(int strip,int red){
    //Voltage protection on Arduino
    if(red > 255){
        red = 255;
    }
    else if(red < 0){
        red = 0;
    }
    red = (int) (BRIGHTNESS * red);
    RGBInterface.getI2C().writeBulk(convert("SET_STRIP"));
    RGBInterface.getI2C().writeBulk(convert(strip+""));
    RGBInterface.getI2C().writeBulk(convert("SET_COLOR"));
    RGBInterface.getI2C().writeBulk(convert("0"));
    RGBInterface.getI2C().writeBulk(convert("SET_BRIGHT"));
    RGBInterface.getI2C().writeBulk(convert(red+""));
  }
  private void sendGreen(int strip,int green){
    if(green > 255){
        green = 255;
    }
    else if(green < 0){
        green = 0;
    }
    green = (int) (BRIGHTNESS * green);
    //Voltage protection on Arduino
    RGBInterface.getI2C().writeBulk(convert("SET_STRIP"));
    RGBInterface.getI2C().writeBulk(convert(strip+""));
    RGBInterface.getI2C().writeBulk(convert("SET_COLOR"));
    RGBInterface.getI2C().writeBulk(convert("1"));
    RGBInterface.getI2C().writeBulk(convert("SET_BRIGHT"));
    RGBInterface.getI2C().writeBulk(convert(green+""));
  }
  private void sendBlue(int strip,int blue){
    //Voltage protection on Arduino
    if(blue>255){
        blue = 255;
    }
    else if(blue<0){
        blue = 0;
    }
    blue = (int)(BRIGHTNESS * blue);
    RGBInterface.getI2C().writeBulk(convert("SET_STRIP"));
    RGBInterface.getI2C().writeBulk(convert(strip+""));
    RGBInterface.getI2C().writeBulk(convert("SET_COLOR"));
    RGBInterface.getI2C().writeBulk(convert("2"));
    RGBInterface.getI2C().writeBulk(convert("SET_BRIGHT"));
    RGBInterface.getI2C().writeBulk(convert(blue+""));
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

}