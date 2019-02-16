# use this later https://www.pyimagesearch.com/2015/12/21/increasing-webcam-fps-with-python-and-opencv/

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

count = 1
areaFactor = 0.8
tapeCenterWidth = 12
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.settimeout(0)
print("Pi is connected to NetworkTables")
while 1:
    try:
       ip = socket.gethostbyname('roboRIO-329-FRC.local')
       connected = True
       print("Network connected")
       break
    except:
        print("Waiting for Roborio and MWT connection")
        time.sleep(.5)
        connected = False
        pass
nt.initialize(server=ip)
sd = nt.getTable("SmartDashboard")
global piIsAlive
piIsAlive = 0
theta = 0
dist = 999  
vs = PiVideoStream().start()
time.sleep(2.0)
timerstart = time.time()
count += 1
display= vs.read()
screenText = ''
rightCenter = 999
leftCenter = 999
turnangle = 999
arb = 10000
ars = 200 
def contour(cts):
    rect = cv2.minAreaRect(cts)
    box = cv2.boxPoints(rect)
    return(np.int0(box),rect)
    
def CalcProperties(rightCenter, leftCenter):
    x = (rightCenter[1][0] * rightCenter[1][1] + leftCenter[1][0] * leftCenter[1][1]) / 2
    dist = (1421 * x ** (-.464)) - 3.9375
    return(dist)
def OffsetCalcProperties(rightCenter, leftCenter):
    x = (rightCenter[1][0] * rightCenter[1][1] + leftCenter[1][0] * leftCenter[1][1]) / 2
    dist = (1421 * x ** (-.464)) - 3.9375
    xOffSet = -5
    theta = math.asin(xOffSet/dist)
    dist = dist * math.cos(theta)
    return(dist, theta)

def piAlive():
    global piIsAlive

    piIsAlive += 1
    if piIsAlive > 500000:
        piIsAlive = 0
    sd.putNumber('Pi is alive', piIsAlive)
while True:
    piAlive()
    if count > 200: #Only check network connection every 200 Frames
       count = 0
       try:
           while True:
               ip = socket.gethostbyname('roboRIO-329-FRC.local')
               nt.initialize(server=ip)
               sd = nt.getTable("SmartDashboard")
               print("Network reconnected")
               break                
       except:
           turnAng = 999
           time.sleep(.1)
           pass
    if True:
        fund = False
        process = True
        frame = vs.read()
        if np.array_equal(frame,display) == True: 
            Duplicate = True
        else:
            display=frame 
            low=np.array([50,180,125])
            high=np.array([70,255,255])
            hsv = cv2.cvtColor(frame,cv2.COLOR_BGR2HSV)
            mask = cv2.inRange(hsv, low, high)
            
            ct = None
            
            
            cnts = cv2.findContours(mask.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)[-2] 
            if len(cnts)!=0:  
                zzzzzz = 4

                too_small = []
                ct = 0
		
                for cts in cnts:
                    cv2.drawContours(display,[box.astype(int)],0,(0,0,255),2) #Draws box on image these are the red boxes.  Its what you see
                    boxd,rect = contour(cts)
                    area1 = list(rect)
                    
                    area = area1[1][0]*area1[1][1] 
                    
                    if area < ars or area > arb:
                        too_small.append(ct)
                    ct +=1
                    
                if ct is not None:
                    
                    for c in range(1, len(too_small) + 1):
                        del cnts[too_small[-c]]

                
                centerFind =  800 
                
                if len(cnts) == 1:
                    if cnts[0][0][0][0] >= 320:
                        turnangle=20
                        tryAgain = True 
                    else: 
                        turnangle=-20
                        tryAgain = True

                elif len(cnts) > 1:
                    rightTargets = []
                    leftTargets = []
                    for c in cnts:
                        ce,wh,angle = cv2.minAreaRect(c)
                        if abs(angle) <= 45 and abs(angle) >=4: 
                            rightTargets.append([ce,wh,angle,wh[0]*wh[1]])
                            
                            
                        if abs(angle) > 45 and abs(angle) < 88: 
                            leftTargets.append([ce,wh,angle,wh[0]*wh[1]])
                            
                            
                        
                        boxd,rect = contour(cts)
                        
                        if len(rightTargets) >= 1 and len(leftTargets) >= 1:
                            rightCenter = min(rightTargets, key=lambda x:abs(x[0][0]-320))
                            leftCenter = min(leftTargets, key=lambda x:abs(x[0][0]-320))
                            rightMaxArea = max(rightTargets, key=lambda x:abs(x[3]))
                            leftMaxArea = max(leftTargets, key=lambda x:abs(x[3]))
                            
                            if rightCenter[0][0] > areaFactor * float(rightMaxArea[0][0]): 
                                
                                if leftCenter[0][0] <= rightCenter[0][0]:
                                    screenText = 'Hatch'
                                    heightRight = (rightCenter[0][1] + (rightCenter[1][1]/2)) * 2
                                    heightLeft = (leftCenter[0][1] + (leftCenter[1][1]/2)) * 2
                                    dist, theta = OffsetCalcProperties(rightCenter, leftCenter)
                                
                                    pixInchConv = tapeCenterWidth / abs((leftCenter[0][0] - rightCenter[0][0]))
                                    perpDist = pixInchConv * (((leftCenter[0][0] + rightCenter[0][0]) / 2) - 320)
                                    turnangle = round(math.degrees(math.atan(perpDist / dist) + theta),1)
                                    tryAgain = False
                                    
                                elif leftCenter[0][0] > rightCenter[0][0] :
                                    dist = CalcProperties(rightCenter, leftCenter)
									
                                    pixInchConv = tapeCenterWidth / abs((leftCenter[0][0] - rightCenter[0][0]))
                                    perpDist = pixInchConv * (((leftCenter[0][0] + rightCenter[0][0]) / 2) - 320)
                                    turnangle = round(math.degrees(math.atan(perpDist / dist)),1)
                                    tryAgain = True
                                else:
                                    dist = CalcProperties(rightCenter, leftCenter)

                                    pixInchConv = tapeCenterWidth / abs((leftCenter[0][0] - rightCenter[0][0]))
                                    perpDist = pixInchConv * (((leftCenter[0][0] + rightCenter[0][0]) / 2) - 320) 
                                    turnangle = round(math.degrees(math.atan(perpDist / dist)),1)
                                    tryAgain = True
                                    
                            elif leftMaxArea[0][0] > leftCenter[0][0]:
                                dist = CalcProperties(rightCenter, leftCenter)
                                

                                pixInchConv = tapeCenterWidth / abs((leftMaxArea[0][0] - rightMaxArea[0][0]))
                                perpDist = pixInchConv * (((leftMaxArea[0][0] + rightMaxArea[0][0]) / 2) - 320)
                                turnangle = round(math.degrees(math.atan(perpDist / dist)),1)
                                tryAgain = True
                                
                            elif rightMaxArea[0][0] > rightCenter[0][0]:
                                dist = CalcProperties(rightCenter, leftCenter)

                                pixInchConv = tapeCenterWidth / abs((leftMaxArea[0][0] - rightMaxArea[0][0]))
                                perpDist = pixInchConv * (((leftMaxArea[0][0] + rightMaxArea[0][0]) / 2) - 320)
                                turnangle = round(math.degrees(math.atan(perpDist / dist)),1)
                                tryAgain = True
                                
                            else:
                                dist = CalcProperties(rightCenter, leftCenter)
                                
                                
                                pixInchConv = tapeCenterWidth / abs((leftMaxArea[0][0] - rightMaxArea[0][0]))
                                perpDist = pixInchConv * (((leftMaxArea[0][0] + rightMaxArea[0][0]) / 2) - 320)
                                turnangle = round(math.degrees(math.atan(perpDist / dist)),1)
                                tryAgain = True
                
                PiOffsetX = 0
                PiOffsetDist = 0
                dist = PiOffsetDist + dist 
                def sdPut():
                    sd.putNumber("Turn Angle", turnangle)
                    sd.putNumber("Try Again", tryAgain)
                    sd.putNumber('Distance Away',dist)
                if connected == True:
                    sdPut()
				
##                fps_time = time.time 
##                fps_time = float(time.time)
##                fps_time = np.float(time.time)
##                fps_time = time()
##                FPS = (count / fps_time)
##                FPS = float(count / fps_time)
##                FPS = float(count (fps_time))
##                print(FPS)

    else:
        vs.stop()
		
cv2.destroyAllWindows()
vs329.PiVideoStream.stop
