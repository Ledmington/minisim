package org.minisim;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.minisim.simulation.Simulation;
import org.minisim.simulation.force.Friction;
import org.minisim.simulation.force.Gravity;

public class TestSimulationBuilderImpl {
    @Test
    public void noBodiesIfNotSpecified() {
        Simulation sim = Simulation.builder()
                .width(100)
                .height(100)
                .addForce(new Gravity(1e-10))
                .addForce(new Friction(0.99))
                .solidBorders()
                .build();
        assertEquals(0, sim.getBodies().size());
    }

    @Test
    public void correctNumberOfBodies() {
        Simulation sim = Simulation.builder()
                .nBodies(2)
                .randomBodyIn(0, 10, 0, 10)
                .width(100)
                .height(100)
                .addForce(new Gravity(1e-10))
                .addForce(new Friction(0.99))
                .solidBorders()
                .build();
        assertEquals(2, sim.getBodies().size());
    }
}
