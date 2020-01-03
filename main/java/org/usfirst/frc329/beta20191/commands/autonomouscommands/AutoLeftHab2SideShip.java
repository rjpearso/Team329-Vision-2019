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

public class AutoLeftHab2SideShip extends CommandGroup {

  public AutoLeftHab2SideShip() {
    requires(Robot.sDrive);
    addSequential(new TimerStarting());
    addSequential(new pneuOpenClosing());
    addSequential(new Delaying(.2));
    addSequential(new ZeroYawing());
    addSequential(new SlowStraightDistanceDriving(36, 0));  // Get to Hab1
    addSequential(new TimerElapsing());
    addSequential(new SlowStraightDistanceDriving(48, 0));
    addSequential(new StraightDistanceDriving(147, -5));
    addSequential(new TimerElapsing());
    addSequential(new PIDTurning(90));
    addSequential(new TimerElapsing());
    addSequential(new Delaying(.25));
    addSequential(new PiTurning());
    addSequential(new TimerElapsing());
    addSequential(new Delaying(.25));
    addSequential(new PiStraightDistanceDriving());
    addSequential(new TimerElapsing());
    addSequential(new AutoChecking());
    addSequential(new StraightDistanceDriving(2.5));
    //addSequential(new Delaying(2));
    addSequential(new pneuOpenClosing());
    addSequential(new Delaying(.5));
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
