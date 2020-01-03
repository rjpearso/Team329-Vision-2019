package org.usfirst.frc329.beta20191.commands;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc329.beta20191.Robot;

public class Driving extends Command {

    public Driving() {

        requires(Robot.sDrive);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        boolean tank=true; //, ps4=false xb=false, ;
        double divider = Robot.sDrive.getDivisor();
        if(divider<1) divider = 1;
        divider = 1;  //    Divider disabled... 1.1
        // Robot.sDrive.jSTankdrive(Robot.oi.xb.getRawAxis(1)/divider, Robot.oi.xb.getRawAxis(5)/divider);
        // if(!(SmartDashboard.getBoolean("Use Xbox", xb))) //&& SmartDashboard.getBoolean("Use ps4", ps4)) 
            if(SmartDashboard.getBoolean("Use Tank Drive", tank))
                Robot.sDrive.jSTankdrive(Robot.oi.joystickLeft.getY()/divider, Robot.oi.joystickRight.getY()/divider); 
            else
                //Robot.sDrive.jSArcadedrive(Robot.oi.joystickLeft.getY()/divider, -Robot.oi.joystickRight.getX()*.75); //for comp
                Robot.sDrive.jSArcadedrive(Robot.oi.joystickLeft.getY()/divider, -Robot.oi.joystickRight.getX()/divider); //too sensitive; for beta 
       /* else //if((SmartDashboard.getBoolean("Use ps4", ps4)) && !SmartDashboard.getBoolean("Use Xbox", xb))
            if(SmartDashboard.getBoolean("Use Tank Drive", tank))
                Robot.sDrive.jSTankdrive(Robot.oi.xb.getRawAxis(1)/divider, Robot.oi.xb.getRawAxis(5)/divider);
            else
                Robot.sDrive.jSArcadedrive(Robot.oi.xb.getRawAxis(1)/divider, -Robot.oi.xb.getRawAxis(4)/divider);*/
       // else 
            // if(SmartDashboard.getBoolean("Use Tank Drive", tank))
            //    Robot.sDrive.jSTankdrive(Robot.oi.ps4.getRawAxis(1)/divider, Robot.oi.ps4.getRawAxis(5)/divider);
           // else
             //   Robot.sDrive.jSArcadedrive(Robot.oi.ps4.getRawAxis(1)/divider, -Robot.oi.ps4.getRawAxis(2)/divider);
        
        if((SmartDashboard.getBoolean("Reset Drive Encoder",true)))
            Robot.sDrive.clearDriveEnc(); 
        if((SmartDashboard.getBoolean("Reset Elevator Encoder",true)))
            Robot.sPIDEl.clearElevEnc();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }
}
