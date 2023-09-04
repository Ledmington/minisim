/*
* minisim - Minimalistic N-Body simulation
* Copyright (C) 2022-2023 Filippo Barbari <filippo.barbari@gmail.com>
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

import static org.junit.jupiter.api.Assertions.*;

import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;
import java.util.stream.Stream;

import com.ledmington.minisim.simulation.V2;
import com.ledmington.minisim.simulation.body.Body;
import com.ledmington.minisim.simulation.force.GravityDown;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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
