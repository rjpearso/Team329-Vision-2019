/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc329.beta20191.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc329.beta20191.Robot;

public class PiTurning extends Command {
  private static double targetAngle, currAngle, turnAng;
  private static boolean angleFlag, tryAgain, reallyGood;
  private static final double TOO_FAR = 52;
  private static final double CLOSE_ENOUGH = 4.5; // 1.8 //3.0
  private static final double BAD_ANGLE = -20;///999
  private static double error; 
  private static double piIsAlive = -1;

  
  public PiTurning() {
    requires(Robot.sDrive);
    requires(Robot.sPi);
	requires(Robot.sDrive);
    requires(Robot.sPIDTurner);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.sDrive.regularInputs();
    angleFlag = false; reallyGood = false;
    resetTarget();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(turnAng <= BAD_ANGLE || turnAng >= Math.abs(BAD_ANGLE) || Robot.sPi.piRunning() == false){ // cant see the target    || turnAng >= Math.abs(BAD_ANGLE)
      System.out.println("THE ANGLE IS BAD, CANNOT SEE TARGETS");
      angleFlag = true;
      Robot.sPi.setSeeAngle(false);
    }
    else {
      Robot.sPi.setSeeAngle(true);
      turningToAngle();
      if(angleFlag == true && reallyGood == false) {
        if(tryAgain == false) reallyGood = true; else reallyGood = false;
        resetTarget();
        angleFlag = false;
        System.out.println("new target = " + targetAngle + " tryAgain = " + tryAgain + " RG = " + reallyGood);
      }  
    }
  }

  // Make this return turnAng when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return angleFlag;
  }

  // Called once after isFinished returns turnAng
  @Override
  protected void end() {
    Robot.sDrive.jSTankdrive(0, 0);
    Robot.sDrive.squareInputs();
    System.out.println("piTurning ending yaw = " + currAngle + " error = " + error);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }

  private void turningToAngle() {
    double pow = 0;
    currAngle = Robot.sDrive.ahrs.getYaw();
    error = currAngle - targetAngle; //Math.abs(currAngle - targetAngle);
    if(Math.abs(error) > TOO_FAR) pow = MAX_POWER;
    else pow = MIN_POWER;
    if (Math.abs(error) <= CLOSE_ENOUGH){
      angleFlag = true;
      return;
    }
    else angleFlag = false;
    if(Math.abs(error) <= 180){
      if (error > 0){ 
        Robot.sDrive.jSTankdrive(pow, -pow); //ccw
      }
      else if (error < 0){
        Robot.sDrive.jSTankdrive(-pow, pow); // cw 
      }
    }
    else{ //Math.abs(error) > 180
      if (error > 0){ 
        Robot.sDrive.jSTankdrive(-pow, pow); //cw
    }
      else if (error < 0){
        Robot.sDrive.jSTankdrive(pow, -pow); //ccw
      }
   }
    if(angleFlag == false && reallyGood == true){
      reallyGood = false;
      System.out.println("MISTAKE reallyGood = false");
    } 

    System.out.print("Yaw is = " + currAngle + " error is = " + error);
    System.out.println(" pow = "+ pow);
  }

  public void resetTarget() {
    double pia;
    pia = SmartDashboard.getNumber("Turn Angle", turnAng);
    if(pia != piIsAlive) {
    turnAng = pia;
    targetAngle = turnAng + Robot.sDrive.getYaw();
    tryAgain = SmartDashboard.getBoolean("Try Again", tryAgain);
    }
    else{
      turnAng = BAD_ANGLE*2;
      System.out.println("Pi is not alive");
    }
    System.out.println("turnAng = " + turnAng + " targetAngle = " + targetAngle + " tryAgain = " + tryAgain);
  }

  public class Drive(){
    int P, I, D = 1;
    int integral, previous_error, setpoint = 0;
    Gyro gyro;
    DifferentialDrive robotDrive;


    public Drive(Gyro gyro){
        this.gyro = gyro;
    }

    public void setSetpoint(int setpoint)
    {
        this.setpoint = setpoint;
    }

    public void PID(){
        error = currAngle - targetAngle; //Math.abs(currAngle - targetAngle);
        this.integral += (error*.02); // Integral is increased by the error*time (which is .02 seconds using normal IterativeRobot)
        derivative = (error - this.previous_error) / .02;
        this.rcw = P*error + I*this.integral + D*derivative;
    }

    public void execute()
    {
        PID();
        robotDrive.arcadeDrive(0, rcw);
    }
}
}
