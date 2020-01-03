/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc329.beta20191.commands;

import org.usfirst.frc329.beta20191.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CheckAngVel extends Command {
  public CheckAngVel() {
    requires(Robot.sDrive);
  }

  protected void initialize() {

    Robot.sDrive.regularInputs();
  }

  protected void execute() {
    Robot.sDrive.jSTankdrive(-.75, .75);
    System.out.println("Ang Vel = " + Robot.sDrive.ahrs.getRate());
    SmartDashboard.putNumber("Angular Vel", Robot.sDrive.ahrs.getRate());
  }

  protected boolean isFinished() {
    return false;
  }

  protected void end() {
    Robot.sDrive.squareInputs();
  }

  protected void interrupted() {
    end();
  }
}
