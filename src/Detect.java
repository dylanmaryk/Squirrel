import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;

public class Detect implements Behavior {
	static int rotateAmount = 60;
	
	public boolean takeControl() {
		if (Squirrel.us.getDistance() < 20)
			return true;
		else
			return false;
	}
	
	public void action() {
		Motor.C.rotate(rotateAmount);
		
		if (Squirrel.ls.readValue() < 51) {
			Motor.C.rotate(-rotateAmount);
		}
	}
	
	public void suppress() {
		
	}
}