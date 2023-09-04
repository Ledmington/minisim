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
package com.ledmington.minisim.simulation;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

import com.ledmington.minisim.simulation.body.Body;
import com.ledmington.minisim.simulation.border.Borders;
import com.ledmington.minisim.simulation.border.CyclicBorders;
import com.ledmington.minisim.simulation.border.SolidBorders;
import com.ledmington.minisim.simulation.force.Force;
import com.ledmington.minisim.simulation.force.UnaryForce;

public final class SimulationBuilder {

    private int n = 0;
    private int w;
    private int h;
    private Supplier<Body> bodySupplier;
    private final List<Force> forces = new LinkedList<>();
    private final List<UnaryForce> unaryForces = new LinkedList<>();

    private enum BorderType {
        SOLID,
        CYCLIC
    }

    private BorderType borderType;

    public SimulationBuilder() {}

    public SimulationBuilder nBodies(final int x) {
        n = x;
        return this;
    }

    public SimulationBuilder randomBodyIn(final double xmin, final double xmax, final double ymin, final double ymax) {
        final RandomGenerator rng = RandomGeneratorFactory.getDefault().create(System.nanoTime());
        bodySupplier = () -> Body.builder()
                .position(rng.nextDouble(xmin, xmax), rng.nextDouble(ymin, ymax))
                .radius(1)
                .build();
        return this;
    }

    public SimulationBuilder fixedBody(final Body fixedBody) {
        final Supplier<Body> oldSupplier = bodySupplier;
        bodySupplier = new Supplier<>() {
            private boolean firstTime = true;

            @Override
            public Body get() {
                if (firstTime) {
                    firstTime = false;
                    return fixedBody;
                } else {
                    return oldSupplier.get();
                }
            }
        };
        return this;
    }

    public SimulationBuilder width(final int width) {
        w = width;
        return this;
    }

    public SimulationBuilder height(final int height) {
        h = height;
        return this;
    }

    public SimulationBuilder addForce(final Force force) {
        forces.add(force);
        return this;
    }

    public SimulationBuilder addForce(final UnaryForce force) {
        unaryForces.add(force);
        return this;
    }

    public SimulationBuilder solidBorders() {
        borderType = BorderType.SOLID;
        return this;
    }

    public SimulationBuilder cyclicBorders() {
        borderType = BorderType.CYCLIC;
        return this;
    }

    public Simulation build() {
        Borders b;
        switch (borderType) {
            case SOLID -> b = new SolidBorders(w, h);
            case CYCLIC -> b = new CyclicBorders(w, h);
            default -> b = null;
        }
        return new SerialSimulation(n, bodySupplier, b, forces, unaryForces);
    }
}
