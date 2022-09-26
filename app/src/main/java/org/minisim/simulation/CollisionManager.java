package org.minisim.simulation;

import java.util.List;
import org.minisim.simulation.body.Body;

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
                    final V2 diff = first.position.sub(second.position);

                    /*
                     * Computing the magnitude of the movement as result of this system. R1 = a + b;
                     * R2 = b + c; compenetration = a + b + c we want to find b because it
                     * represents the distance to be covered in order to avoid the collision. If b
                     * was zero, we would have no collisions. b can be as high as min(R1, R2). The
                     * resulting formula is b = R1 + R2 - compenetration = (a+b) + (b+c) - (a+b+c)
                     */
                    final double compenetration = diff.mod();
                    final double b = first.radius() + second.radius() - compenetration;

                    first.position = first.position.add(diff.mul(b / 2));
                    second.position = second.position.sub(diff.mul(b / 2));
                }
            }
        }
        return foundCollisions;
    }
}
