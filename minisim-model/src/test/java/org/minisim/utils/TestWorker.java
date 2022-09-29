package org.minisim.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public final class TestWorker {

    private int tester = 0;
    private final Runnable dumbTask = () -> {
        ThreadUtils.safeSleep(10);
        tester++;
    };
    private Worker w;

    @BeforeEach
    public void setup() {
        w = new Worker(dumbTask);
        tester = 0;
    }

    @AfterEach
    public void teardown() {
        w.join();
    }

    @Test
    public void aNewWorkerIsStopped() {
        assertTrue(w.isStopped());
    }

    @Test
    public void afterStartItIsStarted() {
        w.start();
        assertFalse(w.isStopped());
    }

    @Test
    public void afterStopItIsStopped() {
        w.start();
        ThreadUtils.safeSleep(100);
        w.stop();
        assertTrue(w.isStopped());
        assertTrue(tester > 0);
    }

    @Test
    public void afterJoinItIsStopped() {
        w.start();
        ThreadUtils.safeSleep(100);
        w.join();
        assertTrue(w.isStopped());
        assertTrue(tester > 0);
    }

    @Test
    public void canBeRestarted() {
        w.start();
        ThreadUtils.safeSleep(100);
        w.stop();
        ThreadUtils.safeSleep(100);
        w.start();
        assertFalse(w.isStopped());
    }

    @Test
    public void cannotBeStartedTwice() {
        w.start();
        assertThrows(IllegalThreadStateException.class, () -> w.start());
    }

    @Test
    public void canBeStoppedTwice() {
        w.start();
        ThreadUtils.safeSleep(100);
        w.stop();
        assertTrue(tester > 0);
        w.stop();
        assertTrue(tester > 0);
    }

    @Test
    public void canBeJoinedTwice() {
        w.start();
        ThreadUtils.safeSleep(100);
        w.join();
        assertTrue(tester > 0);
        w.join();
        assertTrue(tester > 0);
    }
}
