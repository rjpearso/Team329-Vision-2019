/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc329.beta20191.commands;

import org.usfirst.frc329.beta20191.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ElevatorMoving extends Command {
  
  public ElevatorMoving() {
  }

  protected void initialize() {
  }

  protected void execute() {
    double pow = Robot.oi.controlPanel.getX(); 
    double offSet;
    offSet = 12 * pow + 6;
    Robot.sPIDEl.setHeightAdj(offSet);
    //System.out.println("offSet = " + offSet);
  }

  protected boolean isFinished() {
    return false;
  }

  protected void end() {
    Robot.sPIDEl.setHeightAdj(0);
    System.out.println("ElevMoving DONE");
  }

  protected void interrupted() {
    end();
  }
}
