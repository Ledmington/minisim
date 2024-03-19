/*
* minisim - Minimalistic N-Body simulation
* Copyright (C) 2022-2024 Filippo Barbari <filippo.barbari@gmail.com>
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

import com.ledmington.minisim.simulation.SimulationState;

import jdk.incubator.vector.DoubleVector;
import jdk.incubator.vector.VectorMask;
import jdk.incubator.vector.VectorSpecies;

public final class Gravity implements Force {

    public static final double NEWTON_GRAVITY = 6.6743e-11;
    private static final VectorSpecies<Double> species = DoubleVector.SPECIES_PREFERRED;
    private static final int lanes = species.length();

    private final DoubleVector constant;

    public Gravity(final double constant) {
        this.constant = DoubleVector.broadcast(species, constant);
    }

    public Gravity() {
        this(NEWTON_GRAVITY);
    }

    @Override
    public void accept(final SimulationState state) {
        final double[] posx = state.posx();
        final double[] posy = state.posy();
        final double[] forcex = state.forcex();
        final double[] forcey = state.forcey();
        final double[] masses = state.masses();
        for (int i = 0; i < posx.length; i++) {
            final DoubleVector posx_i = DoubleVector.broadcast(species, posx[i]);
            final DoubleVector posy_i = DoubleVector.broadcast(species, posy[i]);
            final DoubleVector masses_i = DoubleVector.broadcast(species, masses[i]);
            DoubleVector forcex_i = DoubleVector.broadcast(species, forcex[i]);
            DoubleVector forcey_i = DoubleVector.broadcast(species, forcey[i]);
            int j = i + 1;
            for (; j + (lanes - 1) < posx.length; j += lanes) {
                final DoubleVector posx_j = DoubleVector.fromArray(species, posx, j);
                final DoubleVector posy_j = DoubleVector.fromArray(species, posy, j);
                final DoubleVector masses_j = DoubleVector.fromArray(species, masses, j);

                final DoubleVector x_diff = posx_i.sub(posx_j);
                final DoubleVector y_diff = posy_i.sub(posy_j);

                final DoubleVector x_diff_sq = x_diff.mul(x_diff);
                final DoubleVector y_diff_sq = y_diff.mul(y_diff);

                final DoubleVector distance = x_diff_sq.add(y_diff_sq).sqrt();

                final DoubleVector forceModulo =
                        constant.mul(masses_i).mul(masses_j).div(distance.mul(distance));
                final DoubleVector forceDirection_x = x_diff.div(distance).mul(forceModulo);
                final DoubleVector forceDirection_y = y_diff.div(distance).mul(forceModulo);

                DoubleVector forcex_j = DoubleVector.fromArray(species, forcex, j);
                DoubleVector forcey_j = DoubleVector.fromArray(species, forcey, j);

                forcex_i = forcex_i.sub(forceDirection_x);
                forcex_j = forcex_j.add(forceDirection_x);

                forcey_i = forcey_i.sub(forceDirection_y);
                forcey_j = forcey_j.add(forceDirection_y);

                forcex_j.intoArray(forcex, j);
                forcey_j.intoArray(forcey, j);
            }
            if (j < posx.length) {
                final VectorMask<Double> mask = species.indexInRange(j, posx.length);
                final DoubleVector posx_j = DoubleVector.fromArray(species, posx, j, mask);
                final DoubleVector posy_j = DoubleVector.fromArray(species, posy, j, mask);
                final DoubleVector masses_j = DoubleVector.fromArray(species, masses, j, mask);

                final DoubleVector x_diff = posx_i.sub(posx_j);
                final DoubleVector y_diff = posy_i.sub(posy_j);

                final DoubleVector x_diff_sq = x_diff.mul(x_diff);
                final DoubleVector y_diff_sq = y_diff.mul(y_diff);

                final DoubleVector distance = x_diff_sq.add(y_diff_sq).sqrt();

                final DoubleVector forceModulo =
                        constant.mul(masses_i).mul(masses_j).div(distance.mul(distance));
                final DoubleVector forceDirection_x = x_diff.div(distance).mul(forceModulo);
                final DoubleVector forceDirection_y = y_diff.div(distance).mul(forceModulo);

                DoubleVector forcex_j = DoubleVector.fromArray(species, forcex, j, mask);
                DoubleVector forcey_j = DoubleVector.fromArray(species, forcey, j, mask);

                forcex_i = forcex_i.sub(forceDirection_x);
                forcex_j = forcex_j.add(forceDirection_x);

                forcey_i = forcey_i.sub(forceDirection_y);
                forcey_j = forcey_j.add(forceDirection_y);

                forcex_j.intoArray(forcex, j, mask);
                forcey_j.intoArray(forcey, j, mask);
            }

            forcex[i] = forcex_i.lane(0);
            forcey[i] = forcey_i.lane(0);
        }
    }
}
