package minisim.border;

import minisim.Body;
import minisim.V2;

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
		b.setPosition(new V2((w + b.position().x) % w, (h + b.position().y) % h));
	}
}
