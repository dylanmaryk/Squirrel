import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;

public class Explore implements Behavior {
	public boolean takeControl() {
		return true;
	}
	
	public void action() {
		// If need to keep looking for red ball
		if (Squirrel.returnToExplore)
			returnToExplore();
		
		// If not found red ball
		if (!Squirrel.hasBall) {
			// Travel further down central branch
			Squirrel.pilot.travel(Squirrel.middleDistanceStep);
			
			Squirrel.middleDistanceTotal = Squirrel.middleDistanceTotal + Squirrel.middleDistanceStep;
			
			Squirrel.pilot.reset();
			Squirrel.pilot.rotate(-90);
			Squirrel.pilot.reset();
			Squirrel.pilot.rotate(90);
			Squirrel.pilot.reset();
			Squirrel.pilot.rotate(90);
			Squirrel.pilot.reset();
			Squirrel.pilot.rotate(-90);
		}
	}
	
	private void returnToExplore() {
		returnToMainPath();
		
		Squirrel.returnToExplore = false;
		Squirrel.notDetected = true;
	}
	
	public static void returnToMainPath() {
		// Go back to main branch
		Motor.A.rotate(-Squirrel.tachoDistance, true);
		Motor.B.rotate(-Squirrel.tachoDistance);
		// Squirrel.pilot.travel(-Squirrel.distanceMoved);
		
		Squirrel.pilot.stop();
		
		Motor.A.rotate(-Squirrel.tachoRotationA, true);
		Motor.B.rotate(-Squirrel.tachoRotationB);
		// Squirrel.pilot.rotate(-Squirrel.angleRotated);
		
		Squirrel.pilot.stop();
	}

	public void suppress() {
		// Stop moving
		Squirrel.pilot.stop();
	}
}