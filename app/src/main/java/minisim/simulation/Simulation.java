package minisim.simulation;

import java.util.Objects;
import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.ArrayList;

import minisim.simulation.border.Borders;
import minisim.simulation.force.Force;
import minisim.simulation.force.UnaryForce;

public class Simulation {

	public static SimulationBuilder builder() {
		return new SimulationBuilder();
	}

	private static final Random rnd = new Random();

	private final List<Body> bodies = new ArrayList<>();
	private final List<Force> forces = new ArrayList<>();
	private final List<UnaryForce> unaryForces = new ArrayList<>();
	private final Borders bounds;

	public Simulation(final int nBodies, final Borders b, final List<Force> forces,
			final List<UnaryForce> unaryForces) {
		if (nBodies < 0) {
			throw new IllegalArgumentException("Can't have negative bodies");
		}
		Objects.requireNonNull(b);
		Objects.requireNonNull(forces);
		Objects.requireNonNull(unaryForces);
		this.forces.addAll(forces);
		this.unaryForces.addAll(unaryForces);

		bounds = b;
		for (int i = 0; i < nBodies; i++) {
			final Body body = new Body(new V2(rnd.nextDouble(bounds.RIGHT_BORDER * 0.2, bounds.RIGHT_BORDER * 0.8),
					rnd.nextDouble(bounds.UP_BORDER * 0.2, bounds.UP_BORDER * 0.8)), new V2(0, 0), 1, 1);
			bodies.add(body);
		}
	}

	public void addBody(final Body b) {
		bodies.add(b);
	}

	public List<Body> getBodies() {
		return bodies;
	}

	public void update() {
		// while(detectAndResolveCollisions()) {}
		// TODO: if you do too many iterations, some body will be pushed outside the
		// domain borders
		for (int i = 0; i < 10; i++) {
			if (!CollisionManager.detectAndResolveCollisions(bodies)) {
				break;
			}
		}

		// compute forces
		computeAllForces();

		// apply forces
		for (Body b : bodies) {
			b.applyForce();
			bounds.accept(b);
		}
	}

	private void computeAllForces() {
		// All "double" forces
		for (int i = 0; i < bodies.size(); i++) {
			final Body first = bodies.get(i);
			for (int j = i + 1; j < bodies.size(); j++) {
				final Body second = bodies.get(j);
				for (Force f : forces) {
					f.accept(first, second);
				}
			}
		}

		// All unary forces
		// TODO: highly parallelizable
		for (Body b : bodies) {
			for (UnaryForce uf : unaryForces) {
				uf.accept(b);
			}
		}
	}

	public void render(final GraphicsContext gc) {
		gc.clearRect(0, 0, bounds.RIGHT_BORDER, bounds.UP_BORDER);
		gc.setFill(Color.RED);
		bodies.forEach(b -> gc.fillOval(b.position.x, b.position.y, b.radius, b.radius));
	}
}
