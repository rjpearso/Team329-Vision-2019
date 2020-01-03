/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc329.beta20191.commands;

import org.usfirst.frc329.beta20191.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SafeDowning extends Command {
  private static final double DOWN_SLOW = .14;
  
  public SafeDowning() {
     requires(Robot.sClimb);
  }

  protected void initialize() {
    System.out.println("Safe Downing init");
    Robot.sClimb.setClimberSpeed(DOWN_SLOW, DOWN_SLOW);
  }

  protected void execute() {
  }

  protected boolean isFinished() {
    return false;
  }

  protected void end() {
    double pow = 0;
    Robot.sClimb.setClimberSpeed(0, 0);
    System.out.println("SafeDowning DONE pow = " + pow);
  }

  protected void interrupted() {
    end();
  }
}
