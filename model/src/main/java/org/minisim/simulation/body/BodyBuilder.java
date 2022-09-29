package org.minisim.simulation.body;

import org.minisim.simulation.V2;

public final class BodyBuilder {

    private V2 position = V2.origin();
    private V2 speed = V2.origin();
    private V2 acceleration = V2.origin();
    private V2 force = V2.origin();
    private double mass = 1;
    private double radius = 1;
    private boolean fixed = false;

    public BodyBuilder position(final V2 newPosition) {
        this.position = newPosition;
        return this;
    }

    public BodyBuilder position(final double x, final double y) {
        return position(V2.of(x, y));
    }

    public BodyBuilder speed(final double x, final double y) {
        this.speed = V2.of(x, y);
        return this;
    }

    public BodyBuilder acceleration(final double x, final double y) {
        this.acceleration = V2.of(x, y);
        return this;
    }

    public BodyBuilder force(final V2 newForce) {
        this.force = newForce;
        return this;
    }

    public BodyBuilder force(final double x, final double y) {
        this.force = V2.of(x, y);
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

    public BodyBuilder fixed() {
        this.fixed = true;
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
        if (fixed) {
            return new FixedBody(position, mass, radius);
        } else {
            return new MovableBody(position, speed, acceleration, force, mass, radius);
        }
    }
}
