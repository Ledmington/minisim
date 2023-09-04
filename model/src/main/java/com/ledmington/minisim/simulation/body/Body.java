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

public sealed interface Body permits AbstractBody {

    // builder reference
    static BodyBuilder builder() {
        return new BodyBuilder();
    }

    // getters
    V2 position();

    V2 speed();

    V2 acceleration();

    V2 force();

    double mass();

    double radius();

    void setPosition(final V2 newPosition);

    void setPosition(final double x, final double y);

    void setSpeed(final V2 newSpeed);

    void setSpeed(final double x, final double y);

    void setForce(final V2 newForce);

    // TODO: write doc
    boolean collidesWith(final Body other);

    // TODO: write doc
    double dist(final Body other);

    /**
     * Applies the force previously set to make the Body move.
     * After this call, Body.force() should be V2(0,0).
     */
    void applyForce();
}
