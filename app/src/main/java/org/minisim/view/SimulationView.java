package org.minisim.view;

import java.util.stream.IntStream;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.PixelReader;
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

            Platform.runLater(() -> {
                final WritableImage img = canvas.snapshot(new SnapshotParameters(), null);
                System.out.println("W: " + img.getWidth());
                System.out.println("H: " + img.getHeight());
                final PixelReader pixelReader = img.getPixelReader();
                IntStream.range(0, (int) img.getWidth())
                        .boxed()
                        .flatMap(x -> IntStream.range(0, (int) img.getHeight())
                                .boxed()
                                .map(y -> {
                                    // final int color = pixelReader.getArgb(x, y);
                                    return pixelReader.getColor(x, y);
                                    //                                    return new Pixel(
                                    //                                            x,
                                    //                                            y,
                                    //                                            (color & 0xff000000 >> 24),
                                    //                                            (color & 0x00ff0000 >> 16),
                                    //                                            (color & 0x0000ff00 >> 8),
                                    //                                            (color & 0x000000ff));
                                }))
                        .filter(c -> !c.equals(Color.WHITE))
                        .forEach(System.out::println);
                //                for (int x = 0; x < 10 /*img.getWidth()*/; x++) {
                //                    for (int y = 0; y <  10 /*img.getHeight()*/; y++) {
                //                        final int color = img.getPixelReader().getArgb(x, y);
                //                        System.out.println("(" + x + "," + y + ") -> a:" + (color & 0xff000000 >> 24)
                // + ",r:"
                //                                + (color & 0x00ff0000 >> 16) + ",g:" + (color & 0x0000ff00 >> 8) +
                // ",b:"
                //                                + (color & 0x000000ff));
                //                    }
                //                }
            });
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
