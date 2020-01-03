/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc329.beta20191.commands;

import org.usfirst.frc329.beta20191.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class PIDStraightDriving extends Command {
  private double targetDist;
  private int counter;
  private static final int COUNTER_DONE = 10;

  public PIDStraightDriving(double target) {
    requires(Robot.sDrive);
    requires(Robot.sPIDDrive);
    targetDist = target;
  }

  protected void initialize() {
    Robot.sPIDDrive.setSetpoint(targetDist);
    Robot.sPIDDrive.getPIDController().setP(.1);
    Robot.sPIDDrive.getPIDController().setI(0);
    Robot.sPIDDrive.getPIDController().setD(.15);
    Robot.sPIDDrive.getPIDController().setF(0);
    // Robot.sPIDDrive.getPIDController().setP(SmartDashboard.getNumber("P",temp));
    // Robot.sPIDDrive.getPIDController().setI(SmartDashboard.getNumber("I",temp));
    // Robot.sPIDDrive.getPIDController().setD(SmartDashboard.getNumber("D",temp));
    // Robot.sPIDDrive.getPIDController().setF(SmartDashboard.getNumber("F",temp));
    Robot.sDrive.sDriveencRight.reset();
    Robot.sDrive.sDriveencLeft.reset();
    Robot.sPIDDrive.enable();
    Robot.sDrive.regularInputs();
    System.out.println("PIDDRIVE Start");
    System.out.println(Robot.sPIDTurner.getPIDController().getP() + " " + Robot.sPIDTurner.getPIDController().getI() + " " + Robot.sPIDTurner.getPIDController().getD() + " " + Robot.sPIDTurner.getPIDController().getF());
    System.out.println("SetPoint = " + targetDist + " Yaw = " + Robot.sDrive.getYaw());
    Robot.sPIDDrive.initPIDStraightDriving();
  }

  protected void execute() {
    Robot.sPIDDrive.execPIDStraightDriving();
  }

  protected boolean isFinished() {    
    if(Robot.sPIDDrive.onTarget()) counter++;
    else counter = 0;
    return (counter == COUNTER_DONE);
  }

  protected void end() {
    Robot.sPIDDrive.disable();
    Robot.sDrive.jSTankdrive(0, 0);
    Robot.sDrive.squareInputs();
    System.out.println("PIDDRIVE End = " + Robot.sPIDDrive.getPosition());
  }

  protected void interrupted() {
    end();
  }
}
