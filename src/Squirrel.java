import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Squirrel {
	public static DifferentialPilot pilot = new DifferentialPilot(56, 110, Motor.A, Motor.B);
	public static UltrasonicSensor us = new UltrasonicSensor(SensorPort.S2);
	public static LightSensor ls = new LightSensor(SensorPort.S1);
	public static TouchSensor ts = new TouchSensor(SensorPort.S3);
	public static int middleDistanceTotal = 0;
	public static int rotateAmount = 110;
	public static int middleDistanceStep = 200;
	public static int distanceToTravel = 20;
	public static int distanceToGrab = 10;
	public static int tachoDistance = 0;
	public static int tachoRotationA = 0;
	public static int tachoRotationB = 0;
	public static boolean turnedLeft = false;
	public static boolean suppressed = false;
	public static boolean notDetected = true;
	public static boolean hasBall = false;
	public static boolean returnToExplore = false;
	public static boolean returnHome = false;
	public static boolean atHome = false;
	
	public static void main(String[] args) {
		// Set wheel speeds
		pilot.setTravelSpeed(100);
		pilot.setRotateSpeed(100);
		
		// Set gripper speed
		Motor.C.setSpeed(720);
		
		// Open gripper
		Motor.C.rotate(rotateAmount);
		
		// Set up behaviours and arbitrator
		Behavior behaviorExplore = new Explore();
		Behavior behaviorDetect = new Detect();
		Behavior behaviorReturn = new Return();
		Behavior behaviorButton = new Button();
		
		Behavior[] behaviors = { behaviorExplore, behaviorDetect, behaviorReturn, behaviorButton };
		
		Arbitrator ab = new Arbitrator(behaviors);
		ab.start();
	}
	
	public static void returnToMainPath() {
		// Reverse back to central branch
		Motor.A.rotate(-tachoDistance, true);
		Motor.B.rotate(-tachoDistance);
		
		pilot.stop();
		
		// Rotate to line back up with central branch
		Motor.A.rotate(-tachoRotationA, true);
		Motor.B.rotate(-tachoRotationB);
		
		pilot.stop();
	}
}