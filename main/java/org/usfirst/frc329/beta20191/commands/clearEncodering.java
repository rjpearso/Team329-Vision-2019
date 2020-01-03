/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc329.beta20191.commands;
import org.usfirst.frc329.beta20191.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class clearEncodering extends Command {
  public clearEncodering() {
  }

  protected void initialize() {
    Robot.sDrive.clearDriveEnc();
  }

  protected void execute() {

  }

  protected boolean isFinished() {
    return true;
  }

  protected void end() {
  }

  protected void interrupted() {
    end();
  }
}
