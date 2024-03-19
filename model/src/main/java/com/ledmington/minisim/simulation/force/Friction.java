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

public final class Friction implements Force {

    private static final VectorSpecies<Double> species = DoubleVector.SPECIES_PREFERRED;
    private static final int lanes = species.length();
    private final DoubleVector constant;

    public Friction(final double constant) {
        if (constant < 0 || constant > 1) {
            throw new IllegalArgumentException("Friction constant must be in [0;1]");
        }
        this.constant = DoubleVector.broadcast(species, 1 - constant);
    }

    @Override
    public void accept(final SimulationState state) {
        final double[] forcex = state.forcex();
        final double[] forcey = state.forcey();
        final double[] masses = state.masses();
        int i = 0;
        for (; i + (lanes - 1) < forcex.length; i += lanes) {
            DoubleVector masses_i = DoubleVector.fromArray(species, masses, i);
            DoubleVector forcex_i = DoubleVector.fromArray(species, forcex, i);
            DoubleVector forcey_i = DoubleVector.fromArray(species, forcey, i);
            forcex_i = forcex_i.sub(forcex_i.mul(masses_i).mul(constant));
            forcey_i = forcey_i.sub(forcey_i.mul(masses_i).mul(constant));
            forcex_i.intoArray(forcex, i);
            forcey_i.intoArray(forcey, i);
        }
        if (i < forcex.length) {
            VectorMask<Double> mask = species.indexInRange(i, forcex.length);
            DoubleVector masses_i = DoubleVector.fromArray(species, masses, i, mask);
            DoubleVector forcex_i = DoubleVector.fromArray(species, forcex, i, mask);
            DoubleVector forcey_i = DoubleVector.fromArray(species, forcey, i, mask);
            forcex_i = forcex_i.sub(forcex_i.mul(masses_i).mul(constant));
            forcey_i = forcey_i.sub(forcey_i.mul(masses_i).mul(constant));
            forcex_i.intoArray(forcex, i, mask);
            forcey_i.intoArray(forcey, i, mask);
        }
    }
}
