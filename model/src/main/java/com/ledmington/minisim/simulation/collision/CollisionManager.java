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
package com.ledmington.minisim.simulation.collision;

import java.util.List;

import com.ledmington.minisim.simulation.V2;
import com.ledmington.minisim.simulation.body.Body;

public final class CollisionManager {

    private CollisionManager() {}

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
     * @param bodies
     *            The list of bodies to check and update.
     *
     * @return True if there were any collisions.
     */
    public static boolean detectAndResolveCollisions(final List<Body> bodies) {
        boolean foundCollisions = false;
        final int n = bodies.size();
        for (int i = 0; i < n; i++) {
            final Body first = bodies.get(i);
            for (int j = i + 1; j < n; j++) {
                final Body second = bodies.get(j);

                if (first.collidesWith(second)) {
                    // we have found a collision
                    foundCollisions = true;

                    // vector pointing first (but centered in origin)
                    final V2 diff = first.position().sub(second.position());

                    /*
                     * Computing the magnitude of the movement as result of this system. R1 = a + b;
                     * R2 = b + c; compenetration = a + b + c we want to find b because it
                     * represents the distance to be covered in order to avoid the collision. If b
                     * was zero, we would have no collisions. b can be as high as min(R1, R2). The
                     * resulting formula is b = R1 + R2 - compenetration = (a+b) + (b+c) - (a+b+c)
                     */
                    final double compenetration = diff.mod();
                    final double b = first.radius() + second.radius() - compenetration;

                    first.setPosition(first.position().add(diff.mul(b / 2)));
                    second.setPosition(second.position().sub(diff.mul(b / 2)));
                }
            }
        }
        return foundCollisions;
    }
}
