package org.minisim.simulation.body;

import org.minisim.simulation.V2;

public final class BodyBuilder {

    private V2 position = V2.origin();
    private V2 speed = V2.origin();
    private V2 acceleration = V2.origin();
    private V2 force = V2.origin();
    private double mass = 1;
    private double radius = 1;

    public BodyBuilder position(final V2 position) {
        this.position = position;
        return this;
    }

    public BodyBuilder speed(final V2 speed) {
        this.speed = speed;
        return this;
    }

    public BodyBuilder acceleration(final V2 acceleration) {
        this.acceleration = acceleration;
        return this;
    }

    public BodyBuilder force(final V2 force) {
        this.force = force;
        return this;
    }

    public BodyBuilder mass(final double mass) {
        this.mass = mass;
        return this;
    }

    public BodyBuilder radius(final double radius) {
        this.radius = radius;
        return this;
    }

    /**
     * Builds a new Body with the given parameters. If not set, they will default
     * to the following values:
     * - position = (0,0)
     * - speed = (0,0)
     * - acceleration = (0,0)
     * - force = (0,0)
     * - mass = 1
     * - radius = 1
     * @return
     *      A new Body with the given parameters.
     */
    public Body build() {
        return new Body(position, speed, acceleration, force, mass, radius);
    }
}
