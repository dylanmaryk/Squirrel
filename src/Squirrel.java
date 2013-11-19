import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Squirrel {
	static DifferentialPilot pilot = new DifferentialPilot(56, 110, Motor.A, Motor.C); // Measurements may be different on new robot
	static UltrasonicSensor us = new UltrasonicSensor(SensorPort.S1); // Sensor port may be different on new robot
	
	public static void main(String[] args) {
		Behavior behaviorExplore = new Explore();
		
		Behavior[] behaviors = { behaviorExplore };
		
		Arbitrator ab = new Arbitrator(behaviors);
		ab.start();
	}
}