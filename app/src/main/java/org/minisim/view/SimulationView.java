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
import org.minisim.view.images.ImageManager;
import org.minisim.view.images.ImageName;

public class SimulationView extends BorderPane {

    private boolean isSimulationPlaying = true;

    public SimulationView() {
        final Canvas canvas = new Canvas(500, 500);
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        setCenter(canvas);

        final GridPane controlButtons = new GridPane();
        final Button playbackButton = new IconButton(ImageManager.getImage(ImageName.PLAYBACK), 20, 20);
        final IconButton playButton = new IconButton(ImageManager.getImage(ImageName.PLAY), 20, 20);
        playButton.setOnAction(event -> {
            if (isSimulationPlaying) {
                playButton.changeImage(ImageManager.getImage(ImageName.PAUSE));
            } else {
                playButton.changeImage(ImageManager.getImage(ImageName.PLAY));
            }
            isSimulationPlaying = !isSimulationPlaying;
        });
        controlButtons.addRow(0, playbackButton, playButton);
        controlButtons.setAlignment(Pos.CENTER);
        setBottom(controlButtons);

        final Task<Integer> task = new Task<>() {
            @Override
            protected Integer call() {
                int iterations = 0;
                final int maxIterations = 1000;
                final Simulation sim = App.getSimulation();
                boolean paused = true;
                while (!paused && iterations < maxIterations) {
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
                    /* This doesn't work for some reason
                    logger.info("finished rendering simulation");
                    final WritableImage snapshot = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                    canvas.snapshot(new SnapshotParameters(), snapshot); // can be async with a callback
                    final File imageFile = new File(String.format("snapshot-%04d.png", iterations));
                    logger.info("Prepared snapshot and file");
                    try {
                        if (imageFile.createNewFile()) {
                            logger.info("File " + imageFile.getAbsolutePath() + " didn't exist");
                        } else {
                            logger.info("File " + imageFile.getAbsolutePath() + " already existed");
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    final RenderedImage image = SwingFXUtils.fromFXImage(snapshot, null);
                    try {
                        ImageIO.write(image, "png", imageFile);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    */
                    iterations++;
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
