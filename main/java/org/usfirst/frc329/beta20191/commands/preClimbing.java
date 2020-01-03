package org.usfirst.frc329.beta20191.commands;

import org.usfirst.frc329.beta20191.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class preClimbing extends Command {
  private static final double PRECLIMBTIME = 0.65;  // Wait until legs touch floor.
  Timer t;
  public preClimbing() {
    requires(Robot.sClimb);
  }

  protected void initialize() {
    t = new Timer();
    t.start();
    Robot.climbStartPitch = Robot.sDrive.getPitch();
    Robot.sClimb.setPreclimberSpeed();
  }


  protected void execute() {
  }

  protected boolean isFinished() {
    return (t.get() > PRECLIMBTIME);
  }

  protected void end() {
    double pow = Robot.sClimb.getStaySpeed();
    Robot.sClimb.setClimberSpeed(pow, pow);
    System.out.println("preClimbing DONE");
    t.stop();
  }

  protected void interrupted() {
  }
}
