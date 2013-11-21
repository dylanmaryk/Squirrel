import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;

public class Explore implements Behavior {
	static int middleDistanceStep = 200;
	static int middleDistanceTotal = 0; // Might need to declare in "Squirrel" as might reset each time behaviour run
	static int branchDistance = 0; // Might need to declare in "Squirrel" as might reset each time behaviour run
	static boolean turnedLeft;
	
	public boolean takeControl() {
		return true;
	}
	
	public void action() {
		while (true) {
			if (Squirrel.returnToExplore) {
				Squirrel.pilot.travel(-branchDistance);
				
				if (turnedLeft)
					Squirrel.pilot.rotate(90);
				else
					Squirrel.pilot.rotate(-90);
			}
			
			if (!(Squirrel.hasBall)) {
				Squirrel.pilot.travel(middleDistanceStep);
				
				middleDistanceTotal = middleDistanceTotal + middleDistanceStep;
				
				Squirrel.pilot.rotate(-90);
				
				turnedLeft = true;
				
				if (Squirrel.us.getDistance() < middleDistanceStep) {
					investigate();
					
					break;
				} else {
					Squirrel.pilot.rotate(180);
					
					turnedLeft = false;
					
					if (Squirrel.us.getDistance() < middleDistanceStep) {
						investigate();
						
						break;
					} else {
						Squirrel.pilot.rotate(-90);
					}
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
		
		branchDistance = Motor.A.getTachoCount();
		
		LCD.refresh();
		LCD.drawInt(branchDistance, 0, 0);
	}
}