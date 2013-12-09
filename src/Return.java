import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;

public class Return implements Behavior {
	public boolean takeControl() {
		// If robot to return home
		if (Squirrel.returnHome)
			return true;
		else
			return false;
	}
	
	public void action() {
		Squirrel.returnToMainPath();
		
		// Travel home
		Squirrel.pilot.travel(-Squirrel.middleDistanceTotal);
		
		// Let go of ball
		if (Squirrel.hasBall)
			Motor.C.rotate(Squirrel.rotateAmount);
		
		Squirrel.returnHome = false;
		Squirrel.atHome = true;
	}
	
	public void suppress() {
		
	}
}