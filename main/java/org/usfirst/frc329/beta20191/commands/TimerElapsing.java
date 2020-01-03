/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc329.beta20191.commands;
import org.usfirst.frc329.beta20191.Robot;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Add your docs here.
 */
public class TimerElapsing extends InstantCommand {

  public TimerElapsing() {
      super();
  }

  protected void initialize() {
    System.out.println("Time elapsed : " + Robot.commandTimer.get());
  }

}
