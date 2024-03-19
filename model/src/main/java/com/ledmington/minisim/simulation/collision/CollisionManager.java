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
package com.ledmington.minisim.simulation.collision;

import com.ledmington.minisim.simulation.SimulationState;

public final class CollisionManager {

    private CollisionManager() {}

    private static boolean collidesWith(final double[] posx, final double[] posy, final double[] radii, int i, int j) {
        final double Rsum = radii[i] + radii[j];
        if (Math.abs(posx[i] - posx[j]) > Rsum) {
            return false;
        }
        if (Math.abs(posy[i] - posy[j]) > Rsum) {
            return false;
        }

        return Math.hypot(posx[i] - posx[j], posy[i] - posy[j]) < Rsum;
    }

    /**
     * Looks for collisions between the given bodies. If at least one collision is
     * found, this method returns true and bodies get updated. A possible way to use
     * this method is this one:
     *
     * <pre>
     * while (!CollisionManager.detectAndResolveCollisions(bodies)) {
     * }
     * </pre>
     *
     * @return True if there were any collisions.
     */
    public static boolean detectAndResolveCollisions(final SimulationState state) {
        boolean foundCollisions = false;
        final double[] posx = state.posx();
        final double[] posy = state.posy();
        final double[] radii = state.radii();
        final boolean[] fixed = state.fixed();
        final int n = posx.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {

                if (collidesWith(posx, posy, radii, i, j)) {
                    // we have found a collision
                    foundCollisions = true;

                    // vector pointing first (but centered in origin)
                    final double x_diff = posx[i] - posx[j];
                    final double y_diff = posy[i] - posy[j];

                    /*
                     * Computing the magnitude of the movement as result of this system. R1 = a + b;
                     * R2 = b + c; compenetration = a + b + c we want to find b because it
                     * represents the distance to be covered in order to avoid the collision. If b
                     * was zero, we would have no collisions. b can be as high as min(R1, R2). The
                     * resulting formula is b = R1 + R2 - compenetration = (a+b) + (b+c) - (a+b+c)
                     */
                    final double compenetration = Math.hypot(x_diff, y_diff);
                    final double b = radii[i] + radii[j] - compenetration;

                    if (!fixed[i]) {
                        posx[i] += x_diff * b / 2;
                        posy[i] += y_diff * b / 2;
                    }
                    if (!fixed[j]) {
                        posx[j] -= x_diff * b / 2;
                        posy[j] -= y_diff * b / 2;
                    }
                }
            }
        }
        return foundCollisions;
    }
}
