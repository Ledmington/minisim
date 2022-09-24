package org.minisim.simulation.border;

import org.minisim.simulation.V2;
import org.minisim.simulation.body.Body;

/**
 * By using SolidBorders, bodies will stick on simulation's borders and bounce
 * back.
 */
public final class SolidBorders extends Borders {

    public SolidBorders(final double width, final double height) {
        super(width, height);
    }

    @Override
    public void accept(final Body b) {
        double newPosX = b.position.x();
        double newPosY = b.position.y();
        double newSpeedX = b.speed.x();
        double newSpeedY = b.speed.y();
        if (b.position.x() < LEFT_BORDER) {
            newPosX = LEFT_BORDER;
            newSpeedX = -b.speed.x();
        } else if (b.position.x() > RIGHT_BORDER) {
            newPosX = RIGHT_BORDER;
            newSpeedX = -b.speed.x();
        }

        if (b.position.y() < BOTTOM_BORDER) {
            newPosY = BOTTOM_BORDER;
            newSpeedY = -b.speed.y();
        } else if (b.position.y() > UP_BORDER) {
            newPosY = UP_BORDER;
            newSpeedY = -b.speed.y();
        }

        b.position = new V2(newPosX, newPosY);
        b.speed = new V2(newSpeedX, newSpeedY);
    }
}
