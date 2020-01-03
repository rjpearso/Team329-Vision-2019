package org.usfirst.frc329.beta20191.commands.autonomouscommands;
import org.usfirst.frc329.beta20191.Robot;
import org.usfirst.frc329.beta20191.commands.SlowStraightDistanceDriving;
import org.usfirst.frc329.beta20191.commands.TimerEnding;
import org.usfirst.frc329.beta20191.commands.TimerStarting;
import org.usfirst.frc329.beta20191.commands.ZeroYawing;

import edu.wpi.first.wpilibj.command.CommandGroup;
public class AutoHab1Moving extends CommandGroup {

  public AutoHab1Moving() {
    requires(Robot.sDrive);
    addSequential(new TimerStarting());
    addSequential(new ZeroYawing());
    addSequential(new SlowStraightDistanceDriving(60, 0)); // Get clear of Hab1
    addSequential(new TimerEnding());
  }
}
