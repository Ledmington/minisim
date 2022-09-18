package org.minisim.utils;

// TODO: maybe improve with reports
public class Profiler {
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
