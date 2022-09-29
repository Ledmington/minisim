package org.minisim.body;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.minisim.simulation.V2;
import org.minisim.simulation.body.Body;

public final class TestFixedBody extends TestBody {

    @Test
    public void aNewFixedBodyHasNoSpeed() {
        assertEquals(Body.builder().fixed().position(3, 5).build().speed(), V2.origin());
    }

    @Test
    public void aNewFixedBodyHasNoAcceleration() {
        assertEquals(Body.builder().fixed().position(3, 5).build().acceleration(), V2.origin());
    }

    @Test
    public void aNewFixedBodyHasNoForce() {
        assertEquals(Body.builder().fixed().position(3, 5).build().force(), V2.origin());
    }

    @Test
    public void callingSetPositionShouldNotChangeItsPosition() {
        final V2 oldPosition = V2.of(3, 5);
        final Body b = Body.builder().fixed().position(oldPosition).build();
        b.setPosition(-1, -2);
        assertEquals(b.position(), oldPosition);
    }

    @Test
    public void callingSetForceShouldNotChangeItsForce() {
        final Body b = Body.builder().fixed().position(3, 5).build();
        b.setForce(V2.of(-1, -2));
        assertEquals(b.force(), V2.origin());
    }

    @Test
    public void callingSetSpeedShouldNotChangeItsSpeed() {
        final Body b = Body.builder().fixed().position(3, 5).build();
        b.setSpeed(-1, -2);
        assertEquals(b.speed(), V2.origin());
    }
}
