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
			if (!Squirrel.turnedLeft) {
				// Travel further down central branch
				Squirrel.pilot.travel(Squirrel.middleDistanceStep);
				
				Squirrel.middleDistanceTotal = Squirrel.middleDistanceTotal + Squirrel.middleDistanceStep;
				
				Motor.A.resetTachoCount();
				Motor.B.resetTachoCount();
				
				if (!Squirrel.suppressed) {
					Squirrel.turnedLeft = true;
				}
				
				Squirrel.pilot.rotate(-90);
				
				if (!Squirrel.suppressed) {
					Motor.A.resetTachoCount();
					Motor.B.resetTachoCount();
				}
				
				Squirrel.pilot.rotate(90);
				
				if (!Squirrel.suppressed) {
					Motor.A.resetTachoCount();
					Motor.B.resetTachoCount();
					
					Squirrel.turnedLeft = false;
				}
				
				Squirrel.pilot.rotate(90);
				
				if (!Squirrel.suppressed) {
					Motor.A.resetTachoCount();
					Motor.B.resetTachoCount();
				}
				
				Squirrel.pilot.rotate(-90);
			} else {
				if (!Squirrel.suppressed) {
					Motor.A.resetTachoCount();
					Motor.B.resetTachoCount();
					
					Squirrel.turnedLeft = false;
				}
				
				Squirrel.pilot.rotate(90);
				
				if (!Squirrel.suppressed) {
					Motor.A.resetTachoCount();
					Motor.B.resetTachoCount();
				}
				
				Squirrel.pilot.rotate(-90);
			}
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
		Squirrel.suppressed = true;
		
		// Stop moving
		Squirrel.pilot.stop();
	}
}