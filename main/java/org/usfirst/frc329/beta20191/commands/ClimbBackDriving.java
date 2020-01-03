/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc329.beta20191.commands;

import org.usfirst.frc329.beta20191.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ClimbBackDriving extends Command {
  public ClimbBackDriving() {
  }

  protected void initialize() {
    System.out.println("Climb Back Driving init");
    Robot.sDrive.setCoast();
  }

  protected void execute() {
    double driveSpeed = Robot.sClimb.getClimbDriveSpeed();
    Robot.sClimb.climbMove(driveSpeed);
    System.out.println("ClimbBackDriving speed = " + driveSpeed);
  }

  protected boolean isFinished() {
    return Robot.sClimb.backOverTheEdge();
  }

  protected void end() {
    System.out.println("Climb Back Driving end");
    Robot.sClimb.climbMove(0);
  }


  protected void interrupted() {
    end();
  }
}
