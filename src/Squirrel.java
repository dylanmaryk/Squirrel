import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Squirrel {
	static DifferentialPilot pilot = new DifferentialPilot(56, 110, Motor.A, Motor.B);
	static UltrasonicSensor us = new UltrasonicSensor(SensorPort.S2);
	static LightSensor ls = new LightSensor(SensorPort.S1);
	static boolean hasBall = false;
	static boolean returnToExplore = false;
	
	public static void main(String[] args) {
		pilot.setTravelSpeed(50);
		pilot.setRotateSpeed(50);
		
		Motor.C.setSpeed(720);
		
		Behavior behaviorExplore = new Explore();
		Behavior behaviorDetect = new Detect();
		
		Behavior[] behaviors = { behaviorExplore, behaviorDetect };
		
		Arbitrator ab = new Arbitrator(behaviors);
		ab.start();
	}
}