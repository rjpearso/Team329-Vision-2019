/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc329.beta20191.subsystems;
import org.usfirst.frc329.beta20191.Robot;
import edu.wpi.first.wpilibj.command.PIDSubsystem;


public class sPIDDrive extends PIDSubsystem {

  private static double counter = 0;
  private static final double HEADING_MAX_ERROR = 3;
  private double targetHeading;
  private double currentHeading, errors, absErrors, ms, bs, fix;
  private static final double MAX_POWER_S = .25;
  private static final double MIN_POWER_S = .08;
  private static final double GOOD_ENOUGH_S = HEADING_MAX_ERROR;
  private static final double MIN_ERROR_S   = HEADING_MAX_ERROR*2.5;
  private static final double TOO_FAR_S     = HEADING_MAX_ERROR*5; 
  private static final double MAX_DRIVE_POWER = .65; //.45

  public sPIDDrive() {
    super("sPIDDrive", 0.15, 0, 0, .11288);  //.15, 0, 0, .11288
    // System.out.println("sPIDDrive Start");
    getPIDController().setAbsoluteTolerance(2);
    getPIDController().reset();
    getPIDController().setContinuous(false);
    getPIDController().setInputRange(-300, 300);
    getPIDController().setOutputRange(-MAX_DRIVE_POWER, MAX_DRIVE_POWER);
    getPIDController().disable();
  }

  public void initDefaultCommand() {
  }

  protected double returnPIDInput() {
    double retVal = Robot.sDrive.sDriveencRight.getDistance();
    if ((counter % 10) == 0)
      System.out.println("PIDDRIVEIn = " + retVal);
    return retVal;
  }

  protected void usePIDOutput(double output) {
      if ((counter++ % 10) == 0)
          System.out.println("PIDDRIVEOut = " + output);
      Robot.sDrive.jSTankdrive(-output + fix, -output - fix);
  }

  public void initPIDStraightDriving(){ //assumes regular inputs
    targetHeading = Robot.sDrive.getYaw();
    ms = (MAX_POWER_S - MIN_POWER_S) / (TOO_FAR_S - MIN_ERROR_S);
    bs = MAX_POWER_S - (TOO_FAR_S * ms);
  }

  public void execPIDStraightDriving(){
    currentHeading = Robot.sDrive.getYaw();
    errors = currentHeading-targetHeading;
    absErrors = Math.abs(errors);
    if(absErrors > TOO_FAR_S) fix = MAX_POWER_S;
    else if(absErrors > MIN_ERROR_S) fix = ms * absErrors + bs;
    else if(absErrors > GOOD_ENOUGH_S) fix = MIN_POWER_S;
    else fix = 0;
    if(errors < 0) fix = fix * -1;
    //System.out.printf("CH = %.3f", currentHeading);
    if (Math.abs(fix) > 0){
      System.out.printf("   errors = %.3f", errors);
      System.out.printf("   fix = %.3f %n", fix);
    }
  }


}
