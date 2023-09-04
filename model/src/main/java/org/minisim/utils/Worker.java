package org.minisim.utils;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

/**
 * This class is a wrapper of the {@link Thread} class that allows to pause the execution
 * of an infinite task and to resume it.
 */
public final class Worker {

    private static int ID = 0;
    private static final Logger logger = Logger.getLogger("minisim");

    private static int getAndIncrementID() {
        ID++;
        return ID - 1;
    }

    private Thread thread = null;
    private final String name;
    private final Runnable task;
    private final AtomicBoolean stopped = new AtomicBoolean(true);

    /**
     * Full-fledged constructor of Worker. Creates a Worker, that will execute the given task
     * indefinitely (unless stopped or join-ed), without starting it.
     * @param name
     *      The name of the Worker.
     * @param task
     *      The task to be executed.
     */
    public Worker(final String name, final Runnable task) {
        this.name = name;
        this.task = task;
        createThread();
    }

    private void createThread() {
        thread = new Thread(
                () -> {
                    while (!stopped.get()) {
                        final long ns = Profiler.profile(task);
                        // logger.info(String.format("[%s] executed its task in %.3f ns", name,
                        // (double)ns/1_000_000.0));
                    }
                },
                name);
    }

    /**
     * Constucts a new Worker giving it a default name.
     * @param task
     *      The task to be executed.
     */
    public Worker(final Runnable task) {
        this("worker-" + getAndIncrementID(), task);
    }

    public void start() {
        if (stopped.get()) {
            createThread();
        }
        stopped.set(false);
        thread.start();
    }

    public void stop() {
        stopped.set(true);
    }

    public boolean isStopped() {
        return stopped.get();
    }

    public void join() {
        if (!stopped.get()) {
            stop();
        }
        if (thread != null) {
            ThreadUtils.safeJoin(thread);
            thread = null;
        }
    }
}
