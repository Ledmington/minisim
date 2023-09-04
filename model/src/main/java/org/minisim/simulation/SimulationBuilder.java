package org.minisim.simulation;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

import org.minisim.simulation.body.Body;
import org.minisim.simulation.border.Borders;
import org.minisim.simulation.border.CyclicBorders;
import org.minisim.simulation.border.SolidBorders;
import org.minisim.simulation.force.Force;
import org.minisim.simulation.force.UnaryForce;

public final class SimulationBuilder {

    private int n = 0;
    private int w;
    private int h;
    private Supplier<Body> bodySupplier;
    private final List<Force> forces = new LinkedList<>();
    private final List<UnaryForce> unaryForces = new LinkedList<>();

    private enum BorderType {
        SOLID,
        CYCLIC
    }

    private BorderType borderType;

    public SimulationBuilder() {}

    public SimulationBuilder nBodies(final int x) {
        n = x;
        return this;
    }

    public SimulationBuilder randomBodyIn(final double xmin, final double xmax, final double ymin, final double ymax) {
        final RandomGenerator rng = RandomGeneratorFactory.getDefault().create(System.nanoTime());
        bodySupplier = () -> Body.builder()
                .position(rng.nextDouble(xmin, xmax), rng.nextDouble(ymin, ymax))
                .radius(1)
                .build();
        return this;
    }

    public SimulationBuilder fixedBody(final Body fixedBody) {
        final Supplier<Body> oldSupplier = bodySupplier;
        bodySupplier = new Supplier<>() {
            private boolean firstTime = true;

            @Override
            public Body get() {
                if (firstTime) {
                    firstTime = false;
                    return fixedBody;
                } else {
                    return oldSupplier.get();
                }
            }
        };
        return this;
    }

    public SimulationBuilder width(final int width) {
        w = width;
        return this;
    }

    public SimulationBuilder height(final int height) {
        h = height;
        return this;
    }

    public SimulationBuilder addForce(final Force force) {
        forces.add(force);
        return this;
    }

    public SimulationBuilder addForce(final UnaryForce force) {
        unaryForces.add(force);
        return this;
    }

    public SimulationBuilder solidBorders() {
        borderType = BorderType.SOLID;
        return this;
    }

    public SimulationBuilder cyclicBorders() {
        borderType = BorderType.CYCLIC;
        return this;
    }

    public Simulation build() {
        Borders b;
        switch (borderType) {
            case SOLID -> b = new SolidBorders(w, h);
            case CYCLIC -> b = new CyclicBorders(w, h);
            default -> b = null;
        }
        return new SerialSimulation(n, bodySupplier, b, forces, unaryForces);
    }
}
