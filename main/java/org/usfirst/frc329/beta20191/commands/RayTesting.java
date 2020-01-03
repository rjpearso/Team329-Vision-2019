/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc329.beta20191.commands;

import org.usfirst.frc329.beta20191.Robot;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class RayTesting extends CommandGroup {
  /**
   * Add your docs here.
   */
  public RayTesting() {
    requires(Robot.sDrive);
    addSequential(new TimerStarting());
    addSequential(new ZeroYawing());
    addSequential(new TimerElapsing());
    addSequential(new PIDStraightDriving(-230)); //Was -275, 0
    addSequential(new PIDStraightDriving(-45)); //Was -275, 0
    //addSequential(new Delaying(2));
    addSequential(new TimerElapsing());
    addSequential(new PIDTurning(28));
    addSequential(new TimerElapsing());
    addSequential(new PiAssisting());
    addSequential(new TimerElapsing());
    addSequential(new Delaying(.75));
    addSequential(new TimerElapsing());
    addSequential(new PIDStraightDriving(-50));
    addSequential(new TimerElapsing());
    addSequential(new PIDTurning(0));
    addSequential(new TimerElapsing());
    addSequential(new PIDStraightDriving(230));
    addSequential(new TimerElapsing());
    addSequential(new PiAssisting());
    addSequential(new TimerElapsing());
    addSequential(new TimerEnding());

  }
}
