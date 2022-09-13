package minisim.simulation;

import java.util.Objects;
import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.ArrayList;

import minisim.simulation.border.Borders;

public class Simulation {

	public static final double NEWTON_GRAVITY = 6.6743e-11;

	public static SimulationBuilder builder() {
		return new SimulationBuilder();
	}

	private static final Random rnd = new Random();

	private final List<Body> bodies = new ArrayList<>();
	private final Borders bounds;
	private final double gravitational_constant;
	private final double friction_constant;

	public Simulation(final int nBodies, final Borders b, final double G, final double friction) {
		if (nBodies < 0) {
			throw new IllegalArgumentException("Can't have negative bodies");
		}
		Objects.requireNonNull(b);
		if (G <= 0) {
			throw new IllegalArgumentException("The gravitational constant needs to be strictly positive");
		}
		if (friction < 0 || friction > 1) {
			throw new IllegalArgumentException("THe friction constant needs to be between 0 and 1");
		}

		bounds = b;
		for (int i = 0; i < nBodies; i++) {
			final Body body = new Body(
					// V2(randab(0, bounds->w), randab(0, bounds->h)),
					new V2(rnd.nextDouble(bounds.w * 0.4, bounds.w * 0.6),
							rnd.nextDouble(bounds.h * 0.4, bounds.h * 0.6)),
					new V2(0, 0), 1, 1);
			bodies.add(body);
		}
		gravitational_constant = G;
		friction_constant = friction;
	}

	public void addBody(final Body b) {
		bodies.add(b);
	}

	public List<Body> getBodies() {
		return bodies;
	}

	// TODO: move this inside body and change visibility of fields
	public void updateBody(final int i) {
		final Body b = bodies.get(i);
		b.acc = b.force.div(b.mass);// * DT; // TODO fix later
		b.speed = b.speed.add(b.acc);
		b.position = b.position.add(b.speed);

		b.force = V2.ORIGIN;

		bounds.accept(b);
	}

	public void computeForceBetweenBodies(final Body first, final Body second) {
		// vector pointing first (but centered in origin)
		final V2 diff = first.position.sub(second.position);
		final double distance = first.dist(second);
		final double force = friction_constant * gravitational_constant * first.mass * second.mass
				/ (distance * distance);
		first.force = first.force.sub(diff.mul(force));
		second.force = second.force.sub(diff.mul(force));
	}

	public void computeForceOnBody(final int i) {
		final Body body = bodies.get(i);
		bodies.subList(i + 1, bodies.size()).forEach(b -> computeForceBetweenBodies(body, b));
	}

	/*
	 * Returns true if it has found some collisions. False otherwise.
	 */
	public boolean detectAndResolveCollisions() {
		boolean foundCollisions = false;
		final int n = bodies.size();
		for (int i = 0; i < n; i++) {
			final Body first = bodies.get(i);
			for (int j = i + 1; j < n; j++) {
				final Body second = bodies.get(j);

				if (first.collidesWith(second)) {
					// we have found a collision
					foundCollisions = true;

					// vector pointing first (but centered in origin)
					V2 diff = first.position.sub(second.position);

					/*
					 * Computing the magnitude of the movement as result of this system. R1 = a + b;
					 * R2 = b + c; compenetration = a + b + c we want to find b because it
					 * represents the distance to be covered in order to avoid the collision. If b
					 * was zero, we would have no collisions. b can be as high as min(R1, R2). The
					 * resulting formula is b = R1 + R2 - compenetration = (a+b) + (b+c) - (a+b+c)
					 */
					final double compenetration = diff.mod();
					final double b = first.radius + second.radius - compenetration;

					first.position = first.position.add(diff.mul(b / 2));
					second.position = second.position.sub(diff.mul(b / 2));
				}
			}
		}
		return foundCollisions;
	}

	public void update() {
		// while(detectAndResolveCollisions()) {}
		// TODO: if you do too many iterations, some body will be pushed outside the
		// domain borders
		for (int i = 0; i < 10; i++) {
			if (!detectAndResolveCollisions()) {
				break;
			}
		}

		// compute forces
		for (int i = 0; i < bodies.size(); i++) {
			computeForceOnBody(i);
		}

		// apply forces
		for (int i = 0; i < bodies.size(); i++) {
			updateBody(i);
		}
	}

	public void render(final GraphicsContext gc) {
		gc.clearRect(0, 0, bounds.w, bounds.h);
		gc.setFill(Color.RED);
		bodies.forEach(b -> gc.fillOval(b.position.x, b.position.y, b.radius, b.radius));
	}
}
