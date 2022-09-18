package minisim.simulation;

import java.util.LinkedList;
import java.util.List;
import minisim.simulation.border.Borders;
import minisim.simulation.border.CyclicBorders;
import minisim.simulation.border.SolidBorders;
import minisim.simulation.force.Force;
import minisim.simulation.force.UnaryForce;

public class SimulationBuilder {

    private int n = 0;
    private int w;
    private int h;
    private List<Force> forces = new LinkedList<>();
    private List<UnaryForce> unaryForces = new LinkedList<>();

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
        return new Simulation(n, b, forces, unaryForces);
    }
}
