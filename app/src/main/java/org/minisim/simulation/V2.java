package org.minisim.simulation;

import java.util.Objects;

/**
 * A class representing a bidimensional vector.
 */
public final class V2 {
    public double x;
    public double y;

    public static V2 origin() {
        return new V2(0.0, 0.0);
    }

    public V2(final double _x, final double _y) {
        x = _x;
        y = _y;
    }

    public V2() {
        this(0.0, 0.0);
    }

    public V2 add(final V2 other) {
        x += other.x;
        y += other.y;
        return this;
    }

    public V2 sub(final V2 other) {
        x -= other.x;
        y -= other.y;
        return this;
    }

    public V2 mul(final double k) {
        x *= k;
        y *= k;
        return this;
    }

    public V2 div(final double k) {
        x /= k;
        y /= k;
        return this;
    }

    public double mod() {
        return Math.sqrt(x * x + y * y);
    }

    public double dot(final V2 other) {
        return x * other.x + y * other.y;
    }

    /**
     * Normalizes the V2 vector and returns it.
     *
     * @return The normalized vector
     */
    public V2 norm() {
        final double m = mod();
        return new V2(x / m, y / m);
    }

    /**
     * Computes the Euclidean distance between this point and the "other" point.
     *
     * @param other
     *            The other point
     * @return The euclidean distance
     */
    public double dist(final V2 other) {
        // return copy().sub(other).mod(); // uses a temporary V2 (but it's elegant)
        return Math.hypot(x - other.x, y - other.y);
    }

    public V2 copy() {
        return new V2(x, y);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || !other.getClass().equals(this.getClass())) {
            return false;
        }
        final V2 otherVector = (V2) other;
        return x == otherVector.x && y == otherVector.y;
    }

    public boolean equals(final V2 other, final double epsilon) {
        return Math.abs(x - other.x) < epsilon && Math.abs(y - other.y) < epsilon;
    }

    @Override
    public String toString() {
        return "V2(" + x + "," + y + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
