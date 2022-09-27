package org.minisim.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import javafx.scene.image.PixelReader;
import org.jcodec.api.awt.AWTSequenceEncoder;
import org.minisim.utils.EncoderUtils;
import org.minisim.utils.FileUtils;

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
        final int framesPerSecond = 25;
        System.out.printf("Compression %d frames in a %d fps video\n", totalFrames, framesPerSecond);
        AWTSequenceEncoder encoder =
                EncoderUtils.safeCreateSequenceEncoder(new File("./simulation.mp4"), framesPerSecond);
        final File[] frames = new File(frameDirectory).listFiles();
        assert frames != null;
        Arrays.sort(frames, (x, y) -> x.getName().compareTo(y.getName()));
        for (int i = 0; i < frames.length; i++) {
            System.out.printf("Loading frame %04d / %04d (\"%s\")\n", i, frames.length, frames[i].getName());
            final BufferedImage img = FileUtils.safeRead(frames[i]);
            EncoderUtils.safeEncodeImage(encoder, img);
        }
        EncoderUtils.safeFinish(encoder);
    }
}
