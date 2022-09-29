package org.minisim.simulation.body;

import org.minisim.simulation.V2;

public sealed class Body permits FixedBody {

    public static BodyBuilder builder() {
        return new BodyBuilder();
    }

    private V2 position;
    private V2 speed;
    private V2 acceleration;
    private V2 force;
    private final double mass;
    private final double radius;

    public Body(
            final V2 position,
            final V2 speed,
            final V2 acceleration,
            final V2 force,
            final double mass,
            final double radius) {
        if (mass <= 0.0) {
            throw new IllegalArgumentException("Mass can't be negative or zero");
        }
        if (radius <= 0.0) {
            throw new IllegalArgumentException("Radius can't be negative or zero");
        }

        this.mass = mass;
        this.radius = radius;
        this.position = position;
        this.speed = speed;
        this.acceleration = acceleration;
        this.force = force;
    }

    public Body(final V2 pos, final V2 speed, final double mass, final double radius) {
        this(pos, speed, V2.origin(), V2.origin(), mass, radius);
    }

    public Body() {
        this(new V2(0.0, 0.0), new V2(0.0, 0.0), 1.0, 1.0);
    }

    public double mass() {
        return mass;
    }

    public double radius() {
        return radius;
    }

    public V2 position() {
        return position;
    }

    public void setPosition(final V2 newPosition) {
        position = newPosition;
    }

    public void setPosition(final double x, final double y) {
        position = V2.of(x, y);
    }

    public V2 speed() {
        return speed;
    }

    public void setSpeed(final double x, final double y) {
        speed = V2.of(x, y);
    }

    public V2 acceleration() {
        return acceleration;
    }

    public V2 force() {
        return force;
    }

    public void setForce(final V2 newForce) {
        force = newForce;
    }

    public double dist(final Body other) {
        return position.dist(other.position);
    }

    public void applyForce() {
        acceleration = force.div(mass); // * DT; // TODO fix later
        speed = speed.add(acceleration);
        position = position.add(speed);

        force = V2.origin();
    }

    public boolean collidesWith(final Body other) {
        final double Rsum = radius + other.radius;
        if (Math.abs(position.x() - other.position.x()) > Rsum) {
            return false;
        }
        if (Math.abs(position.y() - other.position.y()) > Rsum) {
            return false;
        }
        // return position.distsq(other.position) < Rsum * Rsum; // faster way (maybe):
        // doesn't use a sqrt
        return dist(other) < Rsum; // <-- correct way
    }
}
