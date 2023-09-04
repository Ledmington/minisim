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

public abstract class Borders {

    public static final double LEFT_BORDER = 0; // TODO: refactoring needed
    public final double RIGHT_BORDER;
    public static final double BOTTOM_BORDER = 0; // TODO: refactoring needed
    public final double UP_BORDER;

    public Borders(final double width, final double height) {
        if (width <= LEFT_BORDER || height <= BOTTOM_BORDER) {
            throw new IllegalArgumentException("Width and height must be strictly positive");
        }
        RIGHT_BORDER = width;
        UP_BORDER = height;
    }

    public abstract void apply(final double[] posx, final double[] posy, final double[] speedx, final double[] speedy);
}
