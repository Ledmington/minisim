package org.minisim.simulation.body;

import org.minisim.simulation.V2;

final class MovableBody extends AbstractBody {

    private V2 position;
    private V2 speed;
    private V2 acceleration;
    private V2 force;
    private final double mass;
    private final double radius;

    public MovableBody(
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

    public void applyForce() {
        acceleration = force.div(mass); // * DT; // TODO fix later
        speed = speed.add(acceleration);
        position = position.add(speed);

        force = V2.origin();
    }
}
