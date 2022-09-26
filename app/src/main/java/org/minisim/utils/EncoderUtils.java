package org.minisim.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import org.jcodec.api.awt.AWTSequenceEncoder;

// TODO: maybe convert this to a SafeAWTSequenceEncoder
/**
 * This class contains just static methods that wrap some calls to
 * the jcodec's {@link AWTSequenceEncoder} API.
 */
public final class EncoderUtils {
    private EncoderUtils() {}

    /**
     * A wrapper for the {@link AWTSequenceEncoder#createSequenceEncoder} method to avoid littering the
     * code with try-catches.
     * @param videoFile
     *      The new file that will contain the video.
     * @param framesPerSecond
     *      Number of frames to be rendered each second.
     * @return
     *      A new {@link AWTSequenceEncoder} with the given settings.
     */
    public static AWTSequenceEncoder safeCreateSequenceEncoder(final File videoFile, final int framesPerSecond) {
        try {
            return AWTSequenceEncoder.createSequenceEncoder(videoFile, framesPerSecond);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * A wrapper for the {@link AWTSequenceEncoder#encodeImage} method to avoid littering the
     * code with try-catches.
     * @param encoder
     *      The {@link AWTSequenceEncoder} object to call "encodeImage" on.
     * @param image
     *      The {@link BufferedImage} to be encoded in the video.
     */
    public static void safeEncodeImage(final AWTSequenceEncoder encoder, final BufferedImage image) {
        try {
            encoder.encodeImage(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * A wrapper for the {@link AWTSequenceEncoder#finish} method to avoid littering the
     * code with try-catches.
     * @param encoder
     *      The {@link AWTSequenceEncoder} object to call "finish" on.
     */
    public static void safeFinish(final AWTSequenceEncoder encoder) {
        try {
            encoder.finish();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
