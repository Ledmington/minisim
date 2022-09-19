package org.minisim.utils;

import java.util.concurrent.atomic.AtomicBoolean;

public class Worker {

    private static int ID = 0;

    private final Thread thread;
    private final String name;
    private final Runnable task;
    private AtomicBoolean stopped = new AtomicBoolean(true);

    public Worker(final String name, final Runnable task) {
        this.name = name;
        this.task = task;

        thread = new Thread(
                () -> {
                    while (!stopped.get()) {
                        task.run();
                    }
                },
                name);
    }

    public Worker(final Runnable task) {
        this("worker-" + Worker.ID, task);
        Worker.ID++;
    }

    public void start() {
        stopped.set(false);
        thread.start();
    }

    public void stop() {
        stopped.set(true);
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isStopped() {
        return stopped.get();
    }

    public void join() {
        if (!stopped.get()) {
            stop();
        }
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
