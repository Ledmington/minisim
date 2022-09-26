package org.minisim.force;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.minisim.simulation.V2;
import org.minisim.simulation.body.Body;
import org.minisim.simulation.force.Friction;

public final class TestFriction {

    @Test
    public void invalidFrictionConstant() {
        for (Double v : List.of(-0.1, -3.5, -12.0, 1.1, 1.01, 1.001, 6.2, 32.0)) {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> new Friction(v),
                    "Friction constant should be in range [0;1]");
        }
    }

    @Test
    public void frictionConstantZeroIsAllowed() {
        final Body b = new Body(new V2(2, 3), V2.origin(), 1, 1);
        final Friction friction = new Friction(0);

        b.force = new V2(3, -5);

        friction.accept(b);

        assertEquals(0, b.force.mod());
    }

    @Test
    public void frictionConstantOneIsAllowed() {
        final Body b = new Body(new V2(2, 3), V2.origin(), 1, 1);
        final Friction friction = new Friction(1);

        final V2 oldForce = new V2(3, -5);
        b.force = oldForce.copy();

        friction.accept(b);

        assertEquals(oldForce, b.force);
    }

    @Test
    public void stationaryObjectShouldRemainStationary() {
        final Body b = new Body(new V2(2, 3), V2.origin(), 1, 1);
        final Friction friction = new Friction(0.9);

        final V2 oldPosition = b.position().copy();

        friction.accept(b);
        b.applyForce();

        assertEquals(oldPosition, b.position());
    }

    @Test
    public void alreadyPresentForceShouldDecreaseInModulo() {
        final Body b = new Body(new V2(2, 3), V2.origin(), 1, 1);
        final Friction friction = new Friction(0.9);

        final V2 oldForce = new V2(3, -5);

        b.force = oldForce.copy();

        friction.accept(b);

        assertTrue(b.force.mod() < oldForce.mod());
    }

    @Test
    public void heavierObjectsForceShouldDecreaseMore() {
        final Body light = new Body(new V2(2, 3), V2.origin(), 2, 1);
        final Body heavy = new Body(new V2(7, 4), V2.origin(), 4, 1);
        final Friction friction = new Friction(0.9);

        final V2 oldForce = new V2(3, -5);
        light.force = oldForce.copy();
        heavy.force = oldForce.copy();

        friction.accept(light);
        friction.accept(heavy);

        assertTrue(light.force.mod() > heavy.force.mod());
    }
}
