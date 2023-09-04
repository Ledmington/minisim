package org.minisim;

import javafx.application.HostServices;
import javafx.stage.Stage;

import org.minisim.simulation.Simulation;
import org.minisim.simulation.body.Body;
import org.minisim.simulation.force.Friction;
import org.minisim.simulation.force.Gravity;
import org.minisim.utils.MiniLogger;
import org.minisim.view.FrameManager;
import org.minisim.view.MinisimView;
import org.minisim.view.images.ImageManager;
import org.minisim.view.images.ImageName;

/**
 * This class represents an instance of the program.
 */
public final class Minisim {

    private final Simulation sim = Simulation.builder()
            .nBodies(1000)
            .randomBodyIn(0, 500, 0, 500)
            .fixedBody(Body.builder()
                    .fixed()
                    .mass(1000)
                    .radius(5)
                    .position(250, 250)
                    .build())
            .width(500)
            .height(500)
            .addForce(new Gravity(1e-2))
            // .addForce(new GravityDown(0.1))
            .addForce(new Friction(0.5))
            // .solidBorders()
            .cyclicBorders()
            .build();

    // TODO: refactor or make singleton
    private final FrameManager frameManager = new FrameManager();

    public Minisim(final HostServices hostServices, final Stage stage) {
        final MiniLogger logger = MiniLogger.getLogger("minisim");
        logger.info("MiniSim is running on:");
        logger.info(" - %s %s", AppConstants.OSName, AppConstants.OSVersion);
        logger.info(" - Java %s", AppConstants.javaVersion);
        logger.info(" - JVM %s", AppConstants.jvmVersion);
        logger.info(" - JavaFX %s", AppConstants.javafxVersion);
        stage.getIcons().add(ImageManager.getImage(ImageName.APPLICATION_ICON));
        new MinisimView(this, hostServices, stage);
    }

    public Simulation getSimulation() {
        return sim;
    }

    public FrameManager getFrameManager() {
        return frameManager;
    }
}
