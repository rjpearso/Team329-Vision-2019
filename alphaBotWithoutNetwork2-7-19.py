#!/home/pi/Desktop/launcher.sh python
# import the necessary packages

from picamera.array import PiRGBArray
from picamera import PiCamera
from threading import Thread
import signal
import cv2
import numpy as np
from piVideoStream329v1 import PiVideoStream
from imutils.video import FPS
import argparse
import imutils
import time
import datetime
import math
from math import e
import sys
import os
import select
import socket
from networktables import NetworkTables as nt

        
#from networktables import NetworkTables as nt
#import smbus #only need this if you want to send info to the arduino
## It works without it.  Just don't uncomment the smbus lines below.
## We used this to turn a pixel read when we saw something
## it was helpful but total extra

##setup network table connection to robot
#s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
#s.settimeout(0)
#print('Network Tables is setup on Pi')
areaFactor = 0.8
piAlive = 0
'''while 1:
    try:
       ip = socket.gethostbyname('roboRIO-329-FRC.local')
       print('Connected to robot')
       break
    except:
        print('Waiting for NWT and Roborio connection')
        time.sleep(.5)
        pass
nt.initialize(server=ip)
sd = nt.getTable("SmartDashboard")'''

dist = 999  #this is a default error number for distance.  Recognize this in Robot code
vs = PiVideoStream().start() # Start taking frames
time.sleep(2.0)
timerstart = time.time()
##bus = smbus.SMBus(1) #Do not need this unless you want to talk with the arduino controlling the LED Ring
##address = 0x04
#time.sleep(2.0)
####yPixVal = 22.0 / 480 ##Field of view in Y
####xPixVal = 78 / 640 #Field of view X

display= vs.read()
screenText = ''
rightCenter = 999
leftCenter = 999
turnAng = 999
tryAgain = True
##a1 = 0
##a2 = 0
count = 0
arb = 10000
ars = 200 ##### Very important this is the too small area.  This basically determines how far way you can be and still "see" the target
while True:
    piAlive += 1
    if piAlive > 500000:
        piAlive = 0
    #sd.putNumber('Pi is alive', piAlive)
    '''try:
        if count > 200: #Only check network connection every 200 Frames
       
           while True:
               ip = socket.gethostbyname('roboRIO-329-FRC.local')
               nt.initialize(server=ip)
               sd = nt.getTable("SmartDashboard")
               break
    ##
    ##       
    ##       if ip[:2] != "10":
    ##           while True:
    ##               print("Connection lost!")
    ##               time.sleep(.5)
    ##               nt.initialize(server=ip)
    ##               sd = nt.getTable("SmartDashboard")
    ##               if ip[:2] == "10":
    ##                   break
    except:
       print("Connection lost!")
       turnAng = 999
       time.sleep(.1)
       pass
        '''

    if True:
        found = False
        process = True
        frame = vs.read() # read the most recent frame
        if np.array_equal(frame,display) == True: #Checks if this is a unique frame
            Duplicate = True
        else:
            display=frame #set the new frame to display so it can check if the next is new

            ###################################################
            ###################################################
            #  HSV  #  Set HSV values.  These work for our color green
            ###################################################
            ###################################################
            low=np.array([50,180,125])
            high=np.array([70,255,255])
            hsv = cv2.cvtColor(frame,cv2.COLOR_BGR2HSV) #Change image to HSV
            mask = cv2.inRange(hsv, low, high) #Apply Mask to image so only targets are white all else is black
            #### The following erodes the mask... This is
            #### not ideal as you can just drop the small contours after and erroding is slow
            #mask = cv2.erode(mask, None, iterations=2)
            #mask = cv2.dilate(mask, None, iterations=4)

            ct = None
            
            
            cnts = cv2.findContours(mask.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)[-2]  # Find the contours of the mask
            if len(cnts)!=0:  #only runs the following if you see something
                zzzzzz = 4
               
                #############area=cv2.contourArea(contours) ##don't need 2019 
                
                #x,y,w,h = cv2.boundingRect(cnts[ct]) ## This is if you want vertical and horizontal rectangles not good for 2019 but left it for reference
                
                '''if area < ars or area > arb:
                    too_small.append(ct)
                for c in range(1, len(too_small) + 1):#Get Rid of too small too big
                    del cnts[too_small[-c]]
                            #cnts,area = calcProperties(cnts)'''## if you use the above area you can remove contours now
                    #############################################################
            #try:### Comment out when debugging for hopefully obvious reasons also comment except and pass far below
                #############################################################
                too_small = []
                ct = 0
                for cts in cnts:
                    ### Find boundries and draw rectangle on image
                    
                    rect = cv2.minAreaRect(cts)
                    box = cv2.boxPoints(rect)
                    boxd = np.int0(box)#### Creates box form contours
                    
                    cv2.drawContours(display,[box.astype(int)],0,(0,0,255),2) #Draws box on image these are the red boxes.  Its what you see
                    
                    ####cv2.imwrite('test.png', display) ### use this to write images to disk ****Be Careful*** At 60-90 fps thats a lot of pictures and disk space
                    #print(rect) #Prints the details of the minAreaRect following this order: ( center (x,y), (width, height), angle of rotation ) ## Note the order of the rect variable



                    #################
                    #### This gets rid of the things that are too small
                    ###############################################
                    area1 = list(rect)
                    
                    
                    area = area1[1][0]*area1[1][1] ## calculate the area of the boxes
                    
                    if area < ars or area > arb:
                        #print('Found small area')
                        too_small.append(ct)
                    ct +=1
                    
                if ct is not None: ### Check to make sure we still have something
                    
                    for c in range(1, len(too_small) + 1):#Get Rid of too small edit to get rid of too big too if you need it
                        del cnts[too_small[-c]]

                
                centerFind =  800 #just assign to a big number so first value replaces it
                
                if len(cnts) == 1:
                    #turn 20 degrees to the see 2 then recheck (robot code)
                    if cnts[0][0][0][0] >= 320:
                        turnAng=20 # not sure 20 is the right number
                        tryAgain = True #tell robot code to try again after move
                    else: #if not right its left
                        turnAng=-20
                        tryAgain = True
                #########################################
                        #More than 1 target
                #########################################
                #These boxes tells you what you are calculating on the red boxes above show what you see

                elif len(cnts) > 1:
                    rightTargets = []
                    leftTargets = []
                    for c in cnts:
                        ce,wh,angle = cv2.minAreaRect(c)# this gives you the center[[(x,y),(width,Height),angle],[],[]...]
                        if abs(angle) <= 45 and abs(angle) >=4: #Are they right targets
                            rightTargets.append([ce,wh,angle,wh[0]*wh[1]])
                            
                            
                        if abs(angle) > 45 and abs(angle) < 88: #Are They Left Targets
                            leftTargets.append([ce,wh,angle,wh[0]*wh[1]])
                            
                            
                            
    ##                    print('************************************************************')
    ##                    print('right targets are: ',rightTargets,' right = ',len(rightTargets))
    ##                    print('left targets are: ',leftTargets,' left = ',len(leftTargets))
    ##                    print('************************************************************')
                        
                        rect = ce,wh,angle
                        box = cv2.boxPoints(rect)
                        boxd = np.int0(box)# Probably don't have to do this again but....
                        found = True
                        ####################
                        ## you can add below prints back in for testing
                        ####################
                        xOffSet = -5 # Right is negative in inches
                        #print('@@@@@@@@@@@@@@@@@@')
                        if len(rightTargets) >= 1 and len(leftTargets) >= 1:
                            rightCenter = min(rightTargets, key=lambda x:abs(x[0][0]-320))
                            leftCenter = min(leftTargets, key=lambda x:abs(x[0][0]-320))
                            rightMaxArea = max(rightTargets, key=lambda x:abs(x[3]))
                            leftMaxArea = max(leftTargets, key=lambda x:abs(x[3]))
                            
                            if rightCenter[0][0] > areaFactor * float(rightMaxArea[0][0]): ## We are looking at a target which is close to the center
                                
                                if leftCenter[0][0] <= rightCenter[0][0]:
                                    screenText = 'Hatch'
                                    heightRight = (rightCenter[0][1] + (rightCenter[1][1]/2)) * 2
                                    heightLeft = (leftCenter[0][1] + (leftCenter[1][1]/2)) * 2
                                    x = (rightCenter[1][0] * rightCenter[1][1] + leftCenter[1][0] * leftCenter[1][1]) / 2 #calculates the center of the left and right target
                                    dist = (1421 * x ** (-.464)) - 3.9375
                                    #dist = (95.84 * x ** -0.449)*12 #this is the formula to calc distance.  You will need to do this in excel for your camera
                                    #print('Distance: ' + str(dist)) ### Put this back for testing
                                    theta = math.asin(xOffSet/dist)
                                    #dist = dist * math.cos(theta)

                                    
                                    #if rightCenter[3] >= areaFactor*rightMaxArea[3] and leftCenter[3] >= areaFactor*leftMaxArea[3]:
                                    pixInchConv = 11 / abs((leftCenter[0][0] - rightCenter[0][0]))
                                    perpDist = pixInchConv * (((leftCenter[0][0] + rightCenter[0][0]) / 2) - 320) #need to test this on robot
                                    turnAng = round(math.degrees(math.atan(perpDist / dist) + theta),1)#turnAng = round(math.degrees(math.atan(perpDist / dist)),1)
                                    tryAgain = False #we should hit it
                                    
                                elif leftCenter[0][0] > rightCenter[0][0] :
                                    x = (rightCenter[1][0] * rightCenter[1][1] + leftCenter[1][0] * leftCenter[1][1]) / 2 #calculates the center of the left and right target
                                    dist = (1421 * x ** (-.464)) - 3.9375#this is the formula to calc distance.  You will need to do this in excel for your camera
                                    #print('Distance: ' + str(dist)) ### Put this back for testing
                                    theta = math.asin(xOffSet/dist)
                                    #dist = dist * math.cos(theta)                                
                                    #if rightCenter[3] >= areaFactor*rightMaxArea[3] and leftCenter[3] >= areaFactor*leftMaxArea[3]:
                                    pixInchConv = 10 / abs((leftCenter[0][0] - rightCenter[0][0]))
                                    perpDist = pixInchConv * (((leftCenter[0][0] + rightCenter[0][0]) / 2) - 320) #need to test this on robot
                                    turnAng = round(math.degrees(math.atan(perpDist / dist) + theta),1)#turnAng = round(math.degrees(math.atan(perpDist / dist) + theta),1)#turnAng = round(math.degrees(math.atan(perpDist / dist)),1)
                                    tryAgain = True
                                else:
                                    x = (rightCenter[1][0] * rightCenter[1][1] + leftCenter[1][0] * leftCenter[1][1]) / 2 #calculates the center of the left and right target
                                    dist = (1421 * x ** (-.464)) - 3.9375#this is the formula to calc distance.  You will need to do this in excel for your camera

                                    theta = math.asin(xOffSet/dist)
                                    #dist = dist * math.cos(theta)
                                    #print('Distance: ' + str(dist)) ### Put this back for testing
                                
                                    #if rightCenter[3] >= areaFactor*rightMaxArea[3] and leftCenter[3] >= areaFactor*leftMaxArea[3]:
                                    pixInchConv = 10 / abs((leftCenter[0][0] - rightCenter[0][0]))
                                    perpDist = pixInchConv * (((leftCenter[0][0] + rightCenter[0][0]) / 2) - 320) #need to test this on robot
                                    turnAng = round(math.degrees(math.atan(perpDist / dist) + theta),1)#turnAng = round(math.degrees(math.atan(perpDist / dist)),1)
                                    tryAgain = True
                                    
                            elif leftMaxArea[0][0] > leftCenter[0][0]:
                                x = (rightMaxArea[1][0] * rightMaxArea[1][1] + leftMaxArea[1][0] * leftMaxArea[1][1]) / 2 #calculates the center of the left and right target
                                dist = (1421 * x ** (-.464)) - 3.9375 #this is the formula to calc distance.  You will need to do this in excel for your camera
                                #print('Distance: ' + str(dist)) ### Put this back for testing
                                theta = math.asin(xOffSet/dist)
                                #dist = dist * math.cos(theta)
                                
                                #if rightCenter[3] >= areaFactor*rightMaxArea[3] and leftCenter[3] >= areaFactor*leftMaxArea[3]:
                                pixInchConv = 10 / abs((leftMaxArea[0][0] - rightMaxArea[0][0]))
                                perpDist = pixInchConv * (((leftMaxArea[0][0] + rightMaxArea[0][0]) / 2) - 320) #need to test this on robot
                                turnAng = round(math.degrees(math.atan(perpDist / dist) + theta),1)#turnAng = round(math.degrees(math.atan(perpDist / dist)),1)
                                tryAgain = True
                                
                            elif rightMaxArea[0][0] > rightCenter[0][0]:
                                x = (rightMaxArea[1][0] * rightMaxArea[1][1] + leftMaxArea[1][0] * leftMaxArea[1][1]) / 2 #calculates the center of the left and right target
                                dist = (1421 * x ** (-.464)) - 3.9375 #this is the formula to calc distance.  You will need to do this in excel for your camera
                                #print('Distance: ' + str(dist)) ### Put this back for testing
                                theta = math.asin(xOffSet/dist)
                                #dist = dist * math.cos(theta)

                                
                                #if rightCenter[3] >= areaFactor*rightMaxArea[3] and leftCenter[3] >= areaFactor*leftMaxArea[3]:
                                pixInchConv = 10 / abs((leftMaxArea[0][0] - rightMaxArea[0][0]))
                                perpDist = pixInchConv * (((leftMaxArea[0][0] + rightMaxArea[0][0]) / 2) - 320) #need to test this on robot
                                turnAng = round(math.degrees(math.atan(perpDist / dist) + theta),1)#turnAng = round(math.degrees(math.atan(perpDist / dist)),1)
                                tryAgain = True
                                
                            else:
                                x = (rightMaxArea[1][0] * rightMaxArea[1][1] + leftMaxArea[1][0] * leftMaxArea[1][1]) / 2 #calculates the center of the left and right target
                                dist = (1421 * x ** (-.464)) - 3.9375 #this is the formula to calc distance.  You will need to do this in excel for your camera
                                #print('Distance: ' + str(dist)) ### Put this back for testing
                                theta = math.asin(xOffSet/dist)
                                #dist = dist * math.cos(theta)                                

                                #if rightCenter[3] >= areaFactor*rightMaxArea[3] and leftCenter[3] >= areaFactor*leftMaxArea[3]:
                                pixInchConv = 11 / abs((leftMaxArea[0][0] - rightMaxArea[0][0]))
                                perpDist = pixInchConv * (((leftMaxArea[0][0] + rightMaxArea[0][0]) / 2) - 320) #need to test this on robot
                                turnAng = round(math.degrees(math.atan(perpDist / dist) + theta),1)#turnAng = round(math.degrees(math.atan(perpDist / dist)),1)
                                tryAgain = True



##                                    
##                                    if rightCenter[3] < rightMaxArea[3]:
##                                        turnAng = 10
##                                        tryAgain = True
##                                    else :
##                                        turnAng = -10
##                                        tryAgain = True
##                                    
##
##                            else: ## we are closer to the space between hatches
##                                #print('&&&&&&&&&&&&&&&')
##                                #print('between hatches')
##                                screenText = 'Between'
##                                if rightCenter[3] >= leftMaxArea[3]:
##                                    turnAng = -5
##                                    tryAgain = True
##                                else:
##                                    turnAng = 5
##                                    tryAgain = True
##                                    
##                                #print('&&&&&&&&&&&&&&&')
##                                ##################################################
##                                ############# You will need to make sure the +- are right and left in your robot code
##                                ##################################################
##                                '''if abs(rightCenter[0][0]-320) <= a bs(leftCenter[0][0]-320): #You will have to think about this but its correct
##                                    #print('Turn Left')
##                                    turnAng = -5
##                                    tryAgain = True
##                                    screenText = screenText + ' Left'
##                                elif abs(rightCenter[0][0]-320) > abs(leftCenter[0][0]-320):
##                                    #print('Turn Right')
##                                    turnAng = 5
##                                    tryAgain = True
##                                    screenText = screenText + ' Right'
##
##                                    '''
##                                    
##                        if rightCenter != 999 and leftCenter != 999: # Makes sure right center and left center have been initialized
##                            heightRight = (rightCenter[0][1] + (rightCenter[1][1]/2)) * 2
##                            heightLeft = (leftCenter[0][1] + (leftCenter[1][1]/2)) * 2
##                            #print('Right:' + str(heightRight ) + 'Left: ' + str(heightLeft))
##                            x = (rightCenter[1][0] * rightCenter[1][1] + leftCenter[1][0] * leftCenter[1][1]) / 2 #calculates the center of the left and right target
##                            dist = (95.841 * x ** -0.449)*12 #this is the formula to calc distance.  You will need to do this in excel for your camera
##                            #print('Distance: ' + str(dist)) ### Put this back for testing


                        #else:
                         #   print("Targets don't make sense")


                    

                        
                #except:
                #    pass
                if dist is not None and turnAng is not None:
                    None
                    screenText = 'dist=' + str(round(dist,1)) + ' Ang=' + str(round(turnAng,1)) #+ ' dist=' + str(round(dist,1))#+ ' a2=' + str(round(a2,1))str(screenText) + ' Ang=' + str(round(turnAng,1)) + ' dist=' + str(round(dist,1))
                    screenText = str(dist)
                    #cv2.drawContours(display,[box.astype(int)],0,(255,0,0),2) #image channel must be 2 draw blue boxes
                    cv2.line(display,(320,10),(320,470),(255,0,0),2) #draw line in middle of screen
                    font                   = cv2.FONT_HERSHEY_SIMPLEX
                    bottomLeftCornerOfText = (10,300)
                    fontScale              = 1
                    fontColor              = (255,255,255)
                    lineType               = 2
                    cv2.putText(display, screenText, 
                    bottomLeftCornerOfText, 
                    font, 
                    fontScale,
                    fontColor,
                    lineType)### Put text on the screen

                    screenText = ''# reset value of screen text
                    #print('')
                else:
                    turnAng = 999
                PiOffsetX = 0 #put in value to adjust for camera position and code it
                PiOffsetDist = 0 #put in its distance back from bumper
                dist = PiOffsetDist + dist
                #sd.putNumber("Turn Angle", turnAng) #Add these back in to send data to Network tables
                #sd.putNumber("Try Again", tryAgain)
                #sd.putNumber('Distance Away',dist)

##### to write data to Arduino to change light pattern if we see it
##           if found:
##                writeI2C(1)
##            else:
##                writeI2C(2)


            
            #cv2.imshow("Mask", mask) ##look at mask image  ############ COMMENT OUT WHEN ON ROBOT !!!!!!!!!!!!!!!!!! Huge speed penalty
            cv2.imshow("Frame",display) ## look at what it sees ############ COMMENT OUT WHEN ON ROBOT !!!!!!!!!!!!!!!!!!

            
            #Comment back in to see time per frame
            #print('time', time.time() - currentTime)

            key = cv2.waitKey(1) & 0xFF


    else:
        vs.stop()
        #print('Stopping')
        os.system("sudo shutdown -h now")  ### you can use this to shutdown.  But you should use a battery pack


# do a bit of cleanup
cv2.destroyAllWindows()
vs329.PiVideoStream.stop