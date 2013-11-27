import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;

public class Explore implements Behavior {
	public boolean takeControl() {
		return true;
	}
	
	public void action() {
		// If need to keep looking for red ball
		if (Squirrel.returnToExplore) {
			returnToExplore();
		}
		
		// If not found red ball
		if (!Squirrel.hasBall) {
			// Travel further down central branch
			Squirrel.pilot.travel(Squirrel.middleDistanceStep);
			
			Squirrel.middleDistanceTotal = Squirrel.middleDistanceTotal + Squirrel.middleDistanceStep;
			
			Squirrel.pilot.reset();
			// Turn left
			Squirrel.pilot.rotate(-90);
			
			Squirrel.turnedLeft = true;
			
			checkRight();
		}
	}
	
	public static void checkRight() {
		Squirrel.pilot.reset();
		// Turn right
		Squirrel.pilot.rotate(90);
		
		Squirrel.turnedLeft = false;
		
		Squirrel.pilot.reset();
		Squirrel.pilot.rotate(-90);
	}
	
	public static void investigate() {
		Squirrel.branchDistance = 0;
		Squirrel.tachoRotated = Motor.A.getTachoCount();
		
		for (int i = 0; i < Squirrel.middleDistanceStep / Squirrel.distanceToTravel; i++) {
			// Go forwards in increments
			Squirrel.pilot.travel(Squirrel.distanceToTravel);
			
			Squirrel.branchDistance = Squirrel.branchDistance + Squirrel.distanceToTravel;
			
			// If sees something close
			if (Squirrel.us.getDistance() <= Squirrel.distanceToGrab) {
				Squirrel.locatedBall = true;
				
				break;
			} else
				Squirrel.locatedBall = false;
		}
		
		// If hasn't found red ball
		if (!Squirrel.locatedBall)
			Squirrel.returnToExplore = true;
	}
	
	public static void returnToExplore() {
		// Go back distance of branch
		Squirrel.pilot.travel(-Squirrel.branchDistance);
		
		Motor.A.rotate(-Squirrel.tachoRotated, true);
		Motor.B.rotate(Squirrel.tachoRotated, true);
		
		// If had turned left
		if (Squirrel.turnedLeft)
			checkRight();
		
		Squirrel.returnToExplore = false;
	}

	public void suppress() {
		// Stop moving
		Squirrel.pilot.stop();
	}
}