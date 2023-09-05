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

public final class Gravity implements Force {

    public static final double NEWTON_GRAVITY = 6.6743e-11;

    private final double constant;

    public Gravity(final double constant) {
        this.constant = constant;
    }

    public Gravity() {
        this(NEWTON_GRAVITY);
    }

    @Override
    public void accept(final SimulationState state) {
        final double[] posx = state.posx();
        final double[] posy = state.posy();
        final double[] forcex = state.forcex();
        final double[] forcey = state.forcey();
        final double[] masses = state.masses();
        for (int i = 0; i < posx.length; i++) {
            for (int j = i + 1; j < posx.length; j++) {
                final double distance = Math.hypot(posx[i] - posx[j], posy[i] - posy[j]);
                final double forceModulo = constant * masses[i] * masses[j] / (distance * distance);
                final double forceDirectionX = (posx[i] - posx[j]) / distance * forceModulo;
                final double forceDirectionY = (posy[i] - posy[j]) / distance * forceModulo;

                forcex[i] -= forceDirectionX;
                forcey[i] -= forceDirectionY;
                forcex[j] += forceDirectionX;
                forcey[j] += forceDirectionY;
            }
        }
    }
}
