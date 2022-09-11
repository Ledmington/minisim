package minisim.border;

import minisim.Body;

/**
 * By using CyclicBorders, each body that would go otherwise go out of bounds,
 * will come back on the other side of the domain (Pac-Man effect).
 */
public class CyclicBorders extends Borders {

	public CyclicBorders(final double width, final double height) {
		super(width, height);
	}

	@Override
	public void apply(final Body b) {
		b.position.x = (w + b.position.x) % w;
		b.position.y = (h + b.position.y) % h;
	}
}
