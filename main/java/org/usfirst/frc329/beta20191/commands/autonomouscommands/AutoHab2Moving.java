package org.usfirst.frc329.beta20191.commands.autonomouscommands;
import org.usfirst.frc329.beta20191.Robot;
import org.usfirst.frc329.beta20191.commands.Delaying;
import org.usfirst.frc329.beta20191.commands.SlowStraightDistanceDriving;
import org.usfirst.frc329.beta20191.commands.TimerElapsing;
import org.usfirst.frc329.beta20191.commands.TimerEnding;
import org.usfirst.frc329.beta20191.commands.TimerStarting;
import org.usfirst.frc329.beta20191.commands.ZeroYawing;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoHab2Moving extends CommandGroup {
  /**
   * Add your docs here.
   */
  public AutoHab2Moving() {
   requires(Robot.sDrive);
   addSequential(new TimerStarting());
   addSequential(new ZeroYawing());
   addSequential(new SlowStraightDistanceDriving(36, 0));  // Get to Hab1
   addSequential(new TimerElapsing());
   addSequential(new Delaying(0.5));
   addSequential(new SlowStraightDistanceDriving(60, 0)); // Get clear of Hab1
   addSequential(new TimerEnding());
  }
}
