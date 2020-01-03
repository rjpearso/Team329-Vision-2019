package org.usfirst.frc329.beta20191.commands;

import org.usfirst.frc329.beta20191.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PIDHeightKeeping extends Command {
  private double counter = 0;

  public PIDHeightKeeping() {
    requires(Robot.sPIDEl);
  }

  protected void initialize() {
    Robot.sPIDEl.setTargetHeight(Robot.sPIDEl.GROUND);
    //Robot.sPIDEl.getPIDController().setP(SmartDashboard.getNumber("P",temp));
    //Robot.sPIDEl.getPIDController().setI(SmartDashboard.getNumber("I",temp));
    //Robot.sPIDEl.getPIDController().setD(SmartDashboard.getNumber("D",temp));
    //Robot.sPIDEl.getPIDController().setF(SmartDashboard.getNumber("F",temp));
    Robot.sPIDEl.clearElevEnc();
    Robot.sPIDEl.enable();
    System.out.println("PIDHK Start");
   }

  protected void execute() {
    double error;
    //if ((counter++ % 50) == 0)
      //System.out.println("Setpoint = " + Robot.sPIDEl.getSetpoint());
    if ((counter++ % 12) == 0)  // Every 1/4 second
    {
      error = Robot.sPIDEl.getSetpoint() - Robot.sPIDEl.getPosition();
      SmartDashboard.putNumber("El Err", error);
      SmartDashboard.putBoolean("elevOkay", Robot.sPIDEl.isElevOkay());
    }
  }

  protected boolean isFinished() {
    //double a;
    /*if(Robot.sPIDEl.onTarget()){
      a = Robot.sPIDEl.getTime();
      if(a > .5)
        System.out.println("Elev time elapsed = " + a);
    } */
    boolean retval = false;
    //retval = Robot.sPIDEl.onTarget();
    return retval;
  }

  protected void end() {
    Robot.sPIDEl.setTargetHeight(Robot.sPIDEl.GROUND);
    Robot.sPIDEl.disable();
    Robot.sPIDEl.stopElevMotor();
    System.out.println("PIDHK End");
  }

  protected void interrupted() {
    end();
  }
}
