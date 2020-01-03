package org.usfirst.frc329.beta20191.commands;

import org.usfirst.frc329.beta20191.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class PneuFloorPickupping extends Command {
  public PneuFloorPickupping() {
  }

  protected void initialize() {
    Robot.sPneu.floorPickupDown();
  }

  protected void execute() {
  }

  protected boolean isFinished() {
    return false;
  }

  protected void end() {
    Robot.sPneu.floorPickupUp();
  }

  protected void interrupted() {
    end();
  }
}
