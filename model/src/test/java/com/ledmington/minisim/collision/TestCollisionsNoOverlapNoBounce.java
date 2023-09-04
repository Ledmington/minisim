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
package com.ledmington.minisim.collision;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

import com.ledmington.minisim.simulation.V2;
import com.ledmington.minisim.simulation.body.Body;
import com.ledmington.minisim.simulation.collision.CollisionManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public final class TestCollisionsNoOverlapNoBounce {

    private final List<Body> bodies = new LinkedList<>();

    @AfterEach
    public void teardown() {
        bodies.clear();
    }

    @Test
    public void canDetectCollisions() {
        bodies.add(Body.builder().position(1, 1).build());
        bodies.add(Body.builder().position(2, 2).build());
        Assertions.assertTrue(CollisionManager.detectAndResolveCollisions(bodies));
    }

    @Test
    public void noCollisions() {
        bodies.add(Body.builder().position(1, 1).build());
        bodies.add(Body.builder().position(3, 3).build());
        assertFalse(CollisionManager.detectAndResolveCollisions(bodies));
    }

    @Test
    public void canResolveCollisions() {
        final Body first = Body.builder().position(1, 2).build();
        final Body second = Body.builder().position(2, 2).build();
        bodies.add(first);
        bodies.add(second);
        assertTrue(CollisionManager.detectAndResolveCollisions(bodies));
        assertFalse(CollisionManager.detectAndResolveCollisions(bodies));
        assertEquals(V2.of(0.5, 2), first.position());
        assertEquals(V2.of(2.5, 2), second.position());
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.01, 0.1, 1, 2, 3, 4, 5, 10})
    public void canResolveCollisionsDifferentMasses(double m) {
        // this test is a copy-paste of "canResolveCollisions" because the mass
        // must not interfere with collisions
        final Body first = Body.builder().position(1, 2).mass(2).build();
        final Body second = Body.builder().position(2, 2).mass(m).build();
        bodies.add(first);
        bodies.add(second);
        assertTrue(CollisionManager.detectAndResolveCollisions(bodies));
        assertFalse(CollisionManager.detectAndResolveCollisions(bodies));
        assertEquals(V2.of(0.5, 2), first.position());
        assertEquals(V2.of(2.5, 2), second.position());
    }

    @Test
    public void canResolveCollisionsDifferentRadii() {
        final Body first = Body.builder().position(4, 4).radius(2).build();
        final Body second = Body.builder().position(5, 4).radius(3).build();
        bodies.add(first);
        bodies.add(second);
        assertTrue(CollisionManager.detectAndResolveCollisions(bodies));
        assertFalse(CollisionManager.detectAndResolveCollisions(bodies));
        assertEquals(V2.of(2, 4), first.position());
        assertEquals(V2.of(7, 4), second.position());
    }
}
