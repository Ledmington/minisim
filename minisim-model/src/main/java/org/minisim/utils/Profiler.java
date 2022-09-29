package org.minisim.utils;

/**
 * This class contain some useful static methods to measure execution times.
 */
// TODO: maybe improve with reports
public final class Profiler {
    private Profiler() {}

    /**
     * Measures time of execution of the given task in milliseconds.
     * @param task
     * 		The Runnable to be measured
     * @return
     * 		The time of execution in milliseconds
     */
    public static long profile(Runnable task) {
        final long start = System.nanoTime();
        task.run();
        return (System.nanoTime() - start) / 1_000_000;
    }
}
