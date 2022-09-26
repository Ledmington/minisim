package org.minisim.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * This class contains just static methods that wrap some calls to
 * the {@link File} API.
 */
public final class FileUtils {
    private FileUtils() {}

    /**
     * A wrapper for the {@link ImageIO#write} method to avoid littering the
     * code with try-catches.
     * @param theImage
     *      A BufferedImage ready to be dumped on file.
     * @param extension
     *      The extension/encoding algorithm for the image.
     * @param outputFile
     *      A File object where to dump the image content.
     * @return
     *      The same boolean returned from {@link ImageIO#write}. If an {@link IOException} occurs, returns false.
     */
    public static boolean safeWrite(final BufferedImage theImage, final String extension, final File outputFile) {
        try {
            return ImageIO.write(theImage, extension, outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
