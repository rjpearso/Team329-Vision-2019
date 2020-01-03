/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc329.beta20191.commands.HeightSetting;
import org.usfirst.frc329.beta20191.Robot;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Add your docs here.
 */
public class cargoStationBalling extends InstantCommand {
  public cargoStationBalling() {
    super();
  }
  // Called once when the command executes
  @Override
  protected void initialize() {
    Robot.sPIDEl.setTargetHeight(Robot.sPIDEl.CARGO_BALL); // NOT FUNCTIONAL  
  }

}
