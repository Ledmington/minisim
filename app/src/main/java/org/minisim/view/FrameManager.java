package org.minisim.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jcodec.api.awt.AWTSequenceEncoder;
import org.minisim.utils.EncoderUtils;
import org.minisim.utils.FileUtils;
import org.minisim.utils.ThreadUtils;
import org.minisim.view.images.ImageManager;
import org.minisim.view.images.ImageName;

// TODO: refactor or make singleton
public final class FrameManager {

    private final String frameDirectory =
            FileUtils.safeCreateTempDirectory("minisimFrames").toFile().getAbsolutePath();

    private int currentFrame = 0;
    private int totalFrames = 0;

    public FrameManager() {}

    // TODO: make async or parallel or both
    public void saveFrame(final PixelReader reader, final int width, final int height) {
        final BufferedImage theImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                theImage.setRGB(x, y, reader.getArgb(x, y));
            }
        }
        final File outputFile =
                new File(frameDirectory + File.separator + String.format("frame-%04d.png", currentFrame));
        currentFrame++;
        totalFrames++;
        FileUtils.safeWrite(theImage, "png", outputFile);
    }

    // TODO: consider looking at jcodec-streaming for live video creation
    public void createVideo() {
        // TODO: refactor the whole "create progress dialog to show a task" as template method
        final Stage progressDialog = new Stage();
        progressDialog.setTitle("Exporting video");
        final BorderPane pane = new BorderPane();
        final Label title = new Label() {
            {
                setText("Loading frames");
                setAlignment(Pos.CENTER_LEFT);
            }
        };
        title.setPadding(new Insets(15));
        pane.setTop(title);
        final ProgressBar progressBar = new ProgressBar(0);
        progressBar.setPadding(new Insets(15));
        progressBar.setMaxWidth(Double.MAX_VALUE);
        progressBar.setMinWidth(300); // ugly
        pane.setCenter(progressBar);
        final Scene scene = new Scene(pane);
        progressDialog.setScene(scene);
        progressDialog.getIcons().add(ImageManager.getImage(ImageName.APPLICATION_ICON));
        progressDialog.setResizable(false);

        final Thread videoRendererThread = new Thread(
                () -> {
                    final int framesPerSecond = 25;
                    System.out.printf("Compression %d frames in a %d fps video%n", totalFrames, framesPerSecond);
                    AWTSequenceEncoder encoder =
                            EncoderUtils.safeCreateSequenceEncoder(new File("./simulation.mp4"), framesPerSecond);
                    final File[] frames = new File(frameDirectory).listFiles();
                    assert frames != null;
                    Arrays.sort(frames, Comparator.comparing(File::getName));

                    for (int i = 0; i < frames.length; i++) {
                        final int finalI = i;
                        final double percentage = (double) (finalI + 1) / (double) frames.length;
                        Platform.runLater(() -> title.setText(String.format(
                                "Encoding frame %04d / %04d (%.2f%%)", finalI + 1, frames.length, percentage * 100)));
                        progressBar.setProgress(percentage);
                        System.out.printf(
                                "Loading frame %04d / %04d (\"%s\")%n",
                                finalI, frames.length, frames[finalI].getName());
                        final BufferedImage img = FileUtils.safeRead(frames[finalI]);
                        EncoderUtils.safeEncodeImage(encoder, img);
                    }

                    EncoderUtils.safeFinish(encoder);
                },
                "videoRendererThread");
        videoRendererThread.start();

        progressDialog.showAndWait();
        ThreadUtils.safeJoin(videoRendererThread);
    }
}
