package org.minisim.simulation.body;

import org.minisim.simulation.V2;

public sealed interface Body permits AbstractBody {

    // builder reference
    static BodyBuilder builder() {
        return new BodyBuilder();
    }

    // getters
    V2 position();

    V2 speed();

    V2 acceleration();

    V2 force();

    double mass();

    double radius();

    void setPosition(final V2 newPosition);

    void setPosition(final double x, final double y);

    void setSpeed(final V2 newSpeed);

    void setSpeed(final double x, final double y);

    void setForce(final V2 newForce);

    // TODO: write doc
    boolean collidesWith(final Body other);

    // TODO: write doc
    double dist(final Body other);

    /**
     * Applies the force previously set to make the Body move.
     * After this call, Body.force() should be V2(0,0).
     */
    void applyForce();
}
