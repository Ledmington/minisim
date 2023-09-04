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
package com.ledmington.minisim.simulation;

/**
 * A class representing a bidimensional vector.
 */
public record V2(double x, double y) {

    /**
     * Less verbose static method that replaces the constructor.
     * @param x
     *      The first coordinate.
     * @param y
     *      The second coordinate.
     * @return
     *      A new V2(x,y).
     */
    public static V2 of(final double x, final double y) {
        return new V2(x, y);
    }

    /**
     * Returns a new V2 object representing the origin (0,0).
     * @return
     *      V2(0,0)
     */
    public static V2 origin() {
        return new V2(0.0, 0.0);
    }

    /**
     * Adds the content of the given V2 to this one, creating a new V2 with the result.
     * @param other
     *      The V2 to add.
     * @return
     *      A new V2 representing the result.
     */
    public V2 add(final V2 other) {
        return new V2(x + other.x, y + other.y);
    }

    /**
     * Subtracts the content of the given V2 from this one, creating a new V2 with the result.
     * @param other
     *      The V2 to subtract.
     * @return
     *      A new V2 representing the result.
     */
    public V2 sub(final V2 other) {
        return new V2(x - other.x, y - other.y);
    }

    /**
     * Multiplies this V2 by the given scalar k.
     * @param k
     *      The scalar to multiply.
     * @return
     *      A new V2 representing the result.
     */
    public V2 mul(final double k) {
        return new V2(x * k, y * k);
    }

    /**
     * Divides this V2 by the given scalar k.
     * @param k
     *      The scalar to divide by.
     * @return
     *      A new V2 representing the result.
     */
    public V2 div(final double k) {
        return new V2(x / k, y / k);
    }

    /**
     * Computes the modulo of this V2.
     * @return
     *      The modulo of the vector.
     */
    public double mod() {
        return Math.hypot(x, y);
    }

    /**
     * Computes the dot product with given V2 object.
     * @param other
     *      The V2 to dot multiply with.
     * @return
     *      The dot product of the two V2s.
     */
    public double dot(final V2 other) {
        return x * other.x + y * other.y;
    }

    /**
     * Computes the normalized vector.
     * @return
     *      A new V2 representing the normalized version of this one.
     */
    public V2 norm() {
        final double m = mod();
        return new V2(x / m, y / m);
    }

    /**
     * Computes the Euclidean distance between this point and the "other" point.
     * @param other
     *      The other point.
     * @return
     *      The euclidean distance between the two.
     */
    public double dist(final V2 other) {
        // return copy().sub(other).mod(); // uses a temporary V2 (but it's elegant)
        return Math.hypot(x - other.x, y - other.y);
    }

    /**
     * Creates a new V2 with the same first and second values inside.
     * This means that, if we have a V2 named 'v':
     * <pre>
     * v == v.copy() is false
     * v.equals(v.copy()) is true
     * </pre>
     * @return
     *      A new V2 with the same contents.
     */
    public V2 copy() {
        return new V2(x, y);
    }
}
