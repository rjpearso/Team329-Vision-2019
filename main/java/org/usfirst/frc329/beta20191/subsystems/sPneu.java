package org.usfirst.frc329.beta20191.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class sPneu extends Subsystem {
  public static Compressor compressor;
  private static DoubleSolenoid hiPress, loPress;  // hiPress is Beak, loPress is Floor Pickup
  private static final DoubleSolenoid.Value OPEN  = Value.kReverse;
  private static final DoubleSolenoid.Value CLOSE = Value.kForward; // backwards? close = value.kforward? 
  private static WPI_VictorSPX cargoMotor;
  private static final double CARGOSPEED = 0.8;

  public sPneu() {
    // System.out.println("sPneu Start");
    compressor= new Compressor();
    compressor.setClosedLoopControl(true);

    hiPress = new DoubleSolenoid(0, 1);
    hiPress.set(OPEN); 

    loPress = new DoubleSolenoid(2, 3);
    loPress.set(OPEN);  

    cargoMotor = new WPI_VictorSPX(0);
    cargoMotor.setNeutralMode(NeutralMode.Brake);  // IF we want brake.
  }

  public void initDefaultCommand() {
  }

  public void PneuOn() {
    compressor.setClosedLoopControl(true);
  }

  public void PneuOff() {
    compressor.setClosedLoopControl(false);
  }

  public void grabberClose() {
    hiPress.set(CLOSE);
    loPress.set(CLOSE); // Removed for Floor Pickup
  }

  public void grabberOpen() {
    hiPress.set(OPEN);
    loPress.set(OPEN); // Removed for Floor Pickup
  }

  public boolean toggleBeak() {
    if(hiPress.get() == OPEN){
      grabberClose();
      return true;
    }
    else{
      grabberOpen();
      return false;
    }
  }

  public void floorPickupDown() {  // For Floor Pickup
    loPress.set(CLOSE);
    cargoMotor.set(CARGOSPEED);
  }

  public void floorPickupUp() {  // For Floor Pickup
    loPress.set(OPEN);
    cargoMotor.set(0);
  }
}
