package TeamAssignment;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;
import rp.systems.RobotProgrammingDemo;

public class LineFollower extends RobotProgrammingDemo implements SensorPortListener {

	private DifferentialPilot DP;
	private final SensorPort ls1;
	private final SensorPort ls4;
	private static LightSensor lsensor1;
	private static LightSensor lsensor4;

	public LineFollower(SensorPort ls1, SensorPort ls4) {
		this.ls1 = ls1;
		this.ls4 = ls4;
		DP = new DifferentialPilot(5.5f, 17.3f, Motor.A, Motor.B);
	}	

	@Override
	public void stateChanged(SensorPort aSource, int aOldValue, int aNewValue) {
		
	}

	public void run() {
		this.ls1.addSensorPortListener(this);
		this.ls4.addSensorPortListener(this);
		float infinity = Float.POSITIVE_INFINITY;
	
		while(m_run) {
			DP.travel(infinity, true);
			DP.setTravelSpeed(5);
			
			while (lsensor1.readValue() <= 35) {
				DP.stop();
				Motor.A.rotate(-50, true);
				Motor.B.rotate(10, true);
				Delay.msDelay(500);
				Delay.msDelay(100);
			}
			
			while (lsensor4.readValue() <= 35) {
				DP.stop();
				Motor.A.rotate(10, true);
				Motor.B.rotate(-50, true);
				Delay.msDelay(500);
				Delay.msDelay(100);
			}
			
			if (lsensor1.readValue() <= 35 && lsensor4.readValue() <= 35) {
				DP.travel(-5);
			}
		}
	}
	
	public static void main (String[] args) throws InterruptedException {
		Button.waitForAnyPress();
		RobotProgrammingDemo lf = new LineFollower(SensorPort.S1, SensorPort.S4);
		lsensor1 = new LightSensor(SensorPort.S1);
		lsensor4 = new LightSensor(SensorPort.S4);
		lf.run();
	}
}