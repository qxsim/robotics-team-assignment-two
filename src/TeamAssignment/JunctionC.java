package TeamAssignment;

import java.util.ArrayList;
import java.util.ListIterator;
import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.*;

public class JunctionC implements Behavior {
	private DifferentialPilot DP;
	private LightSensor lsensor1;
	private LightSensor lsensor4;

	private static boolean reached = false;
	private boolean suppressed = false;
	private int choice;

	private Integer[] pathArray = new Integer[] { 3, 3, 3, 0, 0, 3, 3, 1, 1, 3, 3, 4};
	private ArrayList<Integer> pathList = new ArrayList<Integer>();
	private ListIterator<Integer> lit;

	public JunctionC(DifferentialPilot DP, SensorPort port1, SensorPort port4) {
		this.DP = DP;
		lsensor1 = new LightSensor(port1);
		lsensor4 = new LightSensor(port4);
		
		for (int i = 0; i < pathArray.length; i++) {
			pathList.add(pathArray[i]);
		}
		
		lit = pathList.listIterator();
	}

	public static boolean getReached() {
		return reached;
	}
	
	public boolean takeControl() {
		return (((lsensor1.readValue() <= 35 && lsensor4.readValue() <= 35) || LineFollowB.stuck()) && !getReached());
	}

	public void suppress() {
		suppressed = true;
	}

	public void action() {
		LineFollowB.reset();
		suppressed = false;
		
		if (lit.hasNext()) {
			choice = lit.next();
		}
		
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
		
		else if (choice == 4) {
			System.out.println("Destination reached.");
			DP.stop();
			reached = true;
		}
		
		while (Motor.A.isMoving() && Motor.B.isMoving() && !suppressed) {

		}

		DP.stop();
	}
}