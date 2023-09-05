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

import com.ledmington.minisim.simulation.body.Body;
import com.ledmington.minisim.simulation.border.Borders;
import com.ledmington.minisim.simulation.collision.CollisionManager;
import com.ledmington.minisim.simulation.force.Force;
import com.ledmington.minisim.utils.MiniLogger;

public final class SerialSimulation implements Simulation {

    private final int nBodies;
    private final SimulationState state;
    private final List<Force> forces = new ArrayList<>();
    private final Borders bounds;
    private static final MiniLogger logger = MiniLogger.getLogger("SerialSimulation");

    public SerialSimulation(
            final int nBodies, final Supplier<Body> createBody, final Borders bounds, final List<Force> forces) {
        if (nBodies < 0) {
            throw new IllegalArgumentException("Can't have negative bodies");
        }
        Objects.requireNonNull(bounds);
        Objects.requireNonNull(forces);
        this.forces.addAll(forces);

        this.bounds = bounds;

        this.nBodies = nBodies;
        final double[] posx = new double[nBodies];
        final double[] posy = new double[nBodies];
        final double[] speedx = new double[nBodies];
        final double[] speedy = new double[nBodies];
        final double[] accx = new double[nBodies];
        final double[] accy = new double[nBodies];
        final double[] forcex = new double[nBodies];
        final double[] forcey = new double[nBodies];
        final double[] masses = new double[nBodies];
        final double[] radii = new double[nBodies];
        final boolean[] fixed = new boolean[nBodies];
        for (int i = 0; i < nBodies; i++) {
            final Body b = createBody.get();
            posx[i] = b.position().x();
            posy[i] = b.position().y();
            speedx[i] = b.speed().x();
            speedy[i] = b.speed().y();
            accx[i] = b.acceleration().x();
            accy[i] = b.acceleration().y();
            forcex[i] = b.force().x();
            forcey[i] = b.force().y();
            masses[i] = b.mass();
            radii[i] = b.radius();
            fixed[i] = b.isFixed();
        }

        this.state = new SimulationState(posx, posy, speedx, speedy, accx, accy, forcex, forcey, masses, radii, fixed);
    }

    public SimulationState getState() {
        return state;
    }

    public Borders getBounds() {
        return bounds;
    }

    public void update() {
        final long start = System.nanoTime();

        // while(detectAndResolveCollisions()) {}
        // TODO: if you do too many iterations, some body will be pushed outside the domain borders
        for (int i = 0; i < 10; i++) {
            if (!CollisionManager.detectAndResolveCollisions(state)) {
                break;
            }
        }

        // compute forces
        for (final Force f : forces) {
            f.accept(state);
        }

        // apply forces
        final double[] accx = state.accx();
        final double[] accy = state.accy();
        final double[] forcex = state.forcex();
        final double[] forcey = state.forcey();
        final double[] speedx = state.speedx();
        final double[] speedy = state.speedy();
        final double[] posx = state.posx();
        final double[] posy = state.posy();
        final double[] masses = state.masses();
        final boolean[] fixed = state.fixed();
        for (int i = 0; i < nBodies; i++) {
            if (!fixed[i]) {
                accx[i] = forcex[i] / masses[i];
                accy[i] = forcey[i] / masses[i];
                speedx[i] += accx[i];
                speedy[i] += accy[i];
                posx[i] += speedx[i];
                posy[i] += speedy[i];
            }
            forcex[i] = 0;
            forcey[i] = 0;
        }

        bounds.accept(state);

        logger.info("Done one iteration in %,d ms", (int) ((double) (System.nanoTime() - start) / 1_000_000.0));
    }
}
