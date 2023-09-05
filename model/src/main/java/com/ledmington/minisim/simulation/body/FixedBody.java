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

final class FixedBody extends AbstractBody {

    private final V2 position;
    private final double mass;
    private final double radius;

    public FixedBody(final V2 position, final double mass, final double radius) {
        // TODO: null checks and positive mass and radius
        this.position = position;
        this.mass = mass;
        this.radius = radius;
    }

    @Override
    public V2 position() {
        return position;
    }

    @Override
    public V2 speed() {
        return V2.origin();
    }

    @Override
    public V2 acceleration() {
        return V2.origin();
    }

    @Override
    public V2 force() {
        return V2.origin();
    }

    @Override
    public double mass() {
        return mass;
    }

    @Override
    public double radius() {
        return radius;
    }

    @Override
    public void setPosition(final V2 newPosition) {
        // intentionally left empty
    }

    @Override
    public void setSpeed(final V2 newSpeed) {
        // intentionally left empty
    }

    @Override
    public void setForce(final V2 newForce) {
        // intentionally left empty
    }

    @Override
    public void applyForce() {
        // intetionally left empty
    }

    @Override
    public boolean isFixed() {
        return true;
    }
}
