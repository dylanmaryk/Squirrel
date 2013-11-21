import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;

public class Detect implements Behavior {
	static int rotateAmount = 70;
	
	public boolean takeControl() {
		if (Squirrel.us.getDistance() < 20)
			return true;
		else
			return false;
	}
	
	public void action() {
		Motor.C.rotate(-rotateAmount);
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if (Squirrel.ls.readValue() < 51) {
			Motor.C.rotate(rotateAmount);
			
			Squirrel.hasBall = false;
			Squirrel.returnToExplore = true;
		} else {
			Squirrel.hasBall = true;
			Squirrel.returnToExplore = false;
		}
	}
	
	public void suppress() {
		
	}
}