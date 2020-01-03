/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc329.beta20191.commands;

import org.usfirst.frc329.beta20191.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Command;


public class PIDTurning extends Command {
  private int counter;
  private static final int DONE_COUNT = 4;
  public double targetAngle;

 
  public PIDTurning(double angle) {
    requires(Robot.sDrive);
    requires(Robot.sPIDTurner);
    targetAngle = angle;
    //Robot.sPIDTurner.setSetpoint(angle);
  }

  protected void initialize() {
    Robot.sPIDTurner.setSetpoint(targetAngle); 
    Robot.sPIDTurner.getPIDController().setP(.08);//.125
    Robot.sPIDTurner.getPIDController().setI(0);
    Robot.sPIDTurner.getPIDController().setD(.12);//.11 at .6 power 
    Robot.sPIDTurner.getPIDController().setF(0);
   // Robot.sPIDTurner.getPIDController().setP(SmartDashboard.getNumber("P",temp));//.125
   // Robot.sPIDTurner.getPIDController().setI(SmartDashboard.getNumber("I",temp));
   // Robot.sPIDTurner.getPIDController().setD(SmartDashboard.getNumber("D",temp));//.11 at .6 power 
   // Robot.sPIDTurner.getPIDController().setF(SmartDashboard.getNumber("F",temp));
    Robot.sPIDTurner.enable();
    //Robot.sDrive.resetYaw();
    Robot.sDrive.regularInputs();
    System.out.println("PIDTURN Start");
    System.out.println(Robot.sPIDTurner.getPIDController().getP() + " " + Robot.sPIDTurner.getPIDController().getI() + " " + Robot.sPIDTurner.getPIDController().getD() + " " + Robot.sPIDTurner.getPIDController().getF());
    System.out.println("SetPoint = " + targetAngle + " Yaw = " + Robot.sDrive.getYaw());
  }

  protected void execute() {
    double error;
    //if ((counter++ % 50) == 0)
      //System.out.println("Setpoint = " + Robot.sPIDTurner.getSetpoint());
    error = Robot.sPIDTurner.getSetpoint() - Robot.sPIDTurner.getPosition();
    SmartDashboard.putNumber("Ang Err", error);
    System.out.println("error = " + error);
  }


  protected boolean isFinished() {
    //return Robot.sPIDTurner.onTarget();
    if(Robot.sPIDTurner.onTarget()) counter++;
    else counter = 0;
    return (counter == DONE_COUNT);
    //return false;
  }

  protected void end() {
    Robot.sPIDTurner.disable();
    Robot.sDrive.jSTankdrive(0, 0);
    Robot.sDrive.squareInputs();
    System.out.println("PIDTURN End = " + counter + "     " + Robot.sPIDTurner.getPosition());
  }

  protected void interrupted() {
    end();
  }
}
