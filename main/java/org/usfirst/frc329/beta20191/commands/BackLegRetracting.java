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

public class BackLegRetracting extends Command {
  Timer t;
  private static final double MAX_RETRACT_TIME = 2;
  public BackLegRetracting() {
  }

  protected void initialize() {
    System.out.println("Back Leg Retracting init");
    t = new Timer();
    t.start();
    Robot.sClimb.setBackClimbCoast();
    Robot.sClimb.retractBackLeg();
  }
  protected void execute() {
    if (t.get() > MAX_RETRACT_TIME)
      Robot.sClimb.stopBackClimbLeg();
  }

  protected boolean isFinished() {
    return Robot.sClimb.backLegHighEnough();
  }

  protected void end() {
    Robot.sClimb.stopBackLeg();
    Robot.sClimb.setBackClimbBrake();
    System.out.println("Back Leg Retracting end");
    t.stop();
  }

  protected void interrupted() {
    end();
  }
}
