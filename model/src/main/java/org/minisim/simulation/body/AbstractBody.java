package org.minisim.simulation.body;

/**
 * This class exists only to allow to have two final methods
 * inside every Body.
 */
abstract sealed class AbstractBody implements Body permits FixedBody, MovableBody {

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
