package org.minisim.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import javafx.application.Platform;
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
        // TODO: refactor as template method
        final Stage progressDialog = new Stage();
        progressDialog.setTitle("Exporting video");
        progressDialog.setResizable(false);
        final BorderPane pane = new BorderPane();
        final Label title = LabelFactory.getInstance().newLabel("Loading frames");
        pane.setTop(title);
        final ProgressBar progressBar = new ProgressBar(0);
        pane.setCenter(progressBar);
        final Scene scene = new Scene(pane);
        progressDialog.setScene(scene);

        final int framesPerSecond = 25;
        System.out.printf("Compression %d frames in a %d fps video%n", totalFrames, framesPerSecond);
        AWTSequenceEncoder encoder =
                EncoderUtils.safeCreateSequenceEncoder(new File("./simulation.mp4"), framesPerSecond);
        final File[] frames = new File(frameDirectory).listFiles();
        assert frames != null;
        Arrays.sort(frames, (x, y) -> x.getName().compareTo(y.getName()));

        for (int i = 0; i < frames.length; i++) {
            final int finalI = i;
            Platform.runLater(() -> {
                title.setText(String.format("Encoding frame %04d / %04d", finalI, frames.length));
                progressBar.setProgress((double) finalI / (double) frames.length);
                System.out.printf(
                        "Loading frame %04d / %04d (\"%s\")%n", finalI, frames.length, frames[finalI].getName());
                final BufferedImage img = FileUtils.safeRead(frames[finalI]);
                EncoderUtils.safeEncodeImage(encoder, img);
            });
        }

        Platform.runLater(() -> EncoderUtils.safeFinish(encoder));

        progressDialog.showAndWait();
    }
}
