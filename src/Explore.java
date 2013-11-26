import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;

public class Explore implements Behavior {
	static int middleDistanceStep = 200;
	static int distanceToTravel = 20;
	static int distanceToGrab = 10;
	
	public boolean takeControl() {
		return true;
	}
	
	public void action() {
		// while (true) {
			// If need to keep looking for red ball
			if (Squirrel.returnToExplore) {
				returnToExplore();
			}
			
			// If not found red ball
			if (!Squirrel.hasBall) {
				// Travel further down central branch
				Squirrel.pilot.travel(middleDistanceStep);
				
				Squirrel.middleDistanceTotal = Squirrel.middleDistanceTotal + middleDistanceStep;
				
				// Turn left
				Squirrel.pilot.rotate(-90);
				
				Squirrel.turnedLeft = true;
				
				// If sees something nearby
				if (Squirrel.us.getDistance() < middleDistanceStep) {
					investigate();
					
					// break;
				} else {
					checkRight();
				}
			}
		// }
	}
	
	private void checkRight() {
		// Turn right
		Squirrel.pilot.rotate(180);
		
		Squirrel.turnedLeft = false;
		
		// If sees something nearby
		if (Squirrel.us.getDistance() < middleDistanceStep) {
			investigate();
			
			// break;
		} else {
			// Turn left
			Squirrel.pilot.rotate(-90);
		}
	}
	
	private void investigate() {
		// Reset recorded distance travelled
		Motor.A.resetTachoCount();
		
		Squirrel.branchDistance = 0;
		
		for (int i = 0; i < middleDistanceStep / distanceToTravel; i++) {
			// Go forwards in increments
			Squirrel.pilot.travel(distanceToTravel);
			
			Squirrel.branchDistance = Squirrel.branchDistance + distanceToTravel;
			
			// If sees something close
			if (Squirrel.us.getDistance() <= distanceToGrab) {
				Squirrel.locatedBall = true;
				
				break;
			} else
				Squirrel.locatedBall = false;
		}
		
		// If hasn't found red ball
		if (!Squirrel.locatedBall)
			Squirrel.returnToExplore = true;
		
		// If had turned right
		if (!Squirrel.turnedLeft)
			returnToExplore();
	}
	
	void returnToExplore() {
		// Go back distance of branch
		Squirrel.pilot.travel(-Squirrel.branchDistance);
		
		// Turn left/right depending on direction turned earlier
		if (Squirrel.turnedLeft)
			checkRight();
		else
			Squirrel.pilot.rotate(-90);
		
		Squirrel.returnToExplore = false;
	}

	public void suppress() {
		// Stop moving
		Squirrel.pilot.stop();
	}
}