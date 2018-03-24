package TeamAssignment;

import java.awt.Rectangle;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import lejos.nxt.addon.NXTCam;
import lejos.robotics.navigation.DifferentialPilot;
import rp.systems.RobotProgrammingDemo;
import rp.util.Rate;

public class CamFollower extends RobotProgrammingDemo implements SensorPortListener {

	private final SensorPort camPort3;
	private static NXTCam cam;
	private DifferentialPilot DP;

	public CamFollower(SensorPort camPort3) {
		this.camPort3 = camPort3;
		DP = new DifferentialPilot(5.5f, 17.3f, Motor.A, Motor.B);
	}	
	
	@Override
	public void stateChanged(SensorPort aSource, int aOldValue, int aNewValue) {
		
	}

	public double getArea(Rectangle rec) {
		return rec.getHeight()*rec.getWidth();
	}
	
	public void run() {
		this.camPort3.addSensorPortListener(this);
		cam.setTrackingMode(NXTCam.OBJECT_TRACKING);
		cam.sortBy(NXTCam.SIZE);
		cam.enableTracking(true);
		Rate r = new Rate(5);
		DP.setTravelSpeed(10);
		
		while(m_run) {
			r.sleep();
			Double centerX = null;
			
			Boolean red = cam.getObjectColor(0) == 1;
			Boolean blue = cam.getObjectColor(0) == 2;
			Boolean whiteLight = cam.getObjectColor(0) == 3;
			
			if (red || blue || whiteLight) {
				if(red) {
					System.out.println("Red");
				}
				
				else if (blue) {
					System.out.println("Blue");
				}
				
				else if (whiteLight) {
					System.out.println("Light");
				
				}
				
				else {
					
				}
				
				DP.forward();
				Rectangle  rec = cam.getRectangle(0);
				centerX = rec.getCenterX();
				System.out.println("CenterX: " + rec.getCenterX());
				
				if (centerX < 70) {
					Motor.B.rotate(10, true);
					Motor.A.rotate(-10, true);
				}
				
				else if (centerX > 100) {
					Motor.A.rotate(10, true);
					Motor.B.rotate(-10, true);
				}
				
				else if (centerX >= 70 && centerX <= 100) {
					
				}
				
				else {
					
				}
			}
			
			else {
				DP.rotate(15, true);
			}	
		}
	}
	
	public static void main (String[] args) throws InterruptedException {
		Button.waitForAnyPress();
		RobotProgrammingDemo cf = new CamFollower(SensorPort.S3);
		cam = new NXTCam(SensorPort.S3);
		cf.run();
	}
}