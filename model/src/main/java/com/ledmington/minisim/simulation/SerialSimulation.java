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
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import com.ledmington.minisim.simulation.body.Body;
import com.ledmington.minisim.simulation.border.Borders;
import com.ledmington.minisim.simulation.collision.CollisionManager;
import com.ledmington.minisim.simulation.force.Force;
import com.ledmington.minisim.simulation.force.UnaryForce;
import com.ledmington.minisim.utils.MiniLogger;

public final class SerialSimulation implements Simulation {

    private final int nBodies;
    private final double[] posx;
    private final double[] posy;
    private final double[] speedx;
    private final double[] speedy;
    private final double[] accx;
    private final double[] accy;
    private final double[] forcex;
    private final double[] forcey;
    private final double[] masses;
    private final double[] radii;
    private final List<Force> forces = new ArrayList<>();
    private final List<UnaryForce> unaryForces = new ArrayList<>();
    private final Borders bounds;
    private static final MiniLogger logger = MiniLogger.getLogger("SerialSimulation");

    public SerialSimulation(
            final int nBodies,
            final Supplier<Body> createBody,
            final Borders bounds,
            final List<Force> forces,
            final List<UnaryForce> unaryForces) {
        if (nBodies < 0) {
            throw new IllegalArgumentException("Can't have negative bodies");
        }
        Objects.requireNonNull(bounds);
        Objects.requireNonNull(forces);
        Objects.requireNonNull(unaryForces);
        this.forces.addAll(forces);
        this.unaryForces.addAll(unaryForces);

        this.bounds = bounds;

        this.nBodies = nBodies;
        this.posx = new double[nBodies];
        this.posy = new double[nBodies];
        this.speedx = new double[nBodies];
        this.speedy = new double[nBodies];
        this.accx = new double[nBodies];
        this.accy = new double[nBodies];
        this.forcex = new double[nBodies];
        this.forcey = new double[nBodies];
        this.masses = new double[nBodies];
        this.radii = new double[nBodies];
        for (int i = 0; i < nBodies; i++) {
            final Body b = createBody.get();
            this.posx[i] = b.position().x();
            this.posy[i] = b.position().y();
            this.speedx[i] = b.speed().x();
            this.speedy[i] = b.speed().y();
            this.accx[i] = b.acceleration().x();
            this.accy[i] = b.acceleration().y();
            this.forcex[i] = b.force().x();
            this.forcey[i] = b.force().y();
            this.masses[i] = b.mass();
            this.radii[i] = b.radius();
        }
    }

    public List<Body> getBodies() {
        return IntStream.range(0, nBodies)
                .mapToObj(i -> Body.builder()
                        .position(posx[i], posy[i])
                        .speed(speedx[i], speedy[i])
                        .acceleration(accx[i], accy[i])
                        .force(forcex[i], forcey[i])
                        .mass(masses[i])
                        .radius(radii[i])
                        .build())
                .toList();
    }

    public Borders getBounds() {
        return bounds;
    }

    public void update() {
        final long start = System.nanoTime();

        // while(detectAndResolveCollisions()) {}
        // TODO: if you do too many iterations, some body will be pushed outside the domain borders
        for (int i = 0; i < 10; i++) {
            if (!CollisionManager.detectAndResolveCollisions(posx, posy, radii)) {
                break;
            }
        }

        // compute forces
        computeAllForces();

        // apply forces
        for (int i = 0; i < nBodies; i++) {
            accx[i] = forcex[i] / masses[i];
            accy[i] = forcey[i] / masses[i];
            speedx[i] += accx[i];
            speedy[i] += accy[i];
            posx[i] += speedx[i];
            posy[i] += speedy[i];
            forcex[i] = 0;
            forcey[i] = 0;
        }

        bounds.apply(posx, posy, speedx, speedy);

        logger.info("Done one iteration in %,d ms", (int) ((double) (System.nanoTime() - start) / 1_000_000.0));
    }

    private void computeAllForces() {
        // All "double" forces
        for (final Force f : forces) {
            f.apply(posx, posy, forcex, forcey, masses);
        }

        // All unary forces
        for (final UnaryForce uf : unaryForces) {
            uf.apply(forcex, forcey, masses);
        }
    }
}
