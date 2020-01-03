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

public class FrontLegRetracting extends Command {
  Timer t;  //, f;
  private static final double MAX_RETRACT_TIME = 2.25;
 // private static final double EXTRA_RETRACT_TIME = .25;
  //private static boolean done = false;
  public FrontLegRetracting() {
  }

  protected void initialize() {
    System.out.println("Front Leg Retracting init");
    t = new Timer();
    t.start();
    Robot.sClimb.setFrontClimbCoast();
    Robot.sClimb.retractFrontLeg();
  }

  protected void execute() {
    if (t.get() > MAX_RETRACT_TIME)
      Robot.sClimb.stopFrontLeg();
  }

  protected boolean isFinished() {
    boolean done;
    done = Robot.sClimb.frontLegHighEnough();
    return done;
  }

  protected void end() {
    System.out.println("Front Leg Retracting end");
    Robot.sClimb.stopFrontLeg();
    //Robot.sClimb.setFrontClimbBrake();
    t.stop();
  }

  protected void interrupted() {
    end();
  }
}
