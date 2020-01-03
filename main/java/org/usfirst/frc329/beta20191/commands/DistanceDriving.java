/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc329.beta20191.commands;
import org.usfirst.frc329.beta20191.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class DistanceDriving extends Command {
  //private static final double DISTANCE_MAX_ERROR = 6;
  private static double d, currLoc, currVel, bp, m, b, error, absError, pow;
  private static final double MAX_POWER = .8;
  private static final double MIN_POWER = .2;
  private static final double BRAKE_POWER = -.45;
  private static final double MIN_VEL = 15; // Changes with min power
  private static final double TOO_FAR = 100; // Max power after
  private static final double MIN_ERROR = 20; // start linear equation
  private static final double GOOD_ENOUGH = 2; // Margin of error 
  private static final double STOP_BRAKE = 4.5; // active brake region
  private static boolean doOnce;

  public DistanceDriving(double dist) {
    requires(Robot.sDrive);
    d = dist;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    currLoc = Robot.sDrive.getDistance();
    Robot.sDrive.clearDriveEnc();
    Robot.sDrive.regularInputs();
    m = (MAX_POWER - MIN_POWER) / (TOO_FAR - MIN_ERROR);
    b = MAX_POWER - (TOO_FAR * m);
    doOnce = false;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    currLoc = Robot.sDrive.getDistance();
    error = d - currLoc;
    absError = Math.abs(error);
    currVel = Robot.sDrive.getVelocity();
    if(absError > TOO_FAR) pow = MAX_POWER;
    else if(absError > MIN_ERROR) pow = m * absError + b;
    else if(absError > STOP_BRAKE && Math.abs(currVel)>MIN_VEL && !doOnce) 
      {
        bp = BRAKE_POWER;
        // bp = Math.min(BRAKE_POWER, BRAKE_POWER * ((currVel - MIN_VEL) / MIN_VEL));
        if(error >= 0) pow = bp; else pow = -bp;
      }
    else if(absError > GOOD_ENOUGH) 
    {
      doOnce = true;
      pow = MIN_POWER;
    }
    //else if(Math.abs(Robot.sDrive.getVelocity()) > MIN_VEL) pow = -MIN_POWER;
    else pow = 0;
    if(error > 0) pow = pow * -1;
    Robot.sDrive.jSTankdrive(pow, pow);
    //System.out.printf("CH = %.3f", currentHeading);
    System.out.printf("   error = %.3f", error);
    System.out.printf("   vel = %.3f", currVel);
    System.out.printf("   pow = %.3f %n", pow);

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if((Math.abs(d - currLoc) < GOOD_ENOUGH)&&(Math.abs(currVel) < MIN_VEL)) return true;
    else return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.sDrive.jSTankdrive(0, 0);
    Robot.sDrive.squareInputs();
    System.out.println("DistanceDriving Done" + "  currLoc = " + currLoc);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
