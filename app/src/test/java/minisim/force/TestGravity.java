package minisim.force;

import minisim.simulation.Body;
import minisim.simulation.V2;
import minisim.simulation.force.Gravity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestGravity {
	private Gravity gravity;

	@BeforeEach
	public void setup() {
		gravity = new Gravity();
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

		gravity.accept(first, second);

		assertTrue(first.position.x > left);
		assertTrue(second.position.x < right);
		assertTrue(first.position.y < down);
		assertTrue(second.position.y > up);
	}
}
