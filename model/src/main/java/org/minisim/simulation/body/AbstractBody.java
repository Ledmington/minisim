package org.minisim.simulation.body;

import org.minisim.simulation.V2;

/**
 * This class exists only to allow to have two final methods
 * inside every Body.
 */
abstract sealed class AbstractBody implements Body permits FixedBody, MovableBody {

    public final void setPosition(final double x, final double y) {
        setPosition(V2.of(x, y));
    }

    public final void setSpeed(final double x, final double y) {
        setSpeed(V2.of(x, y));
    }

    public final boolean collidesWith(final Body other) {
        final double Rsum = radius() + other.radius();
        if (Math.abs(position().x() - other.position().x()) > Rsum) {
            return false;
        }
        if (Math.abs(position().y() - other.position().y()) > Rsum) {
            return false;
        }
        // return position.distsq(other.position) < Rsum * Rsum; // faster way (maybe):
        // doesn't use a sqrt
        return dist(other) < Rsum; // <-- correct way
    }

    public final double dist(final Body other) {
        return position().dist(other.position());
    }
}
