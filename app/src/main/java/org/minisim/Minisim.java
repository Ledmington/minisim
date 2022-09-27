package org.minisim;

import static org.minisim.App.logger;
import static org.minisim.AppConstants.*;

import javafx.stage.Stage;
import org.minisim.simulation.Simulation;
import org.minisim.simulation.force.Friction;
import org.minisim.simulation.force.GravityDown;
import org.minisim.view.FrameManager;
import org.minisim.view.MinisimView;

/**
 * This class represents an instance of the program.
 */
public final class Minisim {

    private final Simulation sim = Simulation.builder()
            .nBodies(1000)
            .randomBodyIn(0, 500, 0, 500)
            .width(500)
            .height(500)
            // .addForce(new Gravity(1e-2))
            .addForce(new GravityDown(0.1))
            .addForce(new Friction(0.5))
            .solidBorders()
            .build();

    // TODO: refactor or make singleton
    private final FrameManager frameManager = new FrameManager();

    public Minisim(final Stage stage) {
        logger.info("MiniSim is running on:");
        logger.info(" - " + OSName + " " + OSVersion);
        logger.info(" - Java " + javaVersion);
        logger.info(" - JVM " + jvmVersion);
        logger.info(" - JavaFX " + javafxVersion);
        new MinisimView(this, stage);
    }

    public Simulation getSimulation() {
        return sim;
    }

    public FrameManager getFrameManager() {
        return frameManager;
    }
}
