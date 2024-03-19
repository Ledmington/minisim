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
package com.ledmington.minisim.body;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ledmington.minisim.simulation.V2;
import com.ledmington.minisim.simulation.body.Body;

import org.junit.jupiter.api.Test;

public final class TestFixedBody extends TestBody {

    @Test
    public void aNewFixedBodyHasNoSpeed() {
        assertEquals(Body.builder().fixed().position(3, 5).build().speed(), V2.origin());
    }

    @Test
    public void aNewFixedBodyHasNoAcceleration() {
        assertEquals(Body.builder().fixed().position(3, 5).build().acceleration(), V2.origin());
    }

    @Test
    public void aNewFixedBodyHasNoForce() {
        assertEquals(Body.builder().fixed().position(3, 5).build().force(), V2.origin());
    }

    @Test
    public void callingSetPositionShouldNotChangeItsPosition() {
        final V2 oldPosition = V2.of(3, 5);
        final Body b = Body.builder().fixed().position(oldPosition).build();
        b.setPosition(-1, -2);
        assertEquals(b.position(), oldPosition);
    }

    @Test
    public void callingSetForceShouldNotChangeItsForce() {
        final Body b = Body.builder().fixed().position(3, 5).build();
        b.setForce(V2.of(-1, -2));
        assertEquals(b.force(), V2.origin());
    }

    @Test
    public void callingSetSpeedShouldNotChangeItsSpeed() {
        final Body b = Body.builder().fixed().position(3, 5).build();
        b.setSpeed(-1, -2);
        assertEquals(b.speed(), V2.origin());
    }
}
