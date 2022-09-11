package minisim;

public class Body {
	private double mass;
	private double radius;
	private V2 position;
	private V2 speed;
	private V2 acc;
	private V2 force;

	public Body(final V2 pos, final V2 speed, final double mass, final double radius) {
		if (mass <= 0.0) {
			throw new IllegalArgumentException("Mass can't be negative or zero");
		}
		if (radius <= 0.0) {
			throw new IllegalArgumentException("Radius can't be negative or zero");
		}

		// circle = sf::CircleShape(radius);
		// circle.setFillColor(sf::Color::Red);
		// circle.setRadius(radius);
		this.mass = mass;
		this.radius = radius;
		this.position = pos;
		this.speed = speed;
		this.acc = new V2(0, 0);
		this.force = new V2(0, 0);
	}

	public Body() {
		this(new V2(0.0, 0.0), new V2(0.0, 0.0), 1.0, 1.0);
	}

	public double mass() {
		return mass;
	}

	public double radius() {
		return radius;
	}

	public V2 position() {
		return position;
	}

	public V2 speed() {
		return speed;
	}

	public V2 acceleration() {
		return acc;
	}

	public V2 force() {
		return force;
	}

	public double dist(final Body other) {
		return position.dist(other.position);
	}

	public boolean collidesWith(final Body other) {
		final double Rsum = radius + other.radius;
		if (Math.abs(position.x() - other.position.x()) > Rsum) {
			return false;
		}
		if (Math.abs(position.y() - other.position.y()) > Rsum) {
			return false;
		}
		return position.distsq(other.position) < Rsum * Rsum; // faster way (maybe): doesn't use a sqrt
		// return dist(other) < Rsum; <-- correct way
	}
}
