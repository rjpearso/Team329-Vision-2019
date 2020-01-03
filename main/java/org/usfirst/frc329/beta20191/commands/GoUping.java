/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc329.beta20191.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class GoUping extends CommandGroup {
  /**
   * Add your docs here.
   */
  public GoUping() {
    addSequential(new preClimbing());
    addSequential(new LevelClimbing());
  }
}
