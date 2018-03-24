package TeamAssignment;

import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.*;

public class JunctionA implements Behavior {
	private DifferentialPilot DP;
	private LightSensor lsensor1;
	private LightSensor lsensor4;
	
	private boolean suppressed = false;

	public JunctionA(DifferentialPilot DP, SensorPort port1, SensorPort port4) {
		this.DP = DP;
		lsensor1 = new LightSensor(port1);
		lsensor4 = new LightSensor(port4);
	}

	public boolean takeControl() {
		return ((lsensor1.readValue() <= 35 && lsensor4.readValue() <= 35) || LineFollowB.stuck());
	}

	public void suppress() {
		suppressed = true;
	}

	public void action() {
		LineFollowB.reset();
		suppressed = false;

		DP.travel(10);
		DP.rotate(90);

		while (Motor.A.isMoving() && Motor.B.isMoving() && !suppressed) {

		}
		DP.stop();
	}
}