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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import com.ledmington.minisim.simulation.body.Body;
import com.ledmington.minisim.simulation.border.Borders;
import com.ledmington.minisim.simulation.collision.CollisionManager;
import com.ledmington.minisim.simulation.force.Force;
import com.ledmington.minisim.simulation.force.UnaryForce;
import com.ledmington.minisim.utils.MiniLogger;

public final class SerialSimulation implements Simulation {

    private final List<Body> bodies = new ArrayList<>();
    private final List<Force> forces = new ArrayList<>();
    private final List<UnaryForce> unaryForces = new ArrayList<>();
    private final Borders bounds;
    private static final MiniLogger logger = MiniLogger.getLogger("SerialSimulation");

    public SerialSimulation(
            final int nBodies,
            final Supplier<Body> createBody,
            final Borders b,
            final List<Force> forces,
            final List<UnaryForce> unaryForces) {
        if (nBodies < 0) {
            throw new IllegalArgumentException("Can't have negative bodies");
        }
        Objects.requireNonNull(b);
        Objects.requireNonNull(forces);
        Objects.requireNonNull(unaryForces);
        this.forces.addAll(forces);
        this.unaryForces.addAll(unaryForces);

        bounds = b;
        for (int i = 0; i < nBodies; i++) {
            bodies.add(createBody.get());
        }
    }

    public void addBody(final Body b) {
        bodies.add(b);
    }

    public List<Body> getBodies() {
        return Collections.unmodifiableList(bodies);
    }

    public Borders getBounds() {
        return bounds;
    }

    public void update() {
        final long start = System.nanoTime();

        // while(detectAndResolveCollisions()) {}
        // TODO: if you do too many iterations, some body will be pushed outside the domain borders
        for (int i = 0; i < 10; i++) {
            if (!CollisionManager.detectAndResolveCollisions(bodies)) {
                break;
            }
        }

        // compute forces
        computeAllForces();

        // apply forces
        for (Body b : bodies) {
            b.applyForce();
            bounds.accept(b);
        }

        logger.info("Done one iteration in %,d ms", (int) ((double) (System.nanoTime() - start) / 1_000_000.0));
    }

    private void computeAllForces() {
        // All "double" forces
        for (int i = 0; i < bodies.size(); i++) {
            final Body first = bodies.get(i);
            for (int j = i + 1; j < bodies.size(); j++) {
                final Body second = bodies.get(j);
                for (Force f : forces) {
                    f.accept(first, second);
                }
            }
        }

        // All unary forces
        // TODO: embarassingly parallel
        for (Body b : bodies) {
            for (UnaryForce uf : unaryForces) {
                uf.accept(b);
            }
        }
    }
}
