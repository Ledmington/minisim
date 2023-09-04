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

import com.ledmington.minisim.simulation.V2;
import com.ledmington.minisim.simulation.body.Body;

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
    public void accept(final Body first, final Body second) {
        // TODO: can refactor the squared distance with a method inside V2
        final double distance = first.dist(second);
        final double force = constant * first.mass() * second.mass() / (distance * distance);
        final V2 forceDirection = first.position().sub(second.position()).norm();

        final V2 diff = forceDirection.mul(force);

        first.setForce(first.force().sub(diff));
        second.setForce(second.force().add(diff));
    }
}
