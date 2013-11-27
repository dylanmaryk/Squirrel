import lejos.robotics.subsumption.Behavior;

public class Scan implements Behavior {
	public boolean takeControl() {
		if (Squirrel.us.getDistance() < Squirrel.middleDistanceStep / 4 && Squirrel.searching)
			return true;
		else
			return false;
	}
	
	public void action() {
		Explore.investigate();
	}
	
	public void suppress() {
		
	}
}