package org.minisim.simulation.force;

import org.minisim.simulation.body.Body;

public final class Friction implements UnaryForce {

    private final double constant;

    public Friction(final double constant) {
        if (constant < 0 || constant > 1) {
            throw new IllegalArgumentException("Friction constant must be in [0;1]");
        }
        this.constant = 1 - constant;
    }

    @Override
    public void accept(final Body body) {
        body.setForce(body.force().sub(body.force().mul(body.mass() * constant)));
    }
}
