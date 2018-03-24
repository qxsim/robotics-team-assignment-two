package TeamAssignment;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import rp.systems.RobotProgrammingDemo;
import rp.util.Rate;

public class DistanceControl extends RobotProgrammingDemo implements SensorPortListener {

	private final SensorPort ulsound2;
	private static UltrasonicSensor ulsensor;
	
	private final float MAX_DISTANCE = 25;
	private double MAX_SPEED;
	private final double Kp = 0.25;
	private double errorSignal;
	private double m_error = 0;
	private DifferentialPilot DP;

	public DistanceControl(SensorPort ulsound2) {
		this.ulsound2 = ulsound2;
		DP = new DifferentialPilot(5.5f, 17.3f, Motor.A, Motor.B);
	}	
	
	private float getDistance() { //returns distance 
		return ulsensor.getDistance();
	}
	
	private void setSpeed(double outputSignal) { //sets speed
		if (outputSignal >= MAX_SPEED) {
			DP.setTravelSpeed(MAX_SPEED);
		}
		
		else if (outputSignal <= 0)
			DP.setTravelSpeed(0);
		
		else {
			DP.setTravelSpeed(outputSignal);	
		}
	}
	
	private double getSpeed() { //gets speed
		return DP.getTravelSpeed();
	}


	@Override
	public void stateChanged(SensorPort aSource, int aOldValue, int aNewValue) {
		
	}

	public void run() {
		this.ulsound2.addSensorPortListener(this);
		Rate rate;
		float infinity = Float.POSITIVE_INFINITY;
	
		while(m_run) { // While loop runs while m_running is true
			DP.travel(infinity, true);
			MAX_SPEED = (DP.getMaxTravelSpeed())/2;
			rate = new Rate(40);
			rate.sleep();
			
			double distance = getDistance()  ; 	//gets distance
			errorSignal = distance - MAX_DISTANCE; //measures error
			
			double normalisedError = getSpeed() + errorSignal*Kp;
		
			setSpeed(normalisedError);
			errorSignal = m_error; //resets the error signal to 0 at the end of the loop.
		}
	}
	
	public static void main (String[] args) throws InterruptedException {
		Button.waitForAnyPress();
		RobotProgrammingDemo dc = new DistanceControl(SensorPort.S2);
		ulsensor = new UltrasonicSensor(SensorPort.S2);
		dc.run();
	}
}