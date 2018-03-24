package TeamAssignment;

import java.util.Random;
import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.*;

public class JunctionB implements Behavior {
	private DifferentialPilot DP;
	private LightSensor lsensor1;
	private LightSensor lsensor4;
	
	private boolean suppressed = false;
	private int max = 3;
	private int min = 0;

	public JunctionB(DifferentialPilot DP, SensorPort port1, SensorPort port4) {
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
		Random random = new Random();
		int choice = random.nextInt(max - min + 1) + min;
		System.out.println(choice);
		DP.travel(10);
		if (choice == 0) {
			DP.rotate(90);
		}
		
		else if (choice == 1) {
			DP.rotate(-90);
		}
		
		else if (choice == 2) {
			DP.rotate(180);
		}
		
		else if (choice == 3) {
			
		}
		
		while (Motor.A.isMoving() && Motor.B.isMoving() && !suppressed) {

		}

		DP.stop();
	}
}