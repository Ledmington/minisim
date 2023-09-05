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
package com.ledmington.minisim.simulation.force;

import com.ledmington.minisim.simulation.SimulationState;

public final class Friction implements Force {

    private final double constant;

    public Friction(final double constant) {
        if (constant < 0 || constant > 1) {
            throw new IllegalArgumentException("Friction constant must be in [0;1]");
        }
        this.constant = 1 - constant;
    }

    @Override
    public void accept(final SimulationState state) {
        final double[] forcex = state.forcex();
        final double[] forcey = state.forcey();
        final double[] masses = state.masses();
        for (int i = 0; i < forcex.length; i++) {
            forcex[i] -= forcex[i] * masses[i] * constant;
            forcey[i] -= forcey[i] * masses[i] * constant;
        }
    }
}
