package org.minisim.view;

import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.minisim.App;
import org.minisim.simulation.Simulation;
import org.minisim.utils.Worker;
import org.minisim.view.images.ImageManager;
import org.minisim.view.images.ImageName;

public class SimulationView extends BorderPane {

    private final Worker simulationUpdaterWorker;

    public SimulationView() {
        final Canvas canvas = new Canvas(500, 500);
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        setCenter(canvas);

        simulationUpdaterWorker = new Worker("simulationUpdater", () -> {
            final Simulation sim = App.getSimulation();
            sim.update();
            renderSimulation(gc);
        });

        final GridPane controlButtons = new GridPane();
        final Button playbackButton = new IconButton(ImageManager.getImage(ImageName.PLAYBACK), 20, 20);
        final IconButton playButton = new IconButton(ImageManager.getImage(ImageName.PLAY), 20, 20);
        playButton.setOnAction(event -> {
            if (simulationUpdaterWorker.isStopped()) {
                playButton.changeImage(ImageManager.getImage(ImageName.PAUSE));
                simulationUpdaterWorker.start();
            } else {
                playButton.changeImage(ImageManager.getImage(ImageName.PLAY));
                simulationUpdaterWorker.stop();
            }
        });
        controlButtons.addRow(0, playbackButton, playButton);
        controlButtons.setAlignment(Pos.CENTER);
        setBottom(controlButtons);
    }

    public void renderSimulation(final GraphicsContext gc) {
        final Simulation sim = App.getSimulation();
        gc.clearRect(0, 0, sim.getBounds().RIGHT_BORDER, sim.getBounds().UP_BORDER);
        gc.setFill(Color.RED);
        sim.getBodies()
                .forEach(b -> gc.fillOval(b.position.x, sim.getBounds().UP_BORDER - b.position.y, b.radius, b.radius));
    }
}
