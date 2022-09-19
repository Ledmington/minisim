package org.minisim.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TestWorker {

    private final Runnable dumbTask = () -> SleepUtils.safeSleep(10);

    @Test
    public void aNewWorkerIsStopped() {
        final Worker w = new Worker(dumbTask);
        assertTrue(w.isStopped());
    }
}
