package minisim.simulation.force;

import minisim.simulation.Body;
import minisim.simulation.V2;

public class Gravity implements Force {

	public static final double NEWTON_GRAVITY = 6.6743e-11;

	private final double constant;

	public Gravity(final double constant) {
		this.constant = constant;
	}

	public Gravity() {
		this(NEWTON_GRAVITY);
	}

	@Override
	public void accept(final Body first, final Body second) {
		System.out.println("Applying gravity on bodies " + first + " and " + second);
		final V2 diff = first.position.sub(second.position);
		final double distance = first.dist(second);
		final double force = constant * first.mass * second.mass / (distance * distance);
		first.force = first.force.sub(diff.mul(force));
		second.force = second.force.sub(diff.mul(force));
	}
}
