/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc329.beta20191.subsystems;

import org.usfirst.frc329.beta20191.commands.PiChecking;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class sPi extends Subsystem {

  private static boolean piAlive;
  private static double lastVal;
  private static boolean seeAngle = false;
  public static boolean turnLeft = false;
  public static boolean turnRight = false;

  public sPi(){
    // System.out.println("sPi Start");
  }
  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new PiChecking());
  }

  public boolean piRunning(){
    return piAlive;
  }

  public void checkPi(){
    double currVal = 0;
    currVal = SmartDashboard.getNumber("Pi is alive", currVal);
    if(currVal != lastVal){
      lastVal = currVal;
      piAlive = true;
    }
    else 
      piAlive = false;
  }

  public void setSeeAngle(boolean b){
      seeAngle = b;
  }

  public boolean getSeeAngle(){
    return seeAngle;
  }

  public void setTurnLeft(boolean b){
    turnLeft = b;
  }

  public void setTurnRight(boolean b){
    turnRight = b;
  }

  public boolean getTurnRight(){
    return turnRight;
  }

  public boolean getTurnLeft(){
    return turnLeft;
  }
}
  

