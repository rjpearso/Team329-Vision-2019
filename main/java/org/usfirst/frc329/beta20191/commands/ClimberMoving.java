/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc329.beta20191.commands;

import org.usfirst.frc329.beta20191.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ClimberMoving extends Command {
  private static final double MAX = .8;
  //private static final double DOWN_SLOW = .14;
  
  public ClimberMoving() {
     requires(Robot.sClimb);
  }

  protected void initialize() {
  }

  protected void execute() {
    double pow = Robot.oi.joystickRight.getRawAxis(3) * MAX; 
    if(pow > MAX) pow = MAX;
    if(pow < -MAX) pow = -MAX;
    if(Math.abs(pow) < .1) pow = 0;
    pow = -pow;
    Robot.sClimb.climbMove(pow);
    System.out.println("climber moving power = " + pow );
    //Robot.sClimb.setClimberSpeed(DOWN_SLOW, DOWN_SLOW);  //uncomment when done
    //Robot.sClimb.setClimberSpeed(pow, pow);
  }

  protected boolean isFinished() {
    return false;
  }

  protected void end() {
    double pow = 0;
    Robot.sClimb.climbMove(0);
    //Robot.sClimb.setClimberSpeed(0, 0); //uncomment when done
    System.out.println("ClimberMoving DONE pow = " + pow);
  }

  protected void interrupted() {
    end();
  }
}
