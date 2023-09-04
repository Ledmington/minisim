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
        return System.nanoTime() - start;
    }
}
