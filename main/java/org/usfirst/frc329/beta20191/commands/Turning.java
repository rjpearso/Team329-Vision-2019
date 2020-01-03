/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc329.beta20191.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc329.beta20191.Robot;

public class Turning extends Command {
  public double targetAngle, currAngle;
  public boolean angleFlag;
  public static final double MIN_POWER = .37;
  public static final double MAX_POWER = .6; // .5
  public static final double TOO_FAR = 52;
  public static final double CLOSE_ENOUGH = 3.0;
  public static double error; 
  
  public Turning(double angle) {
    requires(Robot.sDrive);
    targetAngle = angle;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.sDrive.regularInputs();
    angleFlag = false;
    
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double pow = 0;
    currAngle = Robot.sDrive.ahrs.getYaw();
    error = currAngle - targetAngle; //Math.abs(currAngle - targetAngle); // maybe targetangle - currangle?
    if(Math.abs(error) > TOO_FAR) pow = MAX_POWER;
    else pow = MIN_POWER;
    if (Math.abs(error) <= CLOSE_ENOUGH){
      angleFlag = true;
    }
    else if(Math.abs(error) <= 180){
       if (error > 0){ 
        Robot.sDrive.jSTankdrive(pow, -pow); //ccw
      }
      else if (error < 0){
        Robot.sDrive.jSTankdrive(-pow, pow); // cw 
      }
    }
    else{ //Math.abs(error) > 180
      if (error > 0){ 
        Robot.sDrive.jSTankdrive(-pow, pow); //cw
      }
      else if (error < 0){
        Robot.sDrive.jSTankdrive(pow, -pow); //ccw
      }
    }
    System.out.print("Yaw is = " + currAngle + " error is = " + error);
    System.out.println(" pow = "+ pow);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return angleFlag;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.sDrive.jSTankdrive(0, 0);
    Robot.sDrive.squareInputs();
    System.out.println("PIDTurning ending yaw = " + currAngle + " error = " + error);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
