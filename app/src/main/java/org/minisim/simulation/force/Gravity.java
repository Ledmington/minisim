package org.minisim.simulation.force;

import org.minisim.simulation.V2;
import org.minisim.simulation.body.Body;

public final class Gravity implements Force {

    public static final double NEWTON_GRAVITY = 6.6743e-11;

    private final double constant;

    public Gravity(final double constant) {
        this.constant = constant;
    }

    public Gravity() {
        this(NEWTON_GRAVITY);
    }

    @Override
    public void accept(final Body first, final Body second) {
        // TODO: can refactor the squared distance with a method inside V2
        final double distance = first.dist(second);
        final double force = constant * first.mass() * second.mass() / (distance * distance);
        final V2 forceDirection = first.position().sub(second.position()).norm();

        final V2 diff = forceDirection.mul(force);

        first.setForce(first.force().sub(diff));
        second.setForce(second.force().add(diff));
    }
}
