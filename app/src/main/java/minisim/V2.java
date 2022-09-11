package minisim;

// A class representing a 2D vector
public class V2 {
	public double x;
	public double y;

	public static final V2 ORIGIN = new V2(0.0, 0.0);

	public V2(final double _x, final double _y) {
		x = _x;
		y = _y;
	}

	public V2() {
		this(0.0, 0.0);
	}

	public V2 add(final V2 other) {
		return new V2(x + other.x, y + other.y);
	}

	public V2 sub(final V2 other) {
		return new V2(x - other.x, y - other.y);
	}

	public V2 mul(final double k) {
		return new V2(x * k, y * k);
	}

	public V2 div(final double k) {
		return new V2(x / k, y / k);
	}

	public double mod() {
		return Math.sqrt(x * x + y * y);
	}

	public double dot(final V2 other) {
		return x * other.x + y * other.y;
	}

	// returns the normalized vector
	public V2 norm() {
		final double m = mod();
		return new V2(x / m, y / m);
	}

	/**
	 * Computes the Euclidean distance between this point and the "other" point.
	 * 
	 * Note: if you don't need the exact distance, use "distsq". It's faster.
	 * 
	 * @param other
	 *            The other point
	 * @return The euclidean distance
	 */
	public double dist(final V2 other) {
		// TODO: can be optimized without calling sub (because it creates a new
		// temporary V2)
		return sub(other).mod();
	}

	/**
	 * Computes the square of the Euclidean distance between this point and the
	 * "other" point.
	 * 
	 * Note: this is faster than calling "dist".
	 * 
	 * @param other
	 *            The other point
	 * @return The euclidean distance
	 */
	public double distsq(final V2 other) {
		V2 diff = sub(other);
		return diff.x * diff.x + diff.y * diff.y;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!other.getClass().equals(V2.class)) {
			return false;
		}
		final V2 otherVector = (V2) other;
		return x == otherVector.x && y == otherVector.y;
	}

	public boolean equals(final V2 other, final double epsilon) {
		return Math.abs(x - other.x) < epsilon && Math.abs(y - other.y) < epsilon;
	}

	public String toString() {
		return "V2(" + x + ", " + y + ")";
	}
}
