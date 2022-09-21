package org.minisim.simulation;

import java.util.List;
import org.minisim.simulation.body.Body;
import org.minisim.simulation.border.Borders;

public interface Simulation {
    static SimulationBuilder builder() {
        return new SimulationBuilder();
    }

    // TODO: add javadoc
    void update();

    Borders getBounds();

    List<Body> getBodies();
}
