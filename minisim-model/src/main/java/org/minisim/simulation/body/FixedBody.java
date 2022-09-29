package org.minisim.simulation.body;

import org.minisim.simulation.V2;

/**
 * Extension of {@link Body} which does nothing when trying to modify
 * its position, speed or force.
 */
public final class FixedBody extends Body {

    public FixedBody(final V2 position, final double mass, final double radius) {
        super(position, V2.origin(), V2.origin(), V2.origin(), mass, radius);
    }

    @Override
    public void setPosition(final V2 newPosition) {
        // intentionally left empty
    }

    @Override
    public void setSpeed(final V2 newSpeed) {
        // intentionally left empty
    }

    @Override
    public void setForce(final V2 newForce) {
        // intentionally left empty
    }

    @Override
    public void applyForce() {
        // intentionally left empty
    }
}
