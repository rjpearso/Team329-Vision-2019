/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc329.beta20191.commands;

import org.usfirst.frc329.beta20191.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class NoDriving extends Command {
  public NoDriving() {
    requires(Robot.sDrive);
  }

  protected void initialize() {
    System.out.println("NoDriving");
  }

  protected void execute() {
    Robot.sDrive.jSTankdrive(0, 0);
  }

  protected boolean isFinished() {
    return false;
  }

  protected void end() {
    Robot.sDrive.jSTankdrive(0, 0);
  }

  protected void interrupted() {
    end();
  }
}
