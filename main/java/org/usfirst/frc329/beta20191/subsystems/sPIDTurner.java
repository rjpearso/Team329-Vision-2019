/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc329.beta20191.subsystems;

import org.usfirst.frc329.beta20191.Robot;
import edu.wpi.first.wpilibj.command.PIDSubsystem;


public class sPIDTurner extends PIDSubsystem {
  private static double counter = 0;
  
  public sPIDTurner() {
    super("sPIDTurn", 0.15, 0, 0, 0);  // Need feed forward term to maintain position??
    //System.out.println("sPIDTurner Start");
    //setSetpoint(START_LOC); 0
    getPIDController().setAbsoluteTolerance(3);
    getPIDController().reset();
    getPIDController().setInputRange(-180, 180);
    getPIDController().setContinuous(true);
    getPIDController().setOutputRange(-.60329, .60329);// 0, .6 -.60329, .60329
    getPIDController().disable();
  }

  public void initDefaultCommand() {
  }

  protected double returnPIDInput() {
    double retVal = Robot.sDrive.getYaw();
    if ((counter % 10) == 0)
      System.out.println("PIDIn = " + retVal);
    return retVal;
  }

  protected void usePIDOutput(double output) {
    if ((counter++ % 10) == 0)
        System.out.println("PIDOut = " + output);
    Robot.sDrive.jSTankdrive(-output, output);
  }
}
