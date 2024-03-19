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
package com.ledmington.minisim.simulation;

public record SimulationState(
        double[] posx,
        double[] posy,
        double[] speedx,
        double[] speedy,
        double[] accx,
        double[] accy,
        double[] forcex,
        double[] forcey,
        double[] masses,
        double[] radii,
        boolean[] fixed) {
    public SimulationState {
        if (posx.length != posy.length
                || posx.length != speedx.length
                || posx.length != speedy.length
                || posx.length != accx.length
                || posx.length != accy.length
                || posx.length != forcex.length
                || posx.length != forcey.length
                || posx.length != masses.length
                || posx.length != radii.length
                || posx.length != fixed.length) {
            throw new IllegalArgumentException("Given arrays were not of the same size");
        }
    }
}
