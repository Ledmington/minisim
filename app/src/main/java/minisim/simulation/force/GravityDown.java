package minisim.simulation.force;

import minisim.simulation.Body;
import minisim.simulation.V2;

public class GravityDown implements UnaryForce {

	private final V2 vectorDown;

	public GravityDown(final double modulo) {
		this.vectorDown = new V2(0, modulo);
	}

	@Override
	public void accept(final Body body) {
		body.force.sub(vectorDown);
	}
}
