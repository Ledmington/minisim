package org.minisim.utils;

import java.awt.*;
import java.io.IOException;

/**
 * TODO: write doc
 */
public final class DesktopUtils {
    private DesktopUtils() {}

    /**
     * TODO: write doc
     * @param uri
     */
    public static void safeBrowse(final String uri) {
        try {
            Desktop.getDesktop().browse(URIUtils.safeCreate(uri));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
