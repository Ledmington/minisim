package org.minisim.view;

import static org.minisim.App.logger;

import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.minisim.App;
import org.minisim.simulation.Simulation;

public class SimulationView extends BorderPane {

    private boolean isSimulationPlaying = false;

    public SimulationView() {
        final Canvas canvas = new Canvas(500, 500);
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        setCenter(canvas);

        final GridPane controlButtons = new GridPane();
        final Button playbackButton = new IconButton("/icons/back.png", 20, 20);
        final IconButton playButton = new IconButton("/icons/play.png", 20, 20);
        playButton.setOnAction(event -> {
            if (isSimulationPlaying) {
                playButton.changeImage("/icons/pause.png");
            } else {
                playButton.changeImage("/icons/play.png");
            }
            isSimulationPlaying = !isSimulationPlaying;
        });
        controlButtons.addRow(0, playbackButton, playButton);
        controlButtons.setAlignment(Pos.CENTER);
        setBottom(controlButtons);

        final Task<Integer> task = new Task<>() {
            @Override
            protected Integer call() {
                int iterations;
                final int maxIterations = 1000;
                final Simulation sim = App.getSimulation();
                for (iterations = 0; iterations < maxIterations; iterations++) {
                    if (isCancelled()) {
                        updateMessage("Cancelled");
                        logger.info("cancelled");
                        break;
                    }
                    logger.info("iteration: " + iterations);
                    updateMessage("Iteration " + iterations);
                    updateProgress(iterations, maxIterations);

                    sim.update();
                    renderSimulation(gc);
                }
                return iterations;
            }
        };

        final Thread th = new Thread(task);
        th.setDaemon(false);
        th.start();
    }

    public void renderSimulation(final GraphicsContext gc) {
        final Simulation sim = App.getSimulation();
        gc.clearRect(0, 0, sim.getBounds().RIGHT_BORDER, sim.getBounds().UP_BORDER);
        gc.setFill(Color.RED);
        sim.getBodies().forEach(b -> gc.fillOval(b.position.x, b.position.y, b.radius, b.radius));
    }
}
