package org.minisim.force;

import static org.junit.jupiter.api.Assertions.*;

import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.minisim.simulation.V2;
import org.minisim.simulation.body.Body;
import org.minisim.simulation.force.Friction;

public final class TestFriction {

    @ParameterizedTest
    @ValueSource(doubles = {-0.1, -3.5, -12.0, 1.1, 1.01, 1.001, 6.2, 32.0})
    public void invalidFrictionConstant(double v) {
        assertThrows(
                IllegalArgumentException.class, () -> new Friction(v), "Friction constant should be in range [0;1]");
    }

    private static Stream<Arguments> randomVectors() {
        final RandomGenerator rng = RandomGeneratorFactory.getDefault().create(System.nanoTime());
        return Stream.generate(() -> Arguments.of(rng.nextDouble(-10.0, 10.0), rng.nextDouble(-10.0, 10.0)))
                .limit(10);
    }

    @ParameterizedTest
    @MethodSource("randomVectors")
    public void frictionConstantZeroIsAllowed(double x, double y) {
        final Body b = Body.builder().position(2, 3).force(x, y).build();
        final Friction friction = new Friction(0);

        friction.accept(b);

        assertEquals(0, b.force().mod());
    }

    @ParameterizedTest
    @MethodSource("randomVectors")
    public void frictionConstantOneIsAllowed(double x, double y) {
        final Body b = Body.builder().position(2, 3).build();
        final Friction friction = new Friction(1);

        final V2 oldForce = V2.of(x, y);
        b.setForce(oldForce.copy());

        friction.accept(b);

        assertEquals(oldForce, b.force());
    }

    @ParameterizedTest
    @MethodSource("randomVectors")
    public void stationaryObjectShouldRemainStationary(double x, double y) {
        final Body b = Body.builder().position(x, y).build();
        final Friction friction = new Friction(0.9);

        final V2 oldPosition = b.position().copy();

        friction.accept(b);
        b.applyForce();

        assertEquals(oldPosition, b.position());
    }

    @ParameterizedTest
    @MethodSource("randomVectors")
    public void alreadyPresentForceShouldDecreaseInModulo(double x, double y) {
        final V2 oldForce = V2.of(x, y);
        final Body b = Body.builder().position(2, 3).force(oldForce.copy()).build();
        final Friction friction = new Friction(0.9);

        friction.accept(b);

        assertTrue(b.force().mod() < oldForce.mod());
    }

    @ParameterizedTest
    @MethodSource("randomVectors")
    public void heavierObjectsForceShouldDecreaseMore(double x, double y) {
        final V2 oldForce = V2.of(x, y);
        final Body light =
                Body.builder().position(2, 3).force(oldForce.copy()).mass(2).build();
        final Body heavy =
                Body.builder().position(7, 4).force(oldForce.copy()).mass(4).build();
        final Friction friction = new Friction(0.9);

        friction.accept(light);
        friction.accept(heavy);

        assertTrue(light.force().mod() > heavy.force().mod());
    }
}
