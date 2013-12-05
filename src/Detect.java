import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;

public class Detect implements Behavior {
	public boolean takeControl() { // Set locatedBall to false near end of action method
		// If sees something nearby
		if (Squirrel.us.getDistance() <= 20 && Squirrel.notDetected)
			return true;
		else
			return false;
	}
	
	public void action() {
		Squirrel.suppressed = false;
		Squirrel.tachoRotationA = Motor.A.getTachoCount();
		Squirrel.tachoRotationB = Motor.B.getTachoCount();
		// Squirrel.angleRotated = Squirrel.pilot.getAngleIncrement();
		Squirrel.tachoDistance = 0;
		Squirrel.notDetected = false;
		
		Motor.A.resetTachoCount();
		Motor.B.resetTachoCount();
		
		Squirrel.pilot.travel(Squirrel.middleDistanceStep, true);
		
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
		
		Squirrel.tachoDistance = Motor.A.getTachoCount();
		// Squirrel.distanceMoved = Squirrel.pilot.getMovementIncrement();
	}
	
	public void suppress() {
		
	}
}