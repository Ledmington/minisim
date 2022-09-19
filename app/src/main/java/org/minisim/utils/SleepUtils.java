package org.minisim.utils;

public class SleepUtils {
    private SleepUtils() {}

    /**
     * A wrapper for the Thread.sleep function to avoid littering the
     * code with try-catches.
     * @param millis
     *      Number of milliseconds to wait.
     *      It gets passed directly to Thread.sleep.
     */
    public static void safeSleep(final long millis) {
        try {
            Thread.sleep(millis);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}