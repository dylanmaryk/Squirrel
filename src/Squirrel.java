import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Squirrel {
	public static DifferentialPilot pilot = new DifferentialPilot(56, 110, Motor.A, Motor.B);
	public static UltrasonicSensor us = new UltrasonicSensor(SensorPort.S2);
	public static LightSensor ls = new LightSensor(SensorPort.S1);
	public static int middleDistanceTotal = 0;
	public static int rotateAmount = 140;
	public static int middleDistanceStep = 200;
	public static int distanceToTravel = 20;
	public static int distanceToGrab = 10;
	public static int tachoDistance = 0;
	public static int tachoRotationA = 0;
	public static int tachoRotationB = 0;
	// public static float distanceMoved = 0;
	// public static float angleRotated = 0;
	public static boolean notDetected = true;
	public static boolean hasBall = false;
	public static boolean returnToExplore = false;
	public static boolean returnHome = false;
	
	public static void main(String[] args) {
		// Set wheel speeds
		pilot.setTravelSpeed(50);
		pilot.setRotateSpeed(50);
		
		// Set gripper speed
		Motor.C.setSpeed(720);
		
		// Open gripper
		// Motor.C.rotate(rotateAmount);
		
		// Set up behaviours and arbitrator
		Behavior behaviorExplore = new Explore();
		Behavior behaviorDetect = new Detect();
		Behavior behaviorReturn = new Return();
		
		Behavior[] behaviors = { behaviorExplore, behaviorDetect, behaviorReturn };
		
		Arbitrator ab = new Arbitrator(behaviors);
		ab.start();
	}
}