import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;

public class Detect implements Behavior {
	public boolean takeControl() { // Set locatedBall to false near end of action method
		// If sees something nearby
		if (Squirrel.locatedBall)
			return true;
		else
			return false;
	}
	
	public void action() {
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
			Squirrel.searching = false;
		}
		
		Squirrel.locatedBall = false;
	}
	
	public void suppress() {
		
	}
}