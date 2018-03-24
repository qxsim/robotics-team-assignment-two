package TeamAssignment;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import lejos.robotics.navigation.DifferentialPilot;
import rp.systems.RobotProgrammingDemo;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
 
public class JunctionControlA extends RobotProgrammingDemo implements SensorPortListener {
	
	private static DifferentialPilot DP;

	public JunctionControlA () {
		DP = new DifferentialPilot(5.5f, 17.3f, Motor.A, Motor.B);
	}

	@Override
	public void stateChanged(SensorPort aSource, int aOldValue, int aNewValue) {
		
	}

	public void run() {
		
	}
	
	public static void main (String[] args) throws InterruptedException {
		Button.waitForAnyPress();
		DP = new DifferentialPilot(5.5f, 17.3f, Motor.A, Motor.B);
		DP.setTravelSpeed(5);
		Behavior b1 = new Forward(DP);
		Behavior b2 = new JunctionA(DP, SensorPort.S1, SensorPort.S4);
		Behavior b3 = new LineFollowB(DP, SensorPort.S1, SensorPort.S4);
		Behavior [] bArray = {b1, b3, b2};
		Arbitrator arby = new Arbitrator(bArray);
		arby.start();
	}
}