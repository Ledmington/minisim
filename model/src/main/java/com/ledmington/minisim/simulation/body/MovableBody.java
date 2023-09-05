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

final class MovableBody extends AbstractBody {

    private V2 position;
    private V2 speed;
    private V2 acceleration;
    private V2 force;
    private final double mass;
    private final double radius;

    public MovableBody(
            final V2 position,
            final V2 speed,
            final V2 acceleration,
            final V2 force,
            final double mass,
            final double radius) {
        if (mass <= 0.0) {
            throw new IllegalArgumentException("Mass can't be negative or zero");
        }
        if (radius <= 0.0) {
            throw new IllegalArgumentException("Radius can't be negative or zero");
        }

        this.mass = mass;
        this.radius = radius;
        this.position = position;
        this.speed = speed;
        this.acceleration = acceleration;
        this.force = force;
    }

    public double mass() {
        return mass;
    }

    public double radius() {
        return radius;
    }

    public V2 position() {
        return position;
    }

    @Override
    public void setPosition(final V2 newPosition) {
        position = newPosition;
    }

    @Override
    public void setSpeed(final V2 newSpeed) {
        speed = newSpeed;
    }

    public V2 speed() {
        return speed;
    }

    public V2 acceleration() {
        return acceleration;
    }

    public V2 force() {
        return force;
    }

    @Override
    public void setForce(final V2 newForce) {
        force = newForce;
    }

    @Override
    public void applyForce() {
        acceleration = force.div(mass); // * DT; // TODO fix later
        speed = speed.add(acceleration);
        position = position.add(speed);

        force = V2.origin();
    }

    @Override
    public boolean isFixed() {
        return false;
    }
}
