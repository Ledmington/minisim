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
package com.ledmington.minisim;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ledmington.minisim.simulation.Simulation;
import com.ledmington.minisim.simulation.force.Friction;
import com.ledmington.minisim.simulation.force.Gravity;

import org.junit.jupiter.api.Test;

public final class TestSimulationBuilderImpl {
    @Test
    public void noBodiesIfNotSpecified() {
        Simulation sim = Simulation.builder()
                .width(100)
                .height(100)
                .addForce(new Gravity(1e-10))
                .addForce(new Friction(0.99))
                .solidBorders()
                .build();
        assertEquals(0, sim.getBodies().size());
    }

    @Test
    public void correctNumberOfBodies() {
        Simulation sim = Simulation.builder()
                .nBodies(2)
                .randomBodyIn(0, 10, 0, 10)
                .width(100)
                .height(100)
                .addForce(new Gravity(1e-10))
                .addForce(new Friction(0.99))
                .solidBorders()
                .build();
        assertEquals(2, sim.getBodies().size());
    }
}
