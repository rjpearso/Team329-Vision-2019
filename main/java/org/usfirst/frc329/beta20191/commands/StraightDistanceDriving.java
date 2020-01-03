/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc329.beta20191.commands;
import org.usfirst.frc329.beta20191.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class StraightDistanceDriving extends Command {
  //private static final double DISTANCE_MAX_ERROR = 6;
  private double d, currLoc, currabsVel, bp, m, b, error, absError, pow, a;
  private static final double MAX_POWER = .7; //.65  .8   .725
  private static final double MIN_POWER = .2;
  private static final double BRAKE_POWER = .45;
  private static final double MIN_VEL = 18; // Changes with min power //15
  private static final double TOO_FAR = 100; // Max power after
  private static final double MIN_ERROR = 25; // start linear equation  was 20
  private static final double GOOD_ENOUGH = 2; // Margin of error 
  private static final double STOP_BRAKE = 4.5; // active brake region
  private static boolean doOnce;

  private static final double HEADING_MAX_ERROR = 3;
  private double targetHeading;
  private double left, right;
  private double currentHeading, errors, absErrors, ms, bs, fix;
  private static final double MAX_POWER_S = .2;
  private static final double MIN_POWER_S = .08;
  private static final double GOOD_ENOUGH_S = HEADING_MAX_ERROR;
  private static final double MIN_ERROR_S = HEADING_MAX_ERROR*2.5;
  private static final double TOO_FAR_S = HEADING_MAX_ERROR*5; 
  private static final double NOT_AN_ANGLE = 999;

  public StraightDistanceDriving(double dist) {
    requires(Robot.sDrive);
    d = dist;
    a = NOT_AN_ANGLE; 
  }

  public StraightDistanceDriving(double dist, double angle) {
    requires(Robot.sDrive);
    d = dist;
    a = angle;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    System.out.println("a is " + a + " d is " + d);
    initDistDrive();
    initStraightDrive();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    execDistDrive();
    left = right = pow;
    execStraightDrive();
    Robot.sDrive.jSTankdrive(left + fix, right - fix);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if((Math.abs(d - currLoc) < GOOD_ENOUGH)&&(currabsVel < MIN_VEL)) return true;
    else return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.sDrive.jSTankdrive(0, 0);
    Robot.sDrive.squareInputs();
    System.out.println("StraightDistanceDriving Done" + "  currLoc = " + currLoc);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
  
  private void initDistDrive(){
    //currLoc = Robot.sDrive.getDistance();
    Robot.sDrive.clearDriveEnc();
    Robot.sDrive.regularInputs();
    m = (MAX_POWER - MIN_POWER) / (TOO_FAR - MIN_ERROR);
    b = MAX_POWER - (TOO_FAR * m);
    doOnce = false;
  }

  private void execDistDrive(){
    currLoc = Robot.sDrive.getDistance();
    error = d - currLoc;
    absError = Math.abs(error);
    currabsVel = Math.abs(Robot.sDrive.getVelocity());
    if(absError > TOO_FAR) pow = MAX_POWER;
    else if(absError > MIN_ERROR) pow = m * absError + b;
    else if(absError > STOP_BRAKE && currabsVel>MIN_VEL && !doOnce) 
      {
        double test;
        bp = BRAKE_POWER;
        test = Math.min(-MAX_POWER, Math.max(BRAKE_POWER, (BRAKE_POWER * (currabsVel / (2 * MIN_VEL)))));  //Max, min because brake power is negative
        //bp = test;
        System.out.printf("   test = %.3f", test);
        if(error >= 0) pow = -bp; else pow = bp;
      }
    else if(absError > GOOD_ENOUGH) 
    {
      if (d < 180) doOnce = true;
      pow = MIN_POWER;
    }
    //else if(Math.abs(Robot.sDrive.getVelocity()) > MIN_VEL) pow = -MIN_POWER;
    else pow = 0;
    if(error > 0) pow = pow * -1;
     //System.out.printf("CH = %.3f", currentHeading);
     System.out.printf("   error = %.3f", error);
     System.out.printf("   vel = %.3f", currabsVel);
     System.out.printf("   pow = %.3f %n", pow);
  }

  private void initStraightDrive(){
    if(a == NOT_AN_ANGLE)
      targetHeading = Robot.sDrive.getYaw();
    else
      targetHeading = a; //angle from constructor
    Robot.sDrive.regularInputs();
    ms = (MAX_POWER_S - MIN_POWER_S) / (TOO_FAR_S - MIN_ERROR_S);
    bs = MAX_POWER_S - (TOO_FAR_S * ms);
  }

  private void execStraightDrive(){
    currentHeading = Robot.sDrive.getYaw();
    errors = currentHeading-targetHeading;
    //if(errors <= -180) errors = 360 + errors;
    //else if(errors > 180) errors = error - 360;
    absErrors = Math.abs(errors);
    if(absErrors > TOO_FAR_S) fix = MAX_POWER_S;
    else if(absErrors > MIN_ERROR_S) fix = ms * absErrors + bs;
    else if(absErrors > GOOD_ENOUGH_S) fix = MIN_POWER_S;
    else fix = 0;
    if(errors < 0) fix = fix * -1;
   
    //System.out.printf("CH = %.3f", currentHeading);
    System.out.printf("   errors = %.3f", errors);
    System.out.printf("   fix = %.3f %n", fix);
    //System.out.printf("   left = %.3f", left);
    //System.out.printf("   right = %.3f %n", right);
  }
}
