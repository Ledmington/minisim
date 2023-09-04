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
package com.ledmington.minisim.simulation.body;

import com.ledmington.minisim.simulation.V2;

/**
 * This class exists only to allow to have two final methods
 * inside every Body.
 */
abstract sealed class AbstractBody implements Body permits FixedBody, MovableBody {

    public final void setPosition(final double x, final double y) {
        setPosition(V2.of(x, y));
    }

    public final void setSpeed(final double x, final double y) {
        setSpeed(V2.of(x, y));
    }

    public final boolean collidesWith(final Body other) {
        final double Rsum = radius() + other.radius();
        if (Math.abs(position().x() - other.position().x()) > Rsum) {
            return false;
        }
        if (Math.abs(position().y() - other.position().y()) > Rsum) {
            return false;
        }
        // return position.distsq(other.position) < Rsum * Rsum; // faster way (maybe):
        // doesn't use a sqrt
        return dist(other) < Rsum; // <-- correct way
    }

    public final double dist(final Body other) {
        return position().dist(other.position());
    }
}
