/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc329.beta20191.commands.HeightSetting;
import org.usfirst.frc329.beta20191.Robot;
import edu.wpi.first.wpilibj.command.InstantCommand;


public class bottomBallRocketing extends InstantCommand {
  public bottomBallRocketing() {
    super();
  }
 
  protected void initialize() {
    Robot.sPIDEl.setTargetHeight(Robot.sPIDEl.BOTTOM_BALL); // NOT FUNCTIONAL  
  }

}
