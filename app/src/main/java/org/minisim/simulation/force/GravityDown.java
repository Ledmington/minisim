package org.minisim.simulation.force;

import org.minisim.simulation.V2;
import org.minisim.simulation.body.Body;

public final class GravityDown implements UnaryForce {

    private final V2 vectorDown;

    public GravityDown(final double modulo) {
        this.vectorDown = new V2(0, modulo);
    }

    @Override
    public void accept(final Body body) {
        body.force = body.force.sub(vectorDown);
    }
}
