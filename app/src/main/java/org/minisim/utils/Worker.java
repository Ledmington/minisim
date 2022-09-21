package org.minisim.utils;

import static org.minisim.App.logger;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This class is a wrapper of the {@link Thread} class that allows to pause the execution
 * of an infinite task and to resume it.
 */
public final class Worker {

    private static int ID = 0;

    private Thread thread = null;
    private final String name;
    private final Runnable task;
    private final AtomicBoolean stopped = new AtomicBoolean(true);

    /**
     * Full-fledged constuctor of Worker. Creates a Worker, that will execute the given task
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
                        final long ms = Profiler.profile(task);
                        logger.info(String.format("[%s] executed its task in %d ms", name, ms));
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
        this("worker-" + Worker.ID, task);
        Worker.ID++;
    }

    public void start() {
        if (thread == null || stopped.get()) {
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
