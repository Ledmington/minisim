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
package com.ledmington.minisim.simulation.border;

import com.ledmington.minisim.simulation.body.Body;

/**
 * By using SolidBorders, bodies will stick on simulation's borders and bounce
 * back.
 */
public final class SolidBorders extends Borders {

    public SolidBorders(final double width, final double height) {
        super(width, height);
    }

    @Override
    public void accept(final Body b) {
        double newPosX = b.position().x();
        double newPosY = b.position().y();
        double newSpeedX = b.speed().x();
        double newSpeedY = b.speed().y();
        if (b.position().x() < LEFT_BORDER) {
            newPosX = LEFT_BORDER;
            newSpeedX = -b.speed().x();
        } else if (b.position().x() > RIGHT_BORDER) {
            newPosX = RIGHT_BORDER;
            newSpeedX = -b.speed().x();
        }

        if (b.position().y() < BOTTOM_BORDER) {
            newPosY = BOTTOM_BORDER;
            newSpeedY = -b.speed().y();
        } else if (b.position().y() > UP_BORDER) {
            newPosY = UP_BORDER;
            newSpeedY = -b.speed().y();
        }

        b.setPosition(newPosX, newPosY);
        b.setSpeed(newSpeedX, newSpeedY);
    }
}
