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
	static int middleDistanceStep = 200;
	static int distanceToTravel = 20;
	static int distanceToGrab = 10;
	static int middleDistanceTotal = 0;
	static int branchDistance = 0;
	static int rotateAmount = 70;
	static int tachoRotated;
	static boolean turnedLeft;
	static boolean hasBall = false;
	static boolean returnToExplore = false;
	static boolean locatedBall = false; // Recently located ball while investigating, false again once has ball
	static boolean returnHome = false;
	static boolean searching = true;
	
	public static void main(String[] args) {
		// Set wheel speeds
		pilot.setTravelSpeed(50);
		pilot.setRotateSpeed(50);
		
		// Set gripper speed
		Motor.C.setSpeed(720);
		
		// Open gripper
		Motor.C.rotate(rotateAmount);
		
		// Set up behaviours and arbitrator
		Behavior behaviorExplore = new Explore();
		Behavior behaviorScan = new Scan();
		Behavior behaviorDetect = new Detect();
		Behavior behaviorReturn = new Return();
		
		Behavior[] behaviors = { behaviorExplore, behaviorScan, behaviorDetect, behaviorReturn };
		
		Arbitrator ab = new Arbitrator(behaviors);
		ab.start();
	}
}