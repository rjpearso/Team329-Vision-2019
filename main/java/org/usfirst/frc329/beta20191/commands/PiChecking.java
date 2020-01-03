/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc329.beta20191.commands;

import org.usfirst.frc329.beta20191.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Command;

public class PiChecking extends Command {
  private static int counter;
  private static boolean turnLeft = false; //
  private static boolean turnRight = false; //
  private static double turnAng, error, currAngle, targetAngle, d; //
  private static final double CLOSE_ENOUGH = 2.25; //4.5
  private static double piIsAlive = -1; //

  public PiChecking() {
    requires(Robot.sPi);
  }

  protected void initialize() {
  }

  protected void execute() {
    if(counter++ % 10 == 0)
      Robot.sPi.checkPi();
    turnDirection();
  }

  protected boolean isFinished() {
    return false;
  }

  protected void end() {
  }

  protected void interrupted() {
  }

  public void turnDirection(){
    d = SmartDashboard.getNumber("Distance Away", d);
    double pia;
    pia = SmartDashboard.getNumber("Turn Angle", turnAng);
    if(pia != piIsAlive) {
      turnAng = pia;
      targetAngle = turnAng + Robot.sDrive.getYaw();
    }
    currAngle = Robot.sDrive.ahrs.getYaw();
    error = currAngle - targetAngle;
    if(d < 24){
      turnRight = false;
      turnLeft = false;
    }
    else if(turnAng > CLOSE_ENOUGH){
      turnRight = false;
      turnLeft = true;
    }
    else if(turnAng < -CLOSE_ENOUGH){
      turnRight = true;
      turnLeft = false;
    }
    else if((turnAng > -CLOSE_ENOUGH) && (turnAng < CLOSE_ENOUGH)){
      turnRight = true;
      turnLeft = true;
    }
    Robot.sPi.setTurnLeft(turnLeft);
    Robot.sPi.setTurnRight(turnRight);
  }

}
