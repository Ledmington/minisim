package minisim.simulation.border;

import minisim.simulation.Body;

/**
 * By using SolidBorders, bodies will stick on simulation's borders and bounce
 * back.
 */
public class SolidBorders extends Borders {

	public SolidBorders(final double width, final double height) {
		super(width, height);
	}

	@Override
	public void apply(final Body b) {
		if (b.position.x < 0) {
			b.position.x = 0;
			b.speed.x *= -1;
		} else if (b.position.x > w) {
			b.position.x = w;
			b.speed.x *= -1;
		}

		if (b.position.y < 0) {
			b.position.y = 0;
			b.speed.y *= -1;
		} else if (b.position.y > h) {
			b.position.y = h;
			b.speed.y *= -1;
		}
	}
}
