
package org.usfirst.frc329.beta20191.commands;

import org.usfirst.frc329.beta20191.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class pneuOpenClosing extends Command {
  public pneuOpenClosing() {
  //requires(Robot.sPneu);  // Removed for Floor Pickup
  }

  protected void initialize() {
    if(Robot.sPneu.toggleBeak()){
      Robot.sPIDEl.hatchCloseCheck();
    }
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
