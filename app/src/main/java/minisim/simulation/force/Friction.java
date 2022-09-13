package minisim.simulation.force;

import minisim.simulation.Body;

public class Friction implements UnaryForce {

	private final double constant;

	public Friction(final double constant) {
		this.constant = constant;
	}

	@Override
	public void accept(final Body body) {
		body.force.mul(constant);
	}
}
