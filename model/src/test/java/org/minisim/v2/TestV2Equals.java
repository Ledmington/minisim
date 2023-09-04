package org.minisim.v2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.minisim.simulation.V2;

class TestV2Equals {

    private static final double EPSILON = 1e-12;

    private static Stream<Arguments> randomVectors() {
        final RandomGenerator rng = RandomGeneratorFactory.getDefault().create(System.nanoTime());
        return Stream.generate(() -> Arguments.of(rng.nextDouble(-10.0, 10.0), rng.nextDouble(-10.0, 10.0)))
                .limit(10);
    }

    @ParameterizedTest
    @MethodSource("randomVectors")
    public void vectorEqualsItself(double x, double y) {
        final V2 v = V2.of(x, y);
        assertEquals(v, v);
    }

    @ParameterizedTest
    @MethodSource("randomVectors")
    public void equalsShouldBeSymmetric(double x, double y) {
        final V2 v = V2.of(x, y);
        final V2 w = V2.of(x, y);
        assertEquals(v, w);
        assertEquals(w, v);
    }

    @ParameterizedTest
    @MethodSource("randomVectors")
    public void equalsShouldBeConsistent(double x, double y) {
        final V2 v = V2.of(x, y);
        final V2 w = V2.of(x, y);
        for (int i = 0; i < 10; i++) {
            assertEquals(v, w);
        }
    }

    @Test
    public void vectorDoesNotEqualObject() {
        final V2 v = V2.of(3, 5);
        assertNotEquals(v, new Object());
    }

    @ParameterizedTest
    @MethodSource("randomVectors")
    public void twoVectorsWithSameCoordinatesAreTheSameVector(double x, double y) {
        final V2 v = V2.of(x, y);
        final V2 w = V2.of(x, y);
        assertNotSame(v, w);
        assertEquals(v, w);
    }

    @ParameterizedTest
    @MethodSource("randomVectors")
    public void twoVectorsWithDifferentCoordinatesShouldNotBeEqual(double x, double y) {
        final V2 v = V2.of(x, y);
        assertNotEquals(v, V2.of(x, y + 1));
        assertNotEquals(v, V2.of(x + 1, y));
    }

    @ParameterizedTest
    @MethodSource("randomVectors")
    public void twoVectorsWithSlightlyDifferentCoordinatesShouldNotBeEqual(double x, double y) {
        final V2 v = V2.of(x, y);
        assertNotEquals(v, V2.of(x, y + EPSILON * 2));
        assertNotEquals(v, V2.of(x, y - EPSILON * 2));
        assertNotEquals(v, V2.of(x + EPSILON * 2, y));
        assertNotEquals(v, V2.of(x - EPSILON * 2, y));
    }

    @Test
    public void vectorToString() {
        final V2 v = V2.of(3, 5);
        assertEquals("V2[x=" + v.x() + ", y=" + v.y() + "]", v.toString());
    }
}
