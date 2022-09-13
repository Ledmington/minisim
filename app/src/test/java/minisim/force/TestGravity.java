package minisim.force;

import minisim.simulation.Body;
import minisim.simulation.Simulation;
import minisim.simulation.V2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestGravity {
	private Simulation sim;

	@BeforeEach
	public void setup() {
		sim = Simulation.builder().width(100).height(100).gravity(1e-8).friction(0.99).solidBorders().build();
	}

	@Test
	public void twoBodies() {
		// the origin (0,0) is in the upper left corner
		// the x-axis is placed as usual
		// the y-axis points down (so to go "up", you need to decrease the y coordinate)
		final double down = 40;
		final double up = 30;
		final double left = 20;
		final double right = 30;

		final Body first = new Body(new V2(left, down), new V2(0, 0), 1, 1);
		final Body second = new Body(new V2(right, up), new V2(0, 0), 1, 1);

		sim.addBody(first);
		sim.addBody(second);

		sim.update();

		System.out.println(first.position);
		System.out.println(second.position);

		assertTrue(first.position.x > left);
		assertTrue(second.position.x < right);
		assertTrue(first.position.y < down);
		assertTrue(second.position.y > up);
	}
}
