package org.minisim.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;

// TODO: write doc
public final class LoggerUtils {
    private LoggerUtils() {}

    // TODO: write doc
    public static void safeReadConfiguration(final LogManager logManager, final InputStream configurationStream) {
        try {
            logManager.readConfiguration(configurationStream);
        } catch (SecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
