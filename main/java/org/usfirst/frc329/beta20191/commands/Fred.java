/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc329.beta20191.commands;
import org.usfirst.frc329.beta20191.Robot;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class Fred extends CommandGroup {
  /**
   * Add your docs here.
   */
  public Fred() {
    requires(Robot.sDrive);
    addSequential(new TimerStarting());
    //addSequential(new SlowStraightDistanceDriving(36, 0));
    addSequential(new TimerElapsing());
    addSequential(new StraightDistanceDriving(195, -5));
    addSequential(new TimerElapsing());
    addSequential(new PIDTurning(90));
    addSequential(new TimerElapsing()); 
    addSequential(new Delaying(1));
    addSequential(new PiTurning());
    addSequential(new TimerElapsing());
    addSequential(new Delaying(1));
    addSequential(new PiStraightDistanceDriving());
    addSequential(new TimerElapsing());
    addSequential(new Delaying(2));
    addSequential(new TimerElapsing());
    //Go back
    addSequential(new StraightDistanceDriving(-50, 90));
    addSequential(new TimerElapsing());
    addSequential(new PIDTurning(-177.329));
    addSequential(new TimerElapsing());
    addSequential(new StraightDistanceDriving(195, -177.329));
    addSequential(new TimerEnding());

  }
}
