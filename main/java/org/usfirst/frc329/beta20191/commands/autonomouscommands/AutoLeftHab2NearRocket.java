/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc329.beta20191.commands.autonomouscommands;

import org.usfirst.frc329.beta20191.Robot;
import org.usfirst.frc329.beta20191.commands.AutoChecking;
import org.usfirst.frc329.beta20191.commands.Delaying;
import org.usfirst.frc329.beta20191.commands.PiStraightDistanceDriving;
import org.usfirst.frc329.beta20191.commands.PiTurning;
import org.usfirst.frc329.beta20191.commands.SlowStraightDistanceDriving;
import org.usfirst.frc329.beta20191.commands.StraightDistanceDriving;
import org.usfirst.frc329.beta20191.commands.TimerElapsing;
import org.usfirst.frc329.beta20191.commands.TimerEnding;
import org.usfirst.frc329.beta20191.commands.TimerStarting;
import org.usfirst.frc329.beta20191.commands.PIDTurning;
import org.usfirst.frc329.beta20191.commands.ZeroYawing;
import org.usfirst.frc329.beta20191.commands.pneuOpenClosing;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoLeftHab2NearRocket extends CommandGroup {
  /**
   * Add your docs here.
   */
  public AutoLeftHab2NearRocket() {
   requires(Robot.sDrive);
   addSequential(new TimerStarting());
   addSequential(new pneuOpenClosing());
   addSequential(new ZeroYawing());
   addSequential(new SlowStraightDistanceDriving(36, 0));  // Get to Hab1
   addSequential(new TimerElapsing());
   addSequential(new StraightDistanceDriving(49, 0)); // Get clear of Hab1 maybe slow?
   addSequential(new TimerElapsing());
   //addSequential(new Delaying(.25));
   addSequential(new PIDTurning(-55));
   addSequential(new TimerElapsing());
   addSequential(new StraightDistanceDriving(45));
   addSequential(new TimerElapsing());
   addSequential(new StraightDistanceDriving(40, -28));// maybe slow?
   addSequential(new TimerElapsing());
   addSequential(new Delaying(.25));
   addSequential(new PiTurning());
   addSequential(new TimerElapsing());
   addSequential(new Delaying(.25));
   addSequential(new PiStraightDistanceDriving());
   addSequential(new AutoChecking());
   addSequential(new StraightDistanceDriving(2.5));
   //addSequential(new Delaying(2));
   addSequential(new pneuOpenClosing());
   addSequential(new Delaying(.5));
   addSequential(new TimerElapsing());
   // GO BACK
   addSequential(new StraightDistanceDriving(-30));
   addSequential(new TimerElapsing());
   addSequential(new PIDTurning(179));
   addSequential(new TimerElapsing());
   addSequential(new StraightDistanceDriving(100, 179));
   addSequential(new TimerEnding());
   
  }
}
