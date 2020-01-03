package org.usfirst.frc329.beta20191;

import org.usfirst.frc329.beta20191.commands.*;
import org.usfirst.frc329.beta20191.commands.HeightSetting.bottomBallRocketing;
import org.usfirst.frc329.beta20191.commands.HeightSetting.bottomRocketing;
import org.usfirst.frc329.beta20191.commands.HeightSetting.cargoStationBalling;
import org.usfirst.frc329.beta20191.commands.HeightSetting.hatchLoadingStation;
import org.usfirst.frc329.beta20191.commands.HeightSetting.loadingStationing;
import org.usfirst.frc329.beta20191.commands.HeightSetting.middleBallRocketting;
import org.usfirst.frc329.beta20191.commands.HeightSetting.middleRocketting;
import org.usfirst.frc329.beta20191.commands.HeightSetting.topBallRocketing;
import org.usfirst.frc329.beta20191.commands.HeightSetting.topRocketting;
//import org.usfirst.frc329.beta20191.commands.autonomouscommands.AutoLeftHab1FrontShip;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
    
    public Joystick joystickLeft;
    public Joystick joystickRight;
    public Joystick controlPanel;
    //public Joystick xb; 
    public JoystickButton rightTrigger, leftTrigger, clearEnc, zeroYawing, bottomHatch, bottomBall, middleHatch, floorPickup;
    public JoystickButton middleBall, ground, topHatch, topBall, loadingCargo, cargoShip, killAuto, climbMove; 
    public JoystickButton FLRetract, BLRetract, safeDown, levelClimb, climbDrive, climbing, preClimbing, checkAuto, heightAdj, goingUp, comingDown;  // , piCheck;
    public JoystickButton jsMiddleBall, jsGround, jsTopHatch, jsTopBall, jsLoadingCargo, jsCargoShip, jsKillAuto, jsClimbing, jsBottomHatch, jsBottomBall, jsMiddleHatch;
    public OI() {

        joystickRight = new Joystick(1);
        joystickLeft = new Joystick(0);
        controlPanel = new Joystick(2);
        //xb = new Joystick(4); 
    

        //TAIWAN------------------------------------------------------
        /*rightTrigger = new JoystickButton(xb, 1);  
        rightTrigger.whenPressed(new pneuOpenClosing());

        ground = new JoystickButton(xb, 4); 
        ground.whenPressed(new hatchLoadingStation());

        bottomHatch = new JoystickButton(xb, 5); 
        bottomHatch.whenPressed(new bottomRocketing());

        bottomBall = new JoystickButton(xb, 6);  
        bottomBall.whenPressed(new bottomBallRocketing());

        loadingCargo = new JoystickButton(xb, 2);  //15
        loadingCargo.whenPressed(new loadingStationing()); 

        killAuto = new JoystickButton(joystickLeft, 5); 

        jsBottomBall = new JoystickButton(joystickRight, 8);
        jsBottomBall.whenPressed(new bottomBallRocketing());

        jsBottomHatch = new JoystickButton(joystickRight, 7);
        jsBottomHatch.whenPressed(new bottomRocketing());*/
        //-------------------------------------------------------------

        //floorPickup = new JoystickButton(joystickRight, 2);
        //floorPickup.whileHeld(new PneuFloorPickupping());

        leftTrigger = new JoystickButton(joystickLeft, 1);
        leftTrigger.whenPressed(new PiAssisting());

        rightTrigger = new JoystickButton(joystickRight, 1);
        rightTrigger.whenPressed(new pneuOpenClosing());

        ground = new JoystickButton(controlPanel, 15);  //13
        ground.whenPressed(new hatchLoadingStation());

        topHatch = new JoystickButton(controlPanel, 3); //9
        topHatch.whenPressed(new topRocketting());

        middleHatch = new JoystickButton(controlPanel, 6); //8
        middleHatch.whenPressed(new middleRocketting());

        bottomHatch = new JoystickButton(controlPanel, 10); //11
        bottomHatch.whenPressed(new bottomRocketing());

        bottomBall = new JoystickButton(controlPanel, 11);  //12
        bottomBall.whenPressed(new bottomBallRocketing());

        middleBall = new JoystickButton(controlPanel, 5);  //10
        middleBall.whenPressed(new middleBallRocketting());

        topBall = new JoystickButton(controlPanel, 2);  //14
        topBall.whenPressed(new topBallRocketing());

        loadingCargo = new JoystickButton(controlPanel, 1);  //15
        loadingCargo.whenPressed(new loadingStationing()); 

        cargoShip = new JoystickButton(controlPanel, 4);  //15
        cargoShip.whenPressed(new cargoStationBalling());

        heightAdj = new JoystickButton(controlPanel, 8); 
        heightAdj.whileHeld(new ElevatorMoving());

        killAuto = new JoystickButton(controlPanel, 7);  //11

        //safeDown = new JoystickButton(controlPanel, 13); 
        //safeDown.whileHeld(new SafeDowning());

        //climbing = new JoystickButton(controlPanel, 12);
        //climbing.whileHeld(new Climbing());

        checkAuto = new JoystickButton(joystickRight, 9);  //16
        checkAuto.whenPressed(new AutoChecking());

        //////////////ZEBRA DEMO////////////////////////////
        //goingUp = new JoystickButton(controlPanel, 13);
        //goingUp.whenPressed(new GoUping());

        //comingDown = new JoystickButton(controlPanel, 14);
        //comingDown.whileHeld(new SafeDowning());
        ////////////////////////////////////////////////////

        jsClimbing = new JoystickButton(joystickRight, 12);
        jsClimbing.whileHeld(new Climbing());

        jsKillAuto = new JoystickButton(joystickRight, 11);
        
        jsLoadingCargo = new JoystickButton(joystickRight, 13);
        jsLoadingCargo.whenPressed(new loadingStationing());

        jsGround = new JoystickButton(joystickRight, 16);
        jsGround.whenPressed(new hatchLoadingStation());

        jsCargoShip = new JoystickButton(joystickRight, 14);
        jsCargoShip.whenPressed(new cargoStationBalling());

        jsTopBall = new JoystickButton(joystickRight, 10);
        jsTopBall.whenPressed(new topBallRocketing());

        jsMiddleBall = new JoystickButton(joystickRight, 9);
        jsMiddleBall.whenPressed(new middleBallRocketting());

        jsBottomBall = new JoystickButton(joystickRight, 8);
        jsBottomBall.whenPressed(new bottomBallRocketing());

        jsTopHatch = new JoystickButton(joystickRight, 5);
        jsTopHatch.whenPressed(new topRocketting());

        jsMiddleHatch = new JoystickButton(joystickRight, 6);
        jsMiddleHatch.whenPressed(new middleRocketting());

        jsBottomHatch = new JoystickButton(joystickRight, 7);
        jsBottomHatch.whenPressed(new bottomRocketing()); 

    }
    
    public Joystick getJoystickLeft() {
        return joystickLeft;
    }

    public Joystick getJoystickRight() {
       return joystickRight;
    }
    
}

        //xbTrigger = new JoystickButton(xb, 5);
        //xbTrigger.whenPressed(new pneuOpenClosing());
        // rightTrigger.whenPressed(new PIDStraightDriving(250));
        // rightTrigger.whenPressed(new PIDTurning(135));
        //rightTrigger.whenPressed(new AutoLeftHab1FrontShip());
        //leftTrigger = new JoystickButton(joystickLeft, 1);
        //leftTrigger.whenPressed(new PIDHeightKeeping());

        //piCheck = new JoystickButton(joystickRight, 2); // new, don't know if PIChecking is needed for this button
        //piCheck.whenPressed(new PiChecking());
       
        //bottomHatch = new JoystickButton(joystickRight, 5);  
        //bottomHatch.whenPressed(new bottomRocketing());
        //cargoBall = new JoystickButton(controlPanel, 14);
        //cargoBall.whenPressed(new preClimbing());

        //climbMove = new JoystickButton(joystickRight, 11);
        //climbMove.whileHeld(new ClimberMoving());
        
        //climbDrive = new JoystickButton(controlPanel, 8);
        //climbDrive.whileHeld(new ClimbDriving());
        
        //levelClimb = new JoystickButton(controlPanel, 9);
        //levelClimb.whileHeld(new LevelClimbing());

        //safeDown = new JoystickButton(controlPanel, 10);
        //safeDown.whileHeld(new SafeDowning());

        //FLRetract = new JoystickButton(controlPanel, 15);
        //FLRetract.whenPressed(new FrontLegRetracting());

        //BLRetract = new JoystickButton(controlPanel, 16);
        //BLRetract.whileHeld(new BackLegRetracting());

        //climbing = new JoystickButton(controlPanel, 13);
        //climbing.whenPressed(new Climbing());

        //preClimbing = new JoystickButton(controlPanel, 8);
        //preClimbing.whenPressed(new preClimbing());

        //killAuto = new JoystickButton(contolPanel, 9);
        //editHeight = new JoystickButton(joystickRight, 14);
