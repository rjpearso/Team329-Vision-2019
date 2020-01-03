/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc329.beta20191.commands;

import org.usfirst.frc329.beta20191.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ZeroYawing extends Command {
  private double CLOSE_ENOUGH = .25;
  private int count = 0;
  public ZeroYawing() {
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    count = 0;
    Robot.sDrive.resetYaw();;
    System.out.println("zeroYawing!");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    count++;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Math.abs(Robot.sDrive.getYaw()) < CLOSE_ENOUGH;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    System.out.println("COUNT IS " + count);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
