/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc329.beta20191.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class sClimb extends Subsystem {

  private DigitalInput frontEdgeDetector, backEdgeDetector, frontLegHighEnough, backLegHighEnough;
  private DigitalInput highEnough;
  private WPI_VictorSPX frontClimb;
  private WPI_VictorSPX backClimb;
  private WPI_VictorSPX climbDrive;
  private WPI_VictorSPX backLeg;
  // private static final double FRONT_MULT = 1.6;
  private static final double FRONT_OFFSET = -0.2;
  private static final double MAX_DOWN = -.65;  // -.325    1/FRONT_MULT  -  LEVELCLIMBING.MAX_POWER_S
  private static final double STAY = -.15; // -.193 actually stays
  private static final double STAY_BACK = STAY - .1;
  private static final double FL_CLIMB_RETRACT_SPEED = .329; // STAY +.0329
  private static final double BL_RETRACT_SPEED = -.65; //double check
  private static final double BL_CLIMB_RETRACT_SPEED = 0.7;  //0.6
  private static final double ALPHA_BETA_FIX = 1; //-1 if running beta, 1 if running on alpha
  private static final double CLIMB_DRIVE_SPEED = 0.5;

  public sClimb(){
    // System.out.println("sClimb Start");
    highEnough         = new DigitalInput(6);
    frontEdgeDetector  = new DigitalInput(10); 
    backEdgeDetector   = new DigitalInput(11);
    frontLegHighEnough = new DigitalInput(7);
    backLegHighEnough  = new DigitalInput(8);
    frontClimb         = new WPI_VictorSPX(12);
    backClimb          = new WPI_VictorSPX(13);
    backLeg            = new WPI_VictorSPX(10);
    climbDrive         = new WPI_VictorSPX(5);
    
    climbDrive.setNeutralMode(NeutralMode.Brake);
    setBackClimbBrake();
    setFrontClimbBrake();
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
  public boolean frontOverTheEdge(){
    return !frontEdgeDetector.get();
  }

  public boolean backOverTheEdge(){
    return backEdgeDetector.get();
  }

  public boolean frontLegHighEnough(){
    return !frontLegHighEnough.get();
  }

  public boolean backLegHighEnough(){
    return !backLegHighEnough.get();
  }

  public boolean climbHighEnough(){
    return !highEnough.get();
  }

  public double getClimbDriveSpeed(){
    return CLIMB_DRIVE_SPEED*ALPHA_BETA_FIX;
  }

  public void setClimberSpeed(double f, double b){
    double frontspeed; 
    if(Math.abs(f) < .1 || Math.abs(b) < .1) {
      f = b = frontspeed = 0;
    }
    else frontspeed = f + FRONT_OFFSET;
    //frontspeed = f * FRONT_MULT;
    System.out.println("SCS Back = " + b + " Front = " + frontspeed);
    frontClimb.set(frontspeed);
    backClimb.set(b * ALPHA_BETA_FIX);
    //climbDrive.set(s);
  }

  public void setPreclimberSpeed(){
    System.out.println("Set Preclimb Speed");
    frontClimb.set(STAY);
    backClimb.set(STAY_BACK * ALPHA_BETA_FIX);
  }

  public void climbMove(double s){
    climbDrive.set(s);
    //backLeg.set(s); //FOR TESTING 
  }
  
  public void retractFrontLeg(){
    frontClimb.set(FL_CLIMB_RETRACT_SPEED);
  }
  
  public void stopFrontLeg(){
    frontClimb.set(0);
  }

  public void retractBackLeg(){
    backLeg.set(BL_RETRACT_SPEED*ALPHA_BETA_FIX);
    //backLeg.set(BL_RETRACT_SPEED);
    backClimb.set(BL_CLIMB_RETRACT_SPEED*ALPHA_BETA_FIX);
  }
  
  public void stopBackLeg(){
    backLeg.set(0);
    backClimb.set(0);
  }

  public void stopBackClimbLeg(){
    backClimb.set(0);
  }

  public double getClimbSpeed(){
    return MAX_DOWN;
  }
  
  public double getStaySpeed(){
    return STAY;
  }

  public void setBackClimbCoast(){
    backClimb.setNeutralMode(NeutralMode.Coast);
  }

  public void setBackClimbBrake(){
    backClimb.setNeutralMode(NeutralMode.Brake);
  }

  public void setFrontClimbCoast(){
    frontClimb.setNeutralMode(NeutralMode.Coast);
  }

  public void setFrontClimbBrake(){
    frontClimb.setNeutralMode(NeutralMode.Brake);
  }
}
