package org.usfirst.frc329.beta20191.commands.autonomouscommands;

import org.usfirst.frc329.beta20191.Robot;
import org.usfirst.frc329.beta20191.commands.NoDriving;
import org.usfirst.frc329.beta20191.commands.TimerEnding;
import org.usfirst.frc329.beta20191.commands.TimerStarting;
import org.usfirst.frc329.beta20191.commands.ZeroYawing;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoDoNothing extends CommandGroup {
  /**
   * Add your docs here.
   */
  public AutoDoNothing() {   
    requires(Robot.sDrive);
    addSequential(new TimerStarting());
    addSequential(new ZeroYawing());
    addSequential(new NoDriving());
    addSequential(new TimerEnding());
  }
}
