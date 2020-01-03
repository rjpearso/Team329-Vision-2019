/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc329.beta20191.commands;


import org.usfirst.frc329.beta20191.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class StraightDriving extends Command {
  private static final double HEADING_MAX_ERROR = 3;
  private double targetHeading;
  private static double left, right;
  private static double currentHeading, error, absError, m, b, fix;
  private static final double MAX_POWER = .2;
  private static final double MIN_POWER = .08;
  private static final double GOOD_ENOUGH = HEADING_MAX_ERROR;
  private static final double MIN_ERROR = HEADING_MAX_ERROR*2.5;
  private static final double TOO_FAR = HEADING_MAX_ERROR*5;

  public StraightDriving(double l, double r) {
    // Use requires() here to declare subsystem dependencies
     requires(Robot.sDrive);
     left = l;
     right = r;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
   targetHeading = Robot.sDrive.getYaw();
   Robot.sDrive.regularInputs();
   m = (MAX_POWER - MIN_POWER) / (TOO_FAR - MIN_ERROR);
   b = MAX_POWER - (TOO_FAR * m);
   
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    currentHeading = Robot.sDrive.getYaw();
    error = currentHeading-targetHeading;
    absError = Math.abs(error);
    if(absError > TOO_FAR) fix = MAX_POWER;
    else if(absError > MIN_ERROR) fix = m * absError + b;
    else if(absError > GOOD_ENOUGH) fix = MIN_POWER;
    else fix = 0;
    if(error < 0) fix = fix * -1;
    Robot.sDrive.jSTankdrive(left + fix, right - fix);
    //System.out.printf("CH = %.3f", currentHeading);
    System.out.printf("   error = %.3f", error);
    System.out.printf("   fix = %.3f %n", fix);
    //System.out.printf("   left = %.3f", left);
    //System.out.printf("   right = %.3f %n", right);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.sDrive.jSTankdrive(0, 0);
    Robot.sDrive.squareInputs();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
