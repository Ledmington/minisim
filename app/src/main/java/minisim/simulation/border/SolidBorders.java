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
	public void accept(final Body b) {
		if (b.position.x < LEFT_BORDER) {
			b.position.x = LEFT_BORDER;
			b.speed.x = -b.speed.x;
		} else if (b.position.x > RIGHT_BORDER) {
			b.position.x = RIGHT_BORDER;
			b.speed.x = -b.speed.x;
		}

		if (b.position.y < BOTTOM_BORDER) {
			b.position.y = BOTTOM_BORDER;
			b.speed.y = -b.speed.y;
		} else if (b.position.y > UP_BORDER) {
			b.position.y = UP_BORDER;
			b.speed.y = -b.speed.y;
		}
	}
}
