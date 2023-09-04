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
package com.ledmington.minisim;

import javafx.application.HostServices;
import javafx.stage.Stage;

import com.ledmington.minisim.simulation.Simulation;
import com.ledmington.minisim.simulation.body.Body;
import com.ledmington.minisim.simulation.force.Friction;
import com.ledmington.minisim.simulation.force.Gravity;
import com.ledmington.minisim.utils.MiniLogger;
import com.ledmington.minisim.view.FrameManager;
import com.ledmington.minisim.view.MinisimView;
import com.ledmington.minisim.view.images.ImageManager;
import com.ledmington.minisim.view.images.ImageName;

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
            .solidBorders()
            // .cyclicBorders()
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
