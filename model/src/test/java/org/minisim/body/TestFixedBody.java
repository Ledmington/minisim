package org.minisim.body;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.minisim.simulation.V2;
import org.minisim.simulation.body.FixedBody;

public final class TestFixedBody extends TestBody {

    @Test
    public void aNewFixedBodyHasNoSpeed() {
        assertEquals(new FixedBody(V2.of(3, 5), 1, 1).speed(), V2.origin());
    }

    @Test
    public void aNewFixedBodyHasNoAcceleration() {
        assertEquals(new FixedBody(V2.of(3, 5), 1, 1).acceleration(), V2.origin());
    }

    @Test
    public void aNewFixedBodyHasNoForce() {
        assertEquals(new FixedBody(V2.of(3, 5), 1, 1).force(), V2.origin());
    }

    @Test
    public void callingSetPositionShouldNotChangeItsPosition() {
        final V2 oldPosition = V2.of(3, 5);
        final FixedBody b = new FixedBody(oldPosition, 1, 1);
        b.setPosition(-1, -2);
        assertEquals(b.position(), oldPosition);
    }

    @Test
    public void callingSetForceShouldNotChangeItsForce() {
        final FixedBody b = new FixedBody(V2.of(3, 5), 1, 1);
        b.setForce(V2.of(-1, -2));
        assertEquals(b.force(), V2.origin());
    }

    @Test
    public void callingSetSpeedShouldNotChangeItsSpeed() {
        final FixedBody b = new FixedBody(V2.of(3, 5), 1, 1);
        b.setSpeed(-1, -2);
        assertEquals(b.speed(), V2.origin());
    }
}
