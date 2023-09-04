package org.minisim.body;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.minisim.simulation.V2;
import org.minisim.simulation.body.Body;

public class TestBody {

    public static final double EPSILON = 1e-12;

    @Test
    public void defaultBody() {
        Body b = Body.builder().build();
        assertEquals(0, b.position().x(), EPSILON);
        assertEquals(0, b.position().y(), EPSILON);
        assertEquals(0, b.speed().x(), EPSILON);
        assertEquals(0, b.speed().y(), EPSILON);
        assertEquals(1, b.mass(), EPSILON);
        assertEquals(1, b.radius(), EPSILON);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1, 0})
    public void illegalMass(double m) {
        assertThrows(
                IllegalArgumentException.class, () -> Body.builder().mass(m).build());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1, 0})
    public void illegalRadius(double r) {
        assertThrows(
                IllegalArgumentException.class, () -> Body.builder().radius(r).build());
    }

    @Test
    public void twoBodyCollision() {
        final Body a = Body.builder().position(0, 0).build();
        final Body b = Body.builder().position(1, 1).build();
        assertTrue(a.collidesWith(b));
    }

    @Test
    public void twoBodyNoCollision() {
        final Body a = Body.builder().position(0, 0).build();
        final Body b = Body.builder().position(2, 2).build();
        assertFalse(a.collidesWith(b));
    }

    @Test
    void distanceShouldAlwaysBeCommutative() {
        final List<V2> positions =
                List.of(V2.origin(), V2.of(1, 2), V2.of(3, 5), V2.of(-2, 2), V2.of(10, -10), V2.of(-7, -6));

        for (V2 firstPosition : positions) {
            for (V2 secondPosition : positions) {
                final Body first = Body.builder().position(firstPosition).build();
                final Body second = Body.builder().position(secondPosition).build();
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
