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
package com.ledmington.minisim.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;

/**
 * This class contains just static methods that wrap some calls to
 * the {@link File} and {@link ImageIO} API.
 */
public final class FileUtils {
    private FileUtils() {}

    /**
     * A wrapper for the {@link ImageIO#write} method to avoid littering the
     * code with try-catches.
     *
     * @param theImage   A BufferedImage ready to be dumped on file.
     * @param extension  The extension/encoding algorithm for the image.
     * @param outputFile A File object where to dump the image content.
     */
    public static void safeWrite(final BufferedImage theImage, final String extension, final File outputFile) {
        try {
            ImageIO.write(theImage, extension, outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * A wrapper for the {@link Files#createTempDirectory} method to avoid littering the
     * code with try-catches.
     * @param directoryName
     *      The name of the temporary directory to be created.
     * @return
     *      The Path that would be returned from a call to {@link Files#createTempDirectory}.
     *      If an {@link IOException} occurs, returns null.
     */
    public static Path safeCreateTempDirectory(final String directoryName) {
        try {
            return Files.createTempDirectory(directoryName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * A wrapper for the {@link ImageIO#read} method to avoid littering the
     * code with try-catches.
     * @param input
     *      The file to be read as image.
     * @return
     *      A BufferedImage representing the given file.
     */
    public static BufferedImage safeRead(final File input) {
        try {
            return ImageIO.read(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
