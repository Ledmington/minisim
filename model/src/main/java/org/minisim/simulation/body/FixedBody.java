package org.minisim.simulation.body;

import org.minisim.simulation.V2;

final class FixedBody extends AbstractBody {

    private final V2 position;
    private final double mass;
    private final double radius;

    public FixedBody(final V2 position, final double mass, final double radius) {
        // TODO: null checks and positive mass and radius
        this.position = position;
        this.mass = mass;
        this.radius = radius;
    }

    @Override
    public V2 position() {
        return position;
    }

    @Override
    public V2 speed() {
        return V2.origin();
    }

    @Override
    public V2 acceleration() {
        return V2.origin();
    }

    @Override
    public V2 force() {
        return V2.origin();
    }

    @Override
    public double mass() {
        return mass;
    }

    @Override
    public double radius() {
        return radius;
    }

    @Override
    public void applyForce() {
        // intentionally left blank
    }
}
