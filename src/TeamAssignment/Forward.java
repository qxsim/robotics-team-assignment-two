package TeamAssignment;

import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.*;

public class Forward implements Behavior {
	
	private DifferentialPilot DP;
	
	private boolean suppressed = false;

	public Forward(DifferentialPilot DP) {
		this.DP = DP;
	}
	
	public boolean takeControl() {
		return (!JunctionC.getReached());
	}

	public void suppress() {
		suppressed = true;
	}

	public void action() {
		suppressed = false;
		DP.forward();
		
		while (!suppressed) {

		}
		DP.stop(); // clean up
	}
}