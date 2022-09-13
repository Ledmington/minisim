package minisim.simulation.border;

import minisim.simulation.Body;

import java.util.function.Consumer;

public abstract class Borders implements Consumer<Body> {
	public final double w;
	public final double h;

	public Borders(final double width, final double height) {
		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException("Width and height must be strictly positive");
		}
		w = width;
		h = height;
	}
}
