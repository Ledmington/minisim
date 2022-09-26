package org.minisim.view;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.minisim.App;
import org.minisim.simulation.Simulation;
import org.minisim.utils.Worker;
import org.minisim.view.images.ImageManager;
import org.minisim.view.images.ImageName;

public final class SimulationView extends BorderPane {

    private final Worker simulationUpdaterWorker;

    public SimulationView() {
        final Canvas canvas = new Canvas(500, 500);
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        setCenter(canvas);

        simulationUpdaterWorker = new Worker("simulationUpdater", () -> {
            final Simulation sim = App.getSimulation();
            sim.update();
            renderSimulation(gc);

            // TODO: must absolutely refactor this
            final CompletableFuture<Void> future = new CompletableFuture<>();
            Platform.runLater(() -> {
                final WritableImage img = canvas.snapshot(new SnapshotParameters(), null);
                App.getFrameManager().saveFrame(img.getPixelReader(), (int) img.getWidth(), (int) img.getHeight());
                future.complete(null);
            });
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

        final GridPane controlButtons = new GridPane();
        final Button playbackButton = new IconButton(ImageManager.getImage(ImageName.PLAYBACK), 20, 20);
        playbackButton.setTooltip(new Tooltip("Playback"));
        final IconButton playButton = new IconButton(ImageManager.getImage(ImageName.PLAY), 20, 20);
        playButton.setTooltip(new Tooltip("Play simulation"));
        playButton.setOnAction(event -> {
            if (simulationUpdaterWorker.isStopped()) {
                playButton.changeImage(ImageManager.getImage(ImageName.PAUSE));
                playButton.setTooltip(new Tooltip("Pause simulation"));
                simulationUpdaterWorker.start();
            } else {
                playButton.changeImage(ImageManager.getImage(ImageName.PLAY));
                playButton.setTooltip(new Tooltip("Play simulation"));
                simulationUpdaterWorker.stop();
            }
        });
        controlButtons.addRow(0, playbackButton, playButton);
        controlButtons.setAlignment(Pos.CENTER);
        setBottom(controlButtons);

        // We render it one time before starting it to allow the user to see that it's ready
        renderSimulation(gc);
    }

    public void renderSimulation(final GraphicsContext gc) {
        final Simulation sim = App.getSimulation();
        gc.clearRect(0, 0, sim.getBounds().RIGHT_BORDER, sim.getBounds().UP_BORDER);
        gc.setFill(Color.RED);
        sim.getBodies()
                .forEach(b -> gc.fillOval(
                        b.position().x(),
                        sim.getBounds().UP_BORDER - b.position().y(),
                        b.radius(),
                        b.radius()));
    }
}
