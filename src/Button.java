import lejos.robotics.subsumption.Behavior;

public class Button implements Behavior {
	public boolean takeControl() {
		// If touch sensor pressed
		if (Squirrel.ts.isPressed())
			return true;
		else
			return false;
	}
	
	public void action() {
		// If robot at home
		if (Squirrel.atHome) {
			Squirrel.turnedLeft = false;
			Squirrel.notDetected = true;
			Squirrel.hasBall = false;
			Squirrel.atHome = false;
		}
	}
	
	public void suppress() {
		
	}
}