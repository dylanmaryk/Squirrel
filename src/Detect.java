import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;

public class Detect implements Behavior {
	public boolean takeControl() { // Set locatedBall to false near end of action method
		// If sees something nearby and not already investigating a detected object
		if (Squirrel.us.getDistance() <= 30 && Squirrel.notDetected)
			return true;
		else
			return false;
	}
	
	public void action() {
		Squirrel.suppressed = false;
		// Record tachometer count to "retrace steps" so can rotate by correct number of degrees when returned to central branch
		Squirrel.tachoRotationA = Motor.A.getTachoCount();
		Squirrel.tachoRotationB = Motor.B.getTachoCount();
		Squirrel.tachoDistance = 0;
		Squirrel.notDetected = false;
		
		// Reset tachometer count
		Motor.A.resetTachoCount();
		Motor.B.resetTachoCount();
		
		// Travel towards detected object
		Squirrel.pilot.travel(300, true);
		
		while (Motor.A.isMoving()) {
			// If sees something close
			if (Squirrel.us.getDistance() <= 6) {
				// Stop moving
				Squirrel.pilot.stop();
				
				// Close gripper
				Motor.C.rotate(-Squirrel.rotateAmount);
				
				// Wait 3 seconds
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				// If ball is not red
				if (Squirrel.ls.readValue() < 46) {
					// Let go of ball
					Motor.C.rotate(Squirrel.rotateAmount);
					
					Squirrel.hasBall = false;
					Squirrel.returnToExplore = true;
				} else {
					Squirrel.hasBall = true;
					Squirrel.returnToExplore = false;
					Squirrel.returnHome = true;
				}
				
				break;
			} else
				Squirrel.returnToExplore = true;
		}

		// Record tachometer count to "retrace steps" so can reverse correct distance back to central branch
		Squirrel.tachoDistance = Motor.A.getTachoCount();
	}
	
	public void suppress() {
		
	}
}