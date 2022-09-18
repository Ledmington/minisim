package minisim;

import static org.junit.jupiter.api.Assertions.assertEquals;

import minisim.simulation.Simulation;
import minisim.simulation.force.Friction;
import minisim.simulation.force.Gravity;
import org.junit.jupiter.api.Test;

public class TestSimulationBuilder {
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
                .width(100)
                .height(100)
                .addForce(new Gravity(1e-10))
                .addForce(new Friction(0.99))
                .solidBorders()
                .build();
        assertEquals(2, sim.getBodies().size());
    }
}
