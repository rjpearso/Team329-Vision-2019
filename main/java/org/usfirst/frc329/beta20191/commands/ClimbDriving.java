/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc329.beta20191.commands;

import org.usfirst.frc329.beta20191.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ClimbDriving extends Command {
  
  public ClimbDriving() {
    
  }

  protected void initialize() {
    System.out.println("Climb Driving init");
    Robot.sDrive.setCoast();
  }

  protected void execute() {
    double driveSpeed = Robot.sClimb.getClimbDriveSpeed();
    Robot.sClimb.climbMove(driveSpeed);
    System.out.println("ClimbDriving speed = " + driveSpeed);
  }

  protected boolean isFinished() {
    return Robot.sClimb.frontOverTheEdge();
    //return false;
  }

  protected void end() {
    System.out.println("Climb Driving end");
    Robot.sClimb.climbMove(0);

  }

  protected void interrupted() {
    end();
  }
}
