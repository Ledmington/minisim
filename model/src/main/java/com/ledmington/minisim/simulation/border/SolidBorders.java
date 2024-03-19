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
 * By using SolidBorders, bodies will stick on simulation's borders and bounce
 * back.
 */
public final class SolidBorders extends Borders {

    public SolidBorders(final double width, final double height) {
        super(width, height);
    }

    @Override
    public void accept(final SimulationState state) {
        final double[] posx = state.posx();
        final double[] posy = state.posy();
        final double[] speedx = state.speedx();
        final double[] speedy = state.speedy();
        for (int i = 0; i < posx.length; i++) {
            double newPosX = posx[i];
            double newPosY = posy[i];
            double newSpeedX = speedx[i];
            double newSpeedY = speedy[i];
            if (posx[i] < LEFT_BORDER) {
                newPosX = LEFT_BORDER;
                newSpeedX = -speedx[i];
            } else if (posx[i] > RIGHT_BORDER) {
                newPosX = RIGHT_BORDER;
                newSpeedX = -speedx[i];
            }

            if (posy[i] < BOTTOM_BORDER) {
                newPosY = BOTTOM_BORDER;
                newSpeedY = -speedy[i];
            } else if (posy[i] > UP_BORDER) {
                newPosY = UP_BORDER;
                newSpeedY = -speedy[i];
            }

            posx[i] = newPosX;
            posy[i] = newPosY;
            speedx[i] = newSpeedX;
            speedy[i] = newSpeedY;
        }
    }
}
