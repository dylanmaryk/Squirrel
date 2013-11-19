import lejos.robotics.subsumption.Behavior;

public class Explore implements Behavior {
	int middleDistanceStep = 200;
	int middleDistanceTotal = 0; // Might need to declare in "Squirrel" as might reset each time behaviour run
	int branchDistance = 0; // Might need to declare in "Squirrel" as might reset each time behaviour run
	
	public boolean takeControl() {
		
	}
	
	public void action() {
		while (true) {
			Squirrel.pilot.travel(middleDistanceStep);
			
			middleDistanceTotal = middleDistanceTotal + middleDistanceStep;
			
			Squirrel.pilot.rotate(-90);
			
			if (Squirrel.us.getDistance() != 255) {
				investigate();
			} else {
				Squirrel.pilot.rotate(180);
				
				if (Squirrel.us.getDistance() != 255) {
					investigate();
				} else {
					Squirrel.pilot.rotate(-90);
				}
			}
		}
	}
	
	private void investigate() {
		// Investigate... Reset tacho and start tracking
	}

	public void suppress() {
		
	}
}