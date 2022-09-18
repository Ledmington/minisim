package minisim.simulation.border;

import minisim.simulation.Body;

/**
 * By using CyclicBorders, each body that would go otherwise go out of bounds,
 * will come back on the other side of the domain (Pac-Man effect).
 */
public class CyclicBorders extends Borders {

    public CyclicBorders(final double width, final double height) {
        super(width, height);
    }

    @Override
    public void accept(final Body b) {
        b.position.x = (RIGHT_BORDER + b.position.x) % RIGHT_BORDER;
        b.position.y = (UP_BORDER + b.position.y) % UP_BORDER;
    }
}
