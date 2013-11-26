import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;

public class Return implements Behavior {
	public boolean takeControl() {
		if (Squirrel.returnHome)
			return true;
		else
			return false;
	}
	
	public void action() {
		// Turn around and go back to main branch
		Squirrel.pilot.rotate(180);
		Squirrel.pilot.travel(Squirrel.branchDistance);
		
		// Turn left/right depending on direction turned earlier
		if (Squirrel.turnedLeft)
			Squirrel.pilot.rotate(90);
		else
			Squirrel.pilot.rotate(-90);
		
		// Travel home and turn around
		Squirrel.pilot.travel(Squirrel.middleDistanceTotal);
		Squirrel.pilot.rotate(180);
		
		// Let go of ball
		Motor.C.rotate(Squirrel.rotateAmount);
		
		Squirrel.returnHome = false;
	}
	
	public void suppress() {
		
	}
}