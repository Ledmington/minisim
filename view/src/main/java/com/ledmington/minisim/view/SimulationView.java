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
package com.ledmington.minisim.view;

import java.util.Comparator;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

import com.ledmington.minisim.Minisim;
import com.ledmington.minisim.simulation.Simulation;
import com.ledmington.minisim.simulation.SimulationState;
import com.ledmington.minisim.utils.Worker;
import com.ledmington.minisim.view.images.ImageManager;
import com.ledmington.minisim.view.images.ImageName;

public final class SimulationView extends BorderPane {

    private final Worker simulationUpdaterWorker;
    private final Minisim model;
    private final Canvas canvas;
    private final BodyInfoPane bodyInfo;
    private Optional<Integer> selectedBody = Optional.empty();

    public SimulationView(final Minisim model, final BodyInfoPane bodyInfo) {
        this.model = model;
        this.bodyInfo = bodyInfo;
        canvas = new Canvas(500, 500);
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        canvas.setOnMouseClicked(e -> {
            final double mouseX = e.getX();
            final double mouseY = canvas.getHeight() - e.getY();
            final SimulationState state = model.getSimulation().getState();
            final int nBodies = state.posx().length;
            selectedBody = Stream.iterate(0, i -> i + 1)
                    .limit(nBodies)
                    .min(Comparator.comparingDouble(
                            i -> Math.hypot(state.posx()[i] - mouseX, state.posy()[i] - mouseY)));
            bodyInfo.updateInfo(state, selectedBody);
            renderSimulation(gc);
        });
        setCenter(canvas);

        simulationUpdaterWorker = new Worker("simulationUpdater", () -> {
            final Simulation sim = model.getSimulation();
            sim.update();
            renderSimulation(gc);

            // TODO: must absolutely refactor this
            final CompletableFuture<Void> future = new CompletableFuture<>();
            final AtomicReference<WritableImage> img = new AtomicReference<>();
            Platform.runLater(() -> {
                img.set(canvas.snapshot(new SnapshotParameters(), null));
                future.complete(null);
            });
            try {
                future.get();
                model.getFrameManager()
                        .saveFrame(img.get().getPixelReader(), (int) img.get().getWidth(), (int)
                                img.get().getHeight());
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
        final Simulation sim = model.getSimulation();
        gc.clearRect(0, 0, sim.getBounds().RIGHT_BORDER, sim.getBounds().UP_BORDER);
        gc.setFill(Color.RED);
        final SimulationState state = model.getSimulation().getState();
        final int nBodies = state.posx().length;
        IntStream.range(0, nBodies)
                .forEach(i -> gc.fillOval(
                        state.posx()[i],
                        sim.getBounds().UP_BORDER - state.posy()[i],
                        state.radii()[i],
                        state.radii()[i]));

        if (selectedBody.isEmpty()) {
            return;
        }

        final int selected = selectedBody.orElseThrow();
        final int selectionRadius = 20;
        gc.setFill(Color.BLACK);
        gc.fillRect(0, sim.getBounds().UP_BORDER - state.posy()[selected], state.posx()[selected] - selectionRadius, 1);
        gc.fillRect(
                state.posx()[selected] + selectionRadius,
                sim.getBounds().UP_BORDER - state.posy()[selected],
                canvas.getWidth(),
                1);
        gc.fillRect(state.posx()[selected], 0, 1, canvas.getHeight() - state.posy()[selected] - selectionRadius);
        gc.fillRect(
                state.posx()[selected],
                canvas.getHeight() - state.posy()[selected] + selectionRadius,
                1,
                canvas.getHeight());
        gc.strokeOval(
                state.posx()[selected] - (double) selectionRadius / 2,
                canvas.getHeight() - state.posy()[selected] - (double) selectionRadius / 2,
                selectionRadius,
                selectionRadius);

        Platform.runLater(() -> bodyInfo.updateInfo(state, selectedBody));
    }
}
