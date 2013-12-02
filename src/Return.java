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
		Explore.returnToMainPath();
		
		// Travel home
		Squirrel.pilot.travel(-Squirrel.middleDistanceTotal);
		
		// Let go of ball
		Motor.C.rotate(Squirrel.rotateAmount);
		
		Squirrel.returnHome = false;
	}
	
	public void suppress() {
		
	}
}