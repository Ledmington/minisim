package org.minisim.simulation.border;

import java.util.function.Consumer;
import org.minisim.simulation.body.Body;

public abstract class Borders implements Consumer<Body> {

    public final double LEFT_BORDER = 0;
    public final double RIGHT_BORDER;
    public final double BOTTOM_BORDER = 0;
    public final double UP_BORDER;

    public Borders(final double width, final double height) {
        if (width <= LEFT_BORDER || height <= BOTTOM_BORDER) {
            throw new IllegalArgumentException("Width and height must be strictly positive");
        }
        RIGHT_BORDER = width;
        UP_BORDER = height;
    }
}
