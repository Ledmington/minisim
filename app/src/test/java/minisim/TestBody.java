package minisim;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestBody {

	public final double EPSILON = 1e-12;

	@Test
	public void defaultBody() {
		Body b = new Body();
		assertEquals(0, b.position.x, EPSILON);
		assertEquals(0, b.position.y, EPSILON);
		assertEquals(0, b.speed.x, EPSILON);
		assertEquals(0, b.speed.y, EPSILON);
		assertEquals(1, b.mass, EPSILON);
		assertEquals(1, b.radius, EPSILON);
	}

	@Test
	public void illegalMass() {
		assertThrows(IllegalArgumentException.class, () -> new Body(V2.ORIGIN, V2.ORIGIN, 0, 1));
		assertThrows(IllegalArgumentException.class, () -> new Body(V2.ORIGIN, V2.ORIGIN, -1, 1));
	}

	@Test
	public void illegalRadius() {
		assertThrows(IllegalArgumentException.class, () -> new Body(V2.ORIGIN, V2.ORIGIN, 1, 0));
		assertThrows(IllegalArgumentException.class, () -> new Body(V2.ORIGIN, V2.ORIGIN, 1, -1));
	}

	@Test
	public void twoBodyCollision() {
		Body a = new Body(new V2(0, 0), new V2(0, 0), 1, 1);
		Body b = new Body(new V2(1, 1), new V2(0, 0), 1, 1);
		assertTrue(a.collidesWith(b));
	}

	@Test
	public void twoBodyNoCollision() {
		Body a = new Body(new V2(0, 0), new V2(0, 0), 1, 1);
		Body b = new Body(new V2(2, 2), new V2(0, 0), 1, 1);
		assertTrue(!a.collidesWith(b));
	}
}