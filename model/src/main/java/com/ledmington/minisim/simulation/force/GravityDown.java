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

import com.ledmington.minisim.simulation.SimulationState;

import jdk.incubator.vector.DoubleVector;
import jdk.incubator.vector.VectorMask;
import jdk.incubator.vector.VectorSpecies;

public final class GravityDown implements Force {

    private static final VectorSpecies<Double> species = DoubleVector.SPECIES_PREFERRED;
    private static final int lanes = species.length();
    private final DoubleVector vectorDown;

    public GravityDown(final double modulo) {
        this.vectorDown = DoubleVector.broadcast(species, modulo);
    }

    @Override
    public void accept(final SimulationState state) {
        final double[] forcey = state.forcey();
        int i = 0;
        for (; i + (lanes - 1) < forcey.length; i += lanes) {
            DoubleVector forcey_i = DoubleVector.fromArray(species, forcey, i);
            forcey_i = forcey_i.sub(vectorDown);
            forcey_i.intoArray(forcey, i);
        }
        if (i < forcey.length) {
            VectorMask<Double> mask = species.indexInRange(i, forcey.length);
            DoubleVector forcey_i = DoubleVector.fromArray(species, forcey, i, mask);
            forcey_i = forcey_i.sub(vectorDown);
            forcey_i.intoArray(forcey, i, mask);
        }
    }
}
