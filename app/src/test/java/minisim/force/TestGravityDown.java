package org.minisim.force;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.minisim.simulation.Body;
import org.minisim.simulation.V2;
import org.minisim.simulation.force.GravityDown;

public class TestGravityDown {

    @Test
    public void stationaryObjectShouldFallDown() {
        final Body b = Body.builder().build();
        final GravityDown gravity = new GravityDown(0.9);
        final V2 oldPosition = b.position.copy();
        gravity.accept(b);
        b.applyForce();
        assertEquals(b.position.x, oldPosition.x);
        assertTrue(b.position.y < oldPosition.y);
    }

    @Test
    public void YForceShouldAlwaysDecrease() {
        final Body b = Body.builder().force(new V2(3, 5)).build();
        final GravityDown gravity = new GravityDown(0.9);
        final V2 oldForce = b.force.copy();
        gravity.accept(b);
        assertEquals(b.force.x, oldForce.x);
        assertTrue(b.force.y < oldForce.y);
    }
}
