package org.minisim.utils;

/**
 * This class contains just static methods that wrap some calls to
 * the {@link Thread} API.
 */
public final class ThreadUtils {
    private ThreadUtils() {}

    /**
     * A wrapper for the {@link Thread#sleep} function to avoid littering the
     * code with try-catches.
     * @param millis
     *      Number of milliseconds to wait.
     *      It gets passed directly to Thread.sleep.
     */
    public static void safeSleep(final long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * A wrapper for the {@link Thread#join} function to avoid littering the
     * code with try-catches.
     * @param thread
     *      Thread to be join-ed.
     */
    public static void safeJoin(final Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
