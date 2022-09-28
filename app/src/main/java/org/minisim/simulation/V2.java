package org.minisim.simulation;

/**
 * A class representing a bidimensional vector.
 */
public record V2(double x, double y) {

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
