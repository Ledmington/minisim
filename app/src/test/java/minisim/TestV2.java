package minisim;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class TestV2 {

	private final double EPSILON = 1e-12;

	@Test
	public void defaultIsOrigin() {
		V2 v = new V2();
		assertEquals(0, v.x(), EPSILON);
		assertEquals(0, v.y(), EPSILON);
	}

	@Test
	public void simpleAdd() {
		V2 v = new V2();
		V2 w = new V2(1, 1);
		V2 r = v.add(w);
		assertEquals(r.x(), w.x(), EPSILON);
		assertEquals(r.y(), w.y(), EPSILON);
	}

	@Test
	public void simpleSub() {
		V2 v = new V2();
		V2 w = new V2(1, 1);
		V2 r = v.sub(w);
		assertEquals(r.x(), -w.x(), EPSILON);
		assertEquals(r.y(), -w.y(), EPSILON);
	}

	@Test
	public void addSub() {
		V2 v = new V2();
		V2 w = new V2(1, 1);
		V2 r = v.add(w).sub(w);
		assertEquals(0, r.x(), EPSILON);
		assertEquals(0, r.y(), EPSILON);
	}

	@Test
	public void simpleMul() {
		V2 v = new V2(1, 2);
		V2 r = v.mul(2);
		assertEquals(2, r.x(), EPSILON);
		assertEquals(4, r.y(), EPSILON);
	}

	@Test
	public void simpleDiv() {
		V2 v = new V2(2, 4);
		V2 r = v.div(2);
		assertEquals(1, r.x(), EPSILON);
		assertEquals(2, r.y(), EPSILON);
	}

	@Test
	public void mulDiv() {
		V2 v = new V2(1, 1);
		V2 r = v.mul(2).div(2);
		assertEquals(1, r.x(), EPSILON);
		assertEquals(1, r.y(), EPSILON);
	}

	@Test
	public void moduloOfOriginIsZero() {
		V2 v = new V2();
		assertEquals(0, v.mod(), EPSILON);
	}

	@Test
	public void simpleModulo() {
		V2 v = new V2(5, 12);
		assertEquals(13, v.mod(), EPSILON);
	}

	@Test
	public void dotWithOriginIsZero() {
		V2 v = new V2();
		V2 w = new V2(2, 3);
		assertEquals(0, v.dot(w), EPSILON);
	}

	@Test
	public void normalDot() {
		V2 v = new V2(1, 2);
		V2 w = new V2(2, 3);
		assertEquals(8, v.dot(w), EPSILON);
	}

	@Test
	public void vectorNorm() {
		V2 v = new V2(0, 2);
		V2 n = v.norm();
		assertEquals(0, n.x(), EPSILON);
		assertEquals(1, n.y(), EPSILON);
	}

	@Test
	public void normalizedVectorModuloIsOne() {
		V2 v = new V2(1.7, 5.3);
		V2 n = v.norm();
		assertEquals(1, n.mod(), EPSILON);
	}

	@Test
	public void distanceFromOriginIsVectorModulo() {
		V2 v = new V2();
		V2 w = new V2(1, 2);
		assertEquals(w.dist(v), w.mod(), EPSILON);
	}

	@Test
	public void distanceTwoPoints() {
		V2 v = new V2(2, 1);
		V2 w = new V2(1, 1);
		assertEquals(1, w.dist(v), EPSILON);
	}

	@Test
	public void distanceSquaredFromOrigin() {
		V2 v = new V2();
		V2 w = new V2(1, 2);
		assertEquals(5, w.distsq(v), EPSILON);
	}

	@Test
	public void distanceSquaredTwoPoints() {
		V2 v = new V2(2, 1);
		V2 w = new V2(1, 1);
		assertEquals(1, w.distsq(v), EPSILON);
	}

	@Test
	public void vectorEqualsItself() {
		V2 v = new V2(3, 5);
		assertTrue(v.equals(v));
	}

	@Test
	public void vectorDoesNotEqualString() {
		V2 v = new V2(3, 5);
		assertFalse(v.equals("ciao"));
	}

	@Test
	public void twoVectorsWithSameCoordinatesAreTheSameVector() {
		V2 v = new V2(3, 5);
		V2 w = new V2(3, 5);
		assertFalse(v == w);
		assertTrue(v.equals(w));
	}
}
