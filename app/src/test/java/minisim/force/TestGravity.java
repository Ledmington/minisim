package minisim.force;

import minisim.simulation.Body;
import minisim.simulation.V2;
import minisim.simulation.force.Gravity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestGravity {
	private Gravity gravity;

	@BeforeEach
	public void setup() {
		gravity = new Gravity();
	}

	@Test
	public void forceShouldBeEqual() {
		final Body first = new Body(new V2(3, 5), V2.origin(), 1, 1);
		final Body second = new Body(new V2(-7, 9), V2.origin(), 1, 1);
		gravity.accept(first, second);
		assertEquals(first.force.mod(), second.force.mod(), 1e-12);
		assertEquals(first.force.x, -second.force.x, 1e-12);
		assertEquals(first.force.y, -second.force.y, 1e-12);
	}

	@Test
	public void forceShouldBeEqualWithDifferentMasses() {
		final Body first = new Body(new V2(3, 5), V2.origin(), 2, 1);
		final Body second = new Body(new V2(-7, 9), V2.origin(), 3, 1);
		gravity.accept(first, second);
		assertEquals(first.force.mod(), second.force.mod(), 1e-12);
		assertEquals(first.force.x, -second.force.x, 1e-12);
		assertEquals(first.force.y, -second.force.y, 1e-12);
	}

	@Test
	public void forceShouldBeEqualWithDifferentRadii() {
		final Body first = new Body(new V2(3, 5), V2.origin(), 1, 2);
		final Body second = new Body(new V2(-7, 9), V2.origin(), 1, 3);
		gravity.accept(first, second);
		assertEquals(first.force.mod(), second.force.mod(), 1e-12);
		assertEquals(first.force.x, -second.force.x, 1e-12);
		assertEquals(first.force.y, -second.force.y, 1e-12);
	}

	@Test
	public void distanceShouldDecrease() {
		final Body first = new Body(new V2(3, 5), V2.origin(), 1, 1);
		final Body second = new Body(new V2(-7, 9), V2.origin(), 1, 1);

		final double initialDistance = first.dist(second);

		gravity.accept(first, second);
		first.applyForce();
		second.applyForce();

		final double finalDistance = first.dist(second);

		assertTrue(finalDistance < initialDistance);
	}

	@Test
	public void heavierObjectsShouldProduceStrongerForce() {
		final Body left = new Body(new V2(-6, 5), V2.origin(), 1, 1);
		final Body middle = new Body(new V2(0, 5), V2.origin(), 1, 1);
		final Body rightAndHeavy = new Body(new V2(6, 5), V2.origin(), 3, 1);

		gravity.accept(left, middle);

		final double lighterForce = middle.force.mod();

		middle.force = V2.origin();

		gravity.accept(middle, rightAndHeavy);

		final double heavyForce = middle.force.mod();

		assertEquals(lighterForce * rightAndHeavy.mass, heavyForce, 1e-12);
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
		first.applyForce();
		second.applyForce();

		assertTrue(first.position.x > left);
		assertTrue(second.position.x < right);
		assertTrue(first.position.y < down);
		assertTrue(second.position.y > up);
	}
}
