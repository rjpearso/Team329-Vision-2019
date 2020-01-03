package org.usfirst.frc329.beta20191.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import org.usfirst.frc329.beta20191.Robot;
import org.usfirst.frc329.beta20191.commands.PIDHeightKeeping;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class sPIDElevator extends PIDSubsystem {
  private static final double START_LOC = 0;
  //private static double counter = 0;
  private static Encoder elEnc;
  private static final double COUNTS_PER_INCH_ELEV = 28.165; 
  private static DigitalInput bottomSwitch;
  private static WPI_VictorSPX el1, el2;
  private static final double BEAK_OFFSET = 19;

  public static double targetHeight = 0;
  private final double PNEU_ADJUST = 3;
  private static final double NEAR_BOTTOM = 2;
  private static final double NEAR_TOP = 88 - BEAK_OFFSET;
  public final double GROUND = 19;
  public final double BOTTOM_HATCH = 20.25 + PNEU_ADJUST;   //GROUND + PNEU_ADJUST;
  public final double BOTTOM_BALL = 28.5;
  public final double MIDDLE_HATCH = 48 + PNEU_ADJUST;
  public final double MIDDLE_BALL = 56.5; 
  public final double TOP_BALL = 84.5; 
  public final double TOP_HATCH = 76 + PNEU_ADJUST;
  public final double BALL_PICKUP = 44.5; 
  public final double CARGO_BALL = 44.75;//40.75
  private boolean elevOkay = true;
  private boolean hatchHeight = false;
  private double heightAdjOffSet;

  public static Timer elevTimer;


  public sPIDElevator() {
    // Intert a subsystem name and PID values here
    super("sPIDElevator", 0.22, 0, 0.05, 0);  
    SmartDashboard.putBoolean("Reset Elevator Encoder", false);
    // System.out.println("sPIDElev Start");
    el1 = new WPI_VictorSPX(9);
    el2 = new WPI_VictorSPX(11);
    elEnc = new Encoder(4,5);
    bottomSwitch = new DigitalInput(9);
    elevTimer = new Timer();

    setSetpoint(START_LOC);
    getPIDController().setAbsoluteTolerance(1);
    getPIDController().reset();
    getPIDController().disable();
    getPIDController().setContinuous(false);
    getPIDController().setInputRange(-1, 88);
    getPIDController().setOutputRange(-0.10, 0.65); // (-0.05, 0.65)

    elEnc.setDistancePerPulse(1 / COUNTS_PER_INCH_ELEV);  // actually measure COUNTS PER INCH ELEV ASAP
    elEnc.setSamplesToAverage(20);  // change?
    elEnc.reset();
    elEnc.setReverseDirection(false);
    heightAdjOffSet = 0;
  
  }

  public void initDefaultCommand() {
    setDefaultCommand(new PIDHeightKeeping());
  }

  protected double returnPIDInput() {
    double retVal = elEnc.getDistance();  //getting REAL height (w/o beak)
    //if ((counter % 3) == 0)
      //System.out.println("PIDElevIn = " + retVal);
    return retVal;
  }

  protected void usePIDOutput(double output) {
    if(((getRawHeight() <= NEAR_BOTTOM) || atBottom()) && (targetHeight <= NEAR_BOTTOM)){
      output = 0.05; // 0
      if(atBottom()) clearElevEnc();
    }
    if(!elevSafe()){
      output = 0;
    }
    //if ((counter++ % 3) == 0)
        //System.out.println("PIDElevOut = " + output);
    setElevSpeed(output);
  }

  public void hatchCloseCheck(){
    if(hatchHeight)
    {
      double th = getPIDController().getSetpoint();
      hatchHeight = false;
      getPIDController().setSetpoint(th - PNEU_ADJUST);
    }
  }

  public void setHatchHeight(){
    hatchHeight = true;
  }


  public double getRawHeight(){
    return elEnc.getDistance();
  }

  public double getHeight(){
    return elEnc.getDistance() + BEAK_OFFSET;
  }

  public boolean atBottom(){
    return !bottomSwitch.get();
  }

  public void setElevSpeed(double s){
    el1.set(s);
    el2.set(s);
  }

  public void clearElevEnc() {
    elEnc.reset();
  }

  public void setTargetHeight(double h){
    System.out.println("h = " + h);
    elevTimer.reset();
    elevTimer.start();
    targetHeight = h - BEAK_OFFSET;
    if(targetHeight > NEAR_TOP) targetHeight = NEAR_TOP;
    if(targetHeight < NEAR_BOTTOM) targetHeight = NEAR_BOTTOM;
    getPIDController().setSetpoint(targetHeight);
    return;
  }

  public void setHeightAdj(double h){
    heightAdjOffSet = targetHeight - h;
    if(heightAdjOffSet > NEAR_TOP) heightAdjOffSet = NEAR_TOP;
    if(heightAdjOffSet < NEAR_BOTTOM) heightAdjOffSet = NEAR_BOTTOM;
    Robot.sPIDEl.setSetpoint(heightAdjOffSet);
  }

  public void stopElevMotor(){
    setElevSpeed(0);
  }

  public double getRate(){
    return elEnc.getRate();
  }

  public boolean elevSafe(){
    if((Math.abs(getRate()) < 1) &&
      (Math.abs(getRawHeight()) < 1) &&
      !atBottom())
        elevOkay = false;
    else
      elevOkay = true;
    return elevOkay;
  }

  public boolean isElevOkay(){
    return elevOkay;
  }

  public double getTime(){
    elevTimer.stop();
    return elevTimer.get();
  }

}
