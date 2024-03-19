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
package com.ledmington.minisim.border;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ledmington.minisim.simulation.V2;
import com.ledmington.minisim.simulation.body.Body;
import com.ledmington.minisim.simulation.border.Borders;
import com.ledmington.minisim.simulation.border.CyclicBorders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public final class TestCyclicBorders {

    private Borders cb;
    private final double width = 10;
    private final double height = 10;

    @BeforeEach
    public void setup() {
        cb = new CyclicBorders(width, height);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1, 0})
    public void illegalWidth(double w) {
        assertThrows(IllegalArgumentException.class, () -> new CyclicBorders(w, 10));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1, 0})
    public void illegalHeight(double h) {
        assertThrows(IllegalArgumentException.class, () -> new CyclicBorders(10, h));
    }

    @ParameterizedTest
    @ValueSource(doubles = {1, 2, 3, 4, 5, 6, 7, 8, 9})
    public void noChangesIfInside(double n) {
        final Body b = Body.builder().position(n, n).build();
        cb.accept(b);
        assertEquals(V2.of(n, n), b.position());
    }

    @ParameterizedTest
    @ValueSource(doubles = {11, 22, 33, 44, 55, 66, 77, 88, 99})
    public void outOnRight(double x) {
        final Body b = Body.builder().position(x, 1).build();
        cb.accept(b);
        assertEquals(V2.of(x % width, 1), b.position());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1, -11, -22, -33, -44, -55, -66, -77, -88, -99})
    public void outOnLeft(double x) {
        final Body b = Body.builder().position(x, 1).build();
        cb.accept(b);
        assertEquals(V2.of((x + 100 * width) % width, 1), b.position());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1, -11, -22, -33, -44, -55, -66, -77, -88, -99})
    public void outOnTop(double y) {
        final Body b = Body.builder().position(1, y).build();
        cb.accept(b);
        assertEquals(V2.of(1, (y + 100 * height) % height), b.position());
    }

    @ParameterizedTest
    @ValueSource(doubles = {11, 22, 33, 44, 55, 66, 77, 88, 99})
    public void outOnBottom(double y) {
        final Body b = Body.builder().position(1, y).build();
        cb.accept(b);
        assertEquals(V2.of(1, y % height), b.position());
    }

    @ParameterizedTest
    @ValueSource(doubles = {11, 22, 33, 44, 55, 66, 77, 88, 99})
    public void bottomRightCorner(double n) {
        final Body b = Body.builder().position(n, n).build();
        cb.accept(b);
        assertEquals(V2.of(n % width, n % height), b.position());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1, -11, -22, -33, -44, -55, -66, -77, -88, -99})
    public void topLeftCorner(double n) {
        final Body b = Body.builder().position(n, n).build();
        cb.accept(b);
        assertEquals(V2.of((n + 100 * width) % width, (n + 100 * height) % height), b.position());
    }
}
