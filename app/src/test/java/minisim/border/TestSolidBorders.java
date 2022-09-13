package minisim.border;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import minisim.simulation.border.Borders;
import minisim.simulation.border.SolidBorders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import minisim.simulation.Body;
import minisim.simulation.V2;

public class TestSolidBorders {

	private Borders sb;

	@BeforeEach
	public void setup() {
		sb = new SolidBorders(10, 10);
	}

	@Test
	public void illegalWidth() {
		assertThrows(IllegalArgumentException.class, () -> new SolidBorders(0, 10));
		assertThrows(IllegalArgumentException.class, () -> new SolidBorders(-1, 10));
	}

	@Test
	public void illegalHeight() {
		assertThrows(IllegalArgumentException.class, () -> new SolidBorders(10, 0));
		assertThrows(IllegalArgumentException.class, () -> new SolidBorders(10, -1));
	}

	@Test
	public void noChangesIfInside() {
		Body b = new Body(new V2(1, 1), V2.ORIGIN, 1, 1);
		sb.apply(b);
		assertEquals(b.position, new V2(1, 1));
	}

	@Test
	public void outOnRight() {
		Body b = new Body(new V2(11, 1), V2.ORIGIN, 1, 1);
		sb.apply(b);
		assertEquals(b.position, new V2(10, 1));
	}

	@Test
	public void outOnLeft() {
		Body b = new Body(new V2(-1, 1), V2.ORIGIN, 1, 1);
		sb.apply(b);
		assertEquals(b.position, new V2(0, 1));
	}

	@Test
	public void outOnTop() {
		Body b = new Body(new V2(1, -1), V2.ORIGIN, 1, 1);
		sb.apply(b);
		assertEquals(b.position, new V2(1, 0));
	}

	@Test
	public void outOnBottom() {
		Body b = new Body(new V2(1, 11), V2.ORIGIN, 1, 1);
		sb.apply(b);
		assertEquals(b.position, new V2(1, 10));
	}

	@Test
	public void bottomRightCorner() {
		Body b = new Body(new V2(11, 11), V2.ORIGIN, 1, 1);
		sb.apply(b);
		assertEquals(b.position, new V2(10, 10));
	}

	@Test
	public void topLeftCorner() {
		Body b = new Body(new V2(-1, -1), V2.ORIGIN, 1, 1);
		sb.apply(b);
		assertEquals(b.position, new V2(0, 0));
	}
}
