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

class TestV2Math {

    private static final double EPSILON = 1e-12;

    private static Stream<Arguments> randomVectors() {
        final RandomGenerator rng = RandomGeneratorFactory.getDefault().create(System.nanoTime());
        return Stream.generate(() -> Arguments.of(rng.nextDouble(-10.0, 10.0), rng.nextDouble(-10.0, 10.0)))
                .limit(10);
    }

    @ParameterizedTest
    @MethodSource("randomVectors")
    public void simpleAdd(double x, double y) {
        final V2 v = V2.origin();
        final V2 w = V2.of(x, y);
        final V2 r = v.add(w);
        assertEquals(r.x(), w.x(), EPSILON);
        assertEquals(r.y(), w.y(), EPSILON);
    }

    @Test
    public void simpleSub() {
        final V2 v = V2.origin();
        final V2 w = V2.of(1, 1);
        final V2 r = v.sub(w);
        assertEquals(r.x(), -w.x(), EPSILON);
        assertEquals(r.y(), -w.y(), EPSILON);
    }

    @Test
    public void addSub() {
        final V2 v = V2.origin();
        final V2 w = V2.of(1, 1);
        final V2 r = v.add(w).sub(w);
        assertEquals(0, r.x(), EPSILON);
        assertEquals(0, r.y(), EPSILON);
    }

    @Test
    public void simpleMul() {
        final V2 v = V2.of(1, 2);
        final V2 r = v.mul(2);
        assertEquals(2, r.x(), EPSILON);
        assertEquals(4, r.y(), EPSILON);
    }

    @Test
    public void simpleDiv() {
        final V2 v = V2.of(2, 4);
        final V2 r = v.div(2);
        assertEquals(1, r.x(), EPSILON);
        assertEquals(2, r.y(), EPSILON);
    }

    @Test
    public void mulDiv() {
        final V2 v = V2.of(1, 1);
        final V2 r = v.mul(2).div(2);
        assertEquals(1, r.x(), EPSILON);
        assertEquals(1, r.y(), EPSILON);
    }

    @Test
    public void moduloOfOriginIsZero() {
        final V2 v = V2.origin();
        assertEquals(0, v.mod(), EPSILON);
    }

    @Test
    public void simpleModulo() {
        final V2 v = V2.of(5, 12);
        assertEquals(13, v.mod(), EPSILON);
    }

    @Test
    public void dotWithOriginIsZero() {
        final V2 v = V2.origin();
        final V2 w = V2.of(2, 3);
        assertEquals(0, v.dot(w), EPSILON);
    }

    @Test
    public void normalDot() {
        final V2 v = V2.of(1, 2);
        final V2 w = V2.of(2, 3);
        assertEquals(8, v.dot(w), EPSILON);
    }

    @Test
    public void vectorNorm() {
        final V2 v = V2.of(0, 2);
        final V2 n = v.norm();
        assertEquals(0, n.x(), EPSILON);
        assertEquals(1, n.y(), EPSILON);
    }

    @Test
    public void normalizedVectorModuloIsOne() {
        final V2 v = V2.of(1.7, 5.3);
        final V2 n = v.norm();
        assertEquals(1, n.mod(), EPSILON);
    }

    @Test
    public void distanceFromOriginIsVectorModulo() {
        final V2 v = V2.origin();
        final V2 w = V2.of(1, 2);
        assertEquals(w.dist(v), w.mod(), EPSILON);
    }

    @Test
    public void distanceTwoPoints() {
        final V2 v = V2.of(2, 1);
        final V2 w = V2.of(1, 1);
        assertEquals(1, w.dist(v), EPSILON);
    }
}
