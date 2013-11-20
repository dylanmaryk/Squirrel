import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;

public class Explore implements Behavior {
	static int middleDistanceStep = 200;
	static int middleDistanceTotal = 0; // Might need to declare in "Squirrel" as might reset each time behaviour run
	static int branchDistance = 0; // Might need to declare in "Squirrel" as might reset each time behaviour run
	
	public boolean takeControl() {
		return true;
	}
	
	public void action() {
		while (true) {
			Squirrel.pilot.travel(middleDistanceStep);
			
			middleDistanceTotal = middleDistanceTotal + middleDistanceStep;
			
			Squirrel.pilot.rotate(-90);
			
			if (Squirrel.us.getDistance() < middleDistanceStep) {
				investigate();
				
				break;
			} else {
				Squirrel.pilot.rotate(180);
				
				if (Squirrel.us.getDistance() < middleDistanceStep) {
					investigate();
					
					break;
				} else {
					Squirrel.pilot.rotate(-90);
				}
			}
		}
	}
	
	private void investigate() {
		Motor.A.resetTachoCount();
		
		branchDistance = 0;
		
		Squirrel.pilot.forward();
	}

	public void suppress() {
		Squirrel.pilot.stop();
	}
}