/*
* minisim - Minimalistic N-Body simulation
* Copyright (C) 2022-2024 Filippo Barbari <filippo.barbari@gmail.com>
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package com.ledmington.minisim.force;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;
import java.util.stream.Stream;

import com.ledmington.minisim.simulation.V2;
import com.ledmington.minisim.simulation.body.Body;
import com.ledmington.minisim.simulation.force.Gravity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public final class TestGravity {

    private static final double EPSILON = 1e-12;
    private Gravity gravity;

    @BeforeEach
    public void setup() {
        gravity = new Gravity();
    }

    private static Stream<Arguments> randomPairOfVectors() {
        final RandomGenerator rng = RandomGeneratorFactory.getDefault().create(System.nanoTime());
        return Stream.generate(() -> Arguments.of(
                        rng.nextDouble(-10.0, 10.0),
                        rng.nextDouble(-10.0, 10.0),
                        rng.nextDouble(-10.0, 10.0),
                        rng.nextDouble(-10.0, 10.0)))
                .limit(10);
    }

    @ParameterizedTest
    @MethodSource("randomPairOfVectors")
    public void forceShouldBeEqual(double x1, double y1, double x2, double y2) {
        final Body first = Body.builder().position(x1, y1).build();
        final Body second = Body.builder().position(x2, y2).build();
        gravity.accept(first, second);
        assertEquals(first.force().mod(), second.force().mod(), EPSILON);
        assertEquals(first.force().x(), -second.force().x(), EPSILON);
        assertEquals(first.force().y(), -second.force().y(), EPSILON);
    }

    @ParameterizedTest
    @MethodSource("randomPairOfVectors")
    public void forceShouldBeEqualWithDifferentMasses(double x1, double y1, double x2, double y2) {
        final Body first = Body.builder().position(x1, y1).mass(2).build();
        final Body second = Body.builder().position(x2, y2).mass(3).build();
        gravity.accept(first, second);
        assertEquals(first.force().mod(), second.force().mod(), EPSILON);
        assertEquals(first.force().x(), -second.force().x(), EPSILON);
        assertEquals(first.force().y(), -second.force().y(), EPSILON);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.01, 0.1, 1, 2, 3, 4, 5, 6, 7, 8, 9})
    public void forceShouldBeEqualWithDifferentRadii(double r) {
        /*
        The radius should not interfere with gravity.
         */
        final Body first = Body.builder().position(3, 5).radius(2).build();
        final Body second = Body.builder().position(-7, 9).radius(r).build();
        gravity.accept(first, second);
        assertEquals(first.force().mod(), second.force().mod(), EPSILON);
        assertEquals(first.force().x(), -second.force().x(), EPSILON);
        assertEquals(first.force().y(), -second.force().y(), EPSILON);
    }

    @ParameterizedTest
    @MethodSource("randomPairOfVectors")
    public void distanceShouldDecrease(double x1, double y1, double x2, double y2) {
        final Body first = Body.builder().position(x1, y1).build();
        final Body second = Body.builder().position(x2, y2).build();

        final double initialDistance = first.dist(second);

        gravity.accept(first, second);
        first.applyForce();
        second.applyForce();

        final double finalDistance = first.dist(second);

        assertTrue(finalDistance < initialDistance);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
    public void heavierObjectsShouldProduceStrongerForce(double x) {
        final Body left = Body.builder().position(x - 6, 5).build();
        final Body middle = Body.builder().position(x, 5).mass(2).build();
        final Body rightAndHeavy = Body.builder().position(x + 6, 5).mass(6).build();

        gravity.accept(left, middle);

        final double lighterForce = middle.force().mod();

        middle.setForce(V2.origin());

        gravity.accept(middle, rightAndHeavy);

        final double heavyForce = middle.force().mod();

        assertEquals(lighterForce * rightAndHeavy.mass(), heavyForce, EPSILON);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
    public void furtherObjectsShouldProduceWeakerForce(double x) {
        final Body left = Body.builder().position(x - 1, 5).build();
        final Body middle = Body.builder().position(x, 5).build();
        final Body rightAndFurther = Body.builder().position(x + 2, 5).build();

        gravity.accept(left, middle);

        final double closerForce = middle.force().mod();

        middle.setForce(V2.origin());

        gravity.accept(middle, rightAndFurther);

        final double furtherForce = middle.force().mod();

        assertEquals(closerForce, furtherForce * 4, EPSILON);
    }

    @ParameterizedTest
    @MethodSource("randomPairOfVectors")
    public void twoBodies(double x1, double y1, double x2, double y2) {
        final double up = Math.max(y1, y2);
        final double down = Math.min(y1, y2);
        final double left = Math.min(x1, x2);
        final double right = Math.max(x1, x2);

        final Body first = Body.builder().position(left, down).build();
        final Body second = Body.builder().position(right, up).build();

        gravity.accept(first, second);
        first.applyForce();
        second.applyForce();

        assertTrue(first.position().x() > left);
        assertTrue(second.position().x() < right);

        assertTrue(first.position().y() > down);
        assertTrue(second.position().y() < up);
    }
}
