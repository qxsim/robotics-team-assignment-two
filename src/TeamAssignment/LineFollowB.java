package TeamAssignment;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;

public class LineFollowB implements Behavior {

	private DifferentialPilot DP;
	private LightSensor lsensor1;
	private LightSensor lsensor4;

	private static int rightTrigger = 0;
	private static int leftTrigger = 0;
	private boolean suppressed = false;
	
	public static boolean stuck() {
		return (rightTrigger >= 3 && leftTrigger >= 3);
	}
	
	public static void reset() {
		rightTrigger = 0;
		leftTrigger = 0;
	}
	
	public LineFollowB(DifferentialPilot DP, SensorPort port1, SensorPort port4) {
		this.DP = DP;
		lsensor1 = new LightSensor(port1);
		lsensor4 = new LightSensor(port4);
	}
	
	@Override
	public boolean takeControl() {
		return ((lsensor1.readValue() <= 35 || lsensor4.readValue() <= 35) && !JunctionC.getReached());
	}
	
	@Override
	public void suppress() {
		
	}

	@Override
	public void action() {
		suppressed = false;
		
		while (lsensor1.readValue() <= 35) {
			rightTrigger++;
			Motor.A.rotate(-10, true);
			Motor.B.rotate(10, true);
			Delay.msDelay(500);
			Motor.A.rotate(2, true);
			Delay.msDelay(100);
		}
		
		while (lsensor4.readValue() <= 35) {
			leftTrigger++;
			Motor.A.rotate(10, true);
			Motor.B.rotate(-10, true);
			Delay.msDelay(500);
			Motor.B.rotate(2, true);
			Delay.msDelay(100);
		}
		
		while (Motor.A.isMoving() && Motor.B.isMoving() && !suppressed) {

		}
		
		DP.stop();
	}
}