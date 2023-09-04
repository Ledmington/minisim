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

public final class BodyBuilder {

    private V2 position = V2.origin();
    private V2 speed = V2.origin();
    private V2 acceleration = V2.origin();
    private V2 force = V2.origin();
    private double mass = 1;
    private double radius = 1;
    private boolean fixed = false;

    public BodyBuilder position(final V2 newPosition) {
        this.position = newPosition;
        return this;
    }

    public BodyBuilder position(final double x, final double y) {
        return position(V2.of(x, y));
    }

    public BodyBuilder speed(final double x, final double y) {
        this.speed = V2.of(x, y);
        return this;
    }

    public BodyBuilder acceleration(final double x, final double y) {
        this.acceleration = V2.of(x, y);
        return this;
    }

    public BodyBuilder force(final V2 newForce) {
        this.force = newForce;
        return this;
    }

    public BodyBuilder force(final double x, final double y) {
        this.force = V2.of(x, y);
        return this;
    }

    public BodyBuilder mass(final double mass) {
        this.mass = mass;
        return this;
    }

    public BodyBuilder radius(final double radius) {
        this.radius = radius;
        return this;
    }

    public BodyBuilder fixed() {
        this.fixed = true;
        return this;
    }

    /**
     * Builds a new Body with the given parameters. If not set, they will default
     * to the following values:
     * - position = (0,0)
     * - speed = (0,0)
     * - acceleration = (0,0)
     * - force = (0,0)
     * - mass = 1
     * - radius = 1
     * @return
     *      A new Body with the given parameters.
     */
    public Body build() {
        if (fixed) {
            return new FixedBody(position, mass, radius);
        } else {
            return new MovableBody(position, speed, acceleration, force, mass, radius);
        }
    }
}
