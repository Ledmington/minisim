package org.minisim.simulation.border;

import org.minisim.simulation.V2;
import org.minisim.simulation.body.Body;

/**
 * By using CyclicBorders, each body that would otherwise go out of bounds,
 * will come back on the other side of the domain (Pac-Man effect).
 */
public final class CyclicBorders extends Borders {

    public CyclicBorders(final double width, final double height) {
        super(width, height);
    }

    @Override
    public void accept(final Body b) {
        double x = b.position().x();
        while (x < 0.0) {
            x += RIGHT_BORDER;
        }
        while (x > RIGHT_BORDER) {
            x -= RIGHT_BORDER;
        }

        double y = b.position().y();
        while (y < 0.0) {
            y += UP_BORDER;
        }
        while (y > UP_BORDER) {
            y -= UP_BORDER;
        }

        b.setPosition(new V2(x, y));
    }
}
