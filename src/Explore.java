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
				
				// Reset tachometer count
				Motor.A.resetTachoCount();
				Motor.B.resetTachoCount();
				
				// If still exploring, record robot has turned left
				if (!Squirrel.suppressed) {
					Squirrel.turnedLeft = true;
				}
				
				Squirrel.pilot.rotate(-90);

				// If still exploring, reset tachometer count
				if (!Squirrel.suppressed) {
					Motor.A.resetTachoCount();
					Motor.B.resetTachoCount();
				}
				
				Squirrel.pilot.rotate(90);

				// If still exploring, reset tachometer count and record robot has turned right
				if (!Squirrel.suppressed) {
					Motor.A.resetTachoCount();
					Motor.B.resetTachoCount();
					
					Squirrel.turnedLeft = false;
				}
				
				Squirrel.pilot.rotate(90);

				// If still exploring, reset tachometer count
				if (!Squirrel.suppressed) {
					Motor.A.resetTachoCount();
					Motor.B.resetTachoCount();
				}
				
				Squirrel.pilot.rotate(-90);
			} else {
				// If still exploring, reset tachometer count and record robot has turned right
				if (!Squirrel.suppressed) {
					Motor.A.resetTachoCount();
					Motor.B.resetTachoCount();
					
					Squirrel.turnedLeft = false;
				}
				
				Squirrel.pilot.rotate(90);

				// If still exploring, reset tachometer count
				if (!Squirrel.suppressed) {
					Motor.A.resetTachoCount();
					Motor.B.resetTachoCount();
				}
				
				Squirrel.pilot.rotate(-90);
			}
		}
	}
	
	private void returnToExplore() {
		Squirrel.returnToMainPath();
		
		Squirrel.returnToExplore = false;
		Squirrel.notDetected = true;
	}

	public void suppress() {
		Squirrel.suppressed = true;
		
		// Stop moving
		Squirrel.pilot.stop();
	}
}