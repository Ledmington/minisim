package org.minisim.simulation;

import java.util.*;
import java.util.function.Supplier;

import org.minisim.simulation.body.Body;
import org.minisim.simulation.border.Borders;
import org.minisim.simulation.collision.CollisionManager;
import org.minisim.simulation.force.Force;
import org.minisim.simulation.force.UnaryForce;
import org.minisim.utils.MiniLogger;

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
