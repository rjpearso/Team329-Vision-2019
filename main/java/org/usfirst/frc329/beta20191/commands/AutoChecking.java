/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc329.beta20191.commands;

import org.usfirst.frc329.beta20191.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class AutoChecking extends Command {
  private static boolean autoGood = false;

  public AutoChecking() {
  }

  protected void initialize() {
    autoGood = false;
  }

  protected void execute() {
    autoGood = Robot.oi.checkAuto.get();
  }

  protected boolean isFinished() {
    return autoGood;
  }

  protected void end() {
  }

  protected void interrupted() {
    end();
  }
}
