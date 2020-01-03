/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc329.beta20191.commands;

import org.usfirst.frc329.beta20191.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Climbing extends CommandGroup {
 

  public Climbing() {
    requires(Robot.sClimb);
    addSequential(new TimerStarting());
    addSequential(new preClimbing());
    //addSequential(new AutoChecking());
    addSequential(new TimerElapsing());
    addSequential(new LevelClimbing());
    //addSequential(new AutoChecking());
    addSequential(new TimerElapsing());
    addSequential(new ClimbDriving());
    //addSequential(new AutoChecking());
    addSequential(new TimerElapsing());
    addSequential(new FrontLegRetracting());
    addSequential(new ExtraFrontLegRetracting());
    //addSequential(new AutoChecking());
    addSequential(new TimerElapsing());
    addSequential(new ClimbBackDriving());
    //addSequential(new AutoChecking());
    addSequential(new TimerElapsing());
    addSequential(new BackLegRetracting());
    //addSequential(new AutoChecking());
    addSequential(new TimerElapsing());
    addSequential(new SlowStraightDistanceDriving(6));
    addSequential(new TimerEnding());
    addSequential(new Delaying(2000));  // Leave time to turn off button
  }
}
