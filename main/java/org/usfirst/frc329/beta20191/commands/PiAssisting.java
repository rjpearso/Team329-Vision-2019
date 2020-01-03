/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc329.beta20191.commands;

import org.usfirst.frc329.beta20191.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class PiAssisting extends CommandGroup {
  /**
   * Add your docs here.
   */
  public PiAssisting() {
    requires(Robot.sDrive);
    //addSequential(new TimerStarting());
    addSequential(new PiTurning());
    addSequential(new TimerElapsing()); 
    //addSequential(new PiStraightDistanceDriving());
    //addSequential(new TimerEnding()); 
  }
}
