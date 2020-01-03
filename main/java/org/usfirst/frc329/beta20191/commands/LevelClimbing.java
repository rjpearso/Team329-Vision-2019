/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc329.beta20191.commands;

import org.usfirst.frc329.beta20191.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;

public class LevelClimbing extends Command {
  private static double front, back; 
  private static final double PITCH_MAX_ERROR = .4;
  private double targetPitch;
  private double currentPitch, error, absError, ms, bs, fix;
  private static final double MAX_POWER_S = .12;
  private static final double MIN_POWER_S = .03;
  private static final double GOOD_ENOUGH_S = PITCH_MAX_ERROR;
  private static final double MIN_ERROR_S = PITCH_MAX_ERROR*2.5;
  private static final double TOO_FAR_S = PITCH_MAX_ERROR*7;
  Timer t;
  private static final double GOINGUPTIME = 1.00;
  public LevelClimbing() {
    requires(Robot.sClimb);
  }

  protected void initialize() {
    t = new Timer();
    t.start();
    System.out.println("LevelClimbing Init");
    initLevelClimb();
    front = back = Robot.sClimb.getClimbSpeed();
  }

  protected void execute() {
    front = back = Robot.sClimb.getClimbSpeed();
    execLevelClimb();
    back = back - fix;
    front = front + fix;
    if(back > Robot.sClimb.getStaySpeed()) back = Robot.sClimb.getStaySpeed();
    if(front > Robot.sClimb.getStaySpeed()) front = Robot.sClimb.getStaySpeed();
    System.out.println("LC back = " + back +" front = " + front + " fix = " + fix);
    Robot.sClimb.setClimberSpeed(front, back);
  }

  protected boolean isFinished() {
    //return false;
    return (t.get() > GOINGUPTIME);
    //return Robot.sClimb.climbHighEnough();
  }

  protected void end() {
    System.out.println("LevelClimbing Done");
    back = front = Robot.sClimb.getStaySpeed();
    Robot.sClimb.setClimberSpeed(front, back);
  }

  protected void interrupted() {
    end();
  }
  
  private void initLevelClimb(){
    targetPitch = Robot.climbStartPitch;    
    ms = (MAX_POWER_S - MIN_POWER_S) / (TOO_FAR_S - MIN_ERROR_S);
    bs = MAX_POWER_S - (TOO_FAR_S * ms);
  }

  private void execLevelClimb(){
    currentPitch = Robot.sDrive.getPitch();
    error = currentPitch-targetPitch;
    absError = Math.abs(error);
    if(absError > TOO_FAR_S) fix = MAX_POWER_S;
    else if(absError > MIN_ERROR_S) fix = ms * absError + bs;
    else if(absError > GOOD_ENOUGH_S) fix = MIN_POWER_S;
    else fix = 0;
    if(error < 0) fix = fix * -1; // if back is too high then the error is negative 
   
    System.out.printf("Pitch error = %.3f", error);
    System.out.printf(" fix = %.3f %n", fix);
  }
}
