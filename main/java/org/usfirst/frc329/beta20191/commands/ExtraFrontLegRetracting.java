/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc329.beta20191.commands;
import org.usfirst.frc329.beta20191.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class ExtraFrontLegRetracting extends Command {
  private static boolean done = false;
  Timer t;
  private static final double EXTRA_RETRACT_TIME = .50;
  public ExtraFrontLegRetracting() {
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    t = new Timer();
    t.start();
    Robot.sClimb.retractFrontLeg();
  }

  protected void execute() {
    if (t.get() > EXTRA_RETRACT_TIME)
      done = true;
  }

  protected boolean isFinished() {
    return done;
  }

  protected void end() {
    Robot.sClimb.stopFrontLeg();
    Robot.sClimb.setFrontClimbBrake();
    t.stop();
  }

  protected void interrupted() {
    end();
  }
}
