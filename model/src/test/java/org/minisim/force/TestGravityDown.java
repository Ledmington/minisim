package org.minisim.force;

import static org.junit.jupiter.api.Assertions.*;

import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.minisim.simulation.V2;
import org.minisim.simulation.body.Body;
import org.minisim.simulation.force.GravityDown;

public final class TestGravityDown {

    @Test
    public void stationaryObjectShouldFallDown() {
        final Body b = Body.builder().build();
        final GravityDown gravity = new GravityDown(0.9);
        final V2 oldPosition = b.position().copy();
        gravity.accept(b);
        b.applyForce();
        assertEquals(b.position().x(), oldPosition.x());
        assertTrue(b.position().y() < oldPosition.y());
    }

    private static Stream<Arguments> randomVectors() {
        final RandomGenerator rng = RandomGeneratorFactory.getDefault().create(System.nanoTime());
        return Stream.generate(() -> Arguments.of(rng.nextDouble(-10.0, 10.0), rng.nextDouble(-10.0, 10.0)))
                .limit(10);
    }

    @ParameterizedTest
    @MethodSource("randomVectors")
    public void YForceShouldAlwaysDecrease(double x, double y) {
        final Body b = Body.builder().force(x, y).build();
        final GravityDown gravity = new GravityDown(0.9);
        final V2 oldForce = b.force().copy();
        gravity.accept(b);
        assertEquals(b.force().x(), oldForce.x());
        assertTrue(b.force().y() < oldForce.y());
    }
}
