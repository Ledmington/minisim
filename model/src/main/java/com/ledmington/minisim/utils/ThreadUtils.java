/*
* minisim - Minimalistic N-Body simulation
* Copyright (C) 2022-2024 Filippo Barbari <filippo.barbari@gmail.com>
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
