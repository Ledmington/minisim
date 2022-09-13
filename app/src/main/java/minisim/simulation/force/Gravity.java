package minisim.simulation.force;

import minisim.simulation.Body;

public class Gravity implements Force {
	@Override
	public void accept(final Body first, final Body second) {
		System.out.println("Applying gravity on bodies " + first + " and " + second);
	}
}
