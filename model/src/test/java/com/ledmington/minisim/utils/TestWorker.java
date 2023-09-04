/*
* minisim - Minimalistic N-Body simulation
* Copyright (C) 2022-2023 Filippo Barbari <filippo.barbari@gmail.com>
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package com.ledmington.minisim.utils;

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
