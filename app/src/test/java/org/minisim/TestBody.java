package org.minisim;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.minisim.simulation.V2;
import org.minisim.simulation.body.Body;

public final class TestBody {

    public static final double EPSILON = 1e-12;

    @Test
    public void defaultBody() {
        Body b = new Body();
        assertEquals(0, b.position.x(), EPSILON);
        assertEquals(0, b.position.y(), EPSILON);
        assertEquals(0, b.speed.x(), EPSILON);
        assertEquals(0, b.speed.y(), EPSILON);
        assertEquals(1, b.mass(), EPSILON);
        assertEquals(1, b.radius(), EPSILON);
    }

    @Test
    public void illegalMass() {
        assertThrows(IllegalArgumentException.class, () -> new Body(V2.origin(), V2.origin(), 0, 1));
        assertThrows(IllegalArgumentException.class, () -> new Body(V2.origin(), V2.origin(), -1, 1));
    }

    @Test
    public void illegalRadius() {
        assertThrows(IllegalArgumentException.class, () -> new Body(V2.origin(), V2.origin(), 1, 0));
        assertThrows(IllegalArgumentException.class, () -> new Body(V2.origin(), V2.origin(), 1, -1));
    }

    @Test
    public void twoBodyCollision() {
        Body a = new Body(new V2(0, 0), new V2(0, 0), 1, 1);
        Body b = new Body(new V2(1, 1), new V2(0, 0), 1, 1);
        assertTrue(a.collidesWith(b));
    }

    @Test
    public void twoBodyNoCollision() {
        Body a = new Body(new V2(0, 0), new V2(0, 0), 1, 1);
        Body b = new Body(new V2(2, 2), new V2(0, 0), 1, 1);
        assertFalse(a.collidesWith(b));
    }

    @Test
    void distanceShouldAlwaysBeCommutative() {
        final List<V2> positions =
                List.of(V2.origin(), new V2(1, 2), new V2(3, 5), new V2(-2, 2), new V2(10, -10), new V2(-7, -6));

        for (V2 firstPosition : positions) {
            for (V2 secondPosition : positions) {
                final Body first = new Body(firstPosition, V2.origin(), 1, 1);
                final Body second = new Body(secondPosition, V2.origin(), 1, 1);
                final double firstDistance = first.dist(second);
                final double secondDistance = second.dist(first);
                assertEquals(
                        firstDistance,
                        secondDistance,
                        "Distances between " + firstPosition + " and " + secondPosition + " were not equal: "
                                + firstDistance + " and " + secondDistance);
            }
        }
    }
}
