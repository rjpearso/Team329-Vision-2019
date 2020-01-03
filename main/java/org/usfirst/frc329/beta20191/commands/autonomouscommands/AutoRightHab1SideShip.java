
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

public class AutoRightHab1SideShip extends CommandGroup {
  
  public AutoRightHab1SideShip() {
    requires(Robot.sDrive);
    addSequential(new TimerStarting());
    addSequential(new ZeroYawing());
    addSequential(new pneuOpenClosing());
    addSequential(new SlowStraightDistanceDriving(48));
    addSequential(new StraightDistanceDriving(147, 5));
    addSequential(new TimerElapsing());
    addSequential(new PIDTurning(-90));
    addSequential(new TimerElapsing());
    addSequential(new Delaying(.25));
    addSequential(new PiTurning());
    addSequential(new TimerElapsing());
    addSequential(new Delaying(.25));
    addSequential(new PiStraightDistanceDriving());
    addSequential(new TimerElapsing());
    addSequential(new AutoChecking());
    addSequential(new StraightDistanceDriving(2.5));
    addSequential(new pneuOpenClosing());
    addSequential(new TimerElapsing());
    //Go back
    addSequential(new StraightDistanceDriving(-50, -90));
    addSequential(new TimerElapsing());
    addSequential(new PIDTurning(177.329));
    addSequential(new TimerElapsing());
    addSequential(new StraightDistanceDriving(195, 177.329));
    addSequential(new TimerEnding());
  }
}
