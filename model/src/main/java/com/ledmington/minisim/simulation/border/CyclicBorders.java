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
package com.ledmington.minisim.simulation.border;

import com.ledmington.minisim.simulation.SimulationState;

/**
 * By using CyclicBorders, each body that would otherwise go out of bounds,
 * will come back on the other side of the domain (Pac-Man effect).
 */
public final class CyclicBorders extends Borders {

    public CyclicBorders(final double width, final double height) {
        super(width, height);
    }

    @Override
    public void accept(final SimulationState state) {
        final double[] posx = state.posx();
        final double[] posy = state.posy();
        for (int i = 0; i < posx.length; i++) {
            while (posx[i] < LEFT_BORDER) {
                posx[i] += RIGHT_BORDER;
            }
            while (posx[i] > RIGHT_BORDER) {
                posx[i] -= RIGHT_BORDER;
            }

            while (posy[i] < BOTTOM_BORDER) {
                posy[i] += UP_BORDER;
            }
            while (posy[i] > UP_BORDER) {
                posy[i] -= UP_BORDER;
            }
        }
    }
}
