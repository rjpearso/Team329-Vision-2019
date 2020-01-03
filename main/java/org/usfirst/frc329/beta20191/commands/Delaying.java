/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc329.beta20191.commands;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class Delaying extends Command{
    private Timer t = new Timer();
	private double delay = 0;

   /* public Delaying() {
    	delay = Robot.endTime;
    } */
    
    public Delaying(double t) {
    	delay=t;	
    }

    protected void initialize() {
    	t.start();
    	System.out.println("Delaying = " + delay);
    }

    protected void execute() {
    	//System.out.println("Count Down " + t.get());
    }

    protected boolean isFinished() {
        return t.get()>=delay;
    }

    protected void end() {
    	t.reset();
    	System.out.println("Delaying Done");
    }

    protected void interrupted() {
    	//System.out.println("Delaying Interrupted");
    	end();
    }
}
