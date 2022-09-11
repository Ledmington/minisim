package minisim.border;

import minisim.Body;

public abstract class Borders {
	public final double w;
	public final double h;

	public Borders(final double width, final double height) {
		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException("Width and height must be strictly positive");
		}
		w = width;
		h = height;
	}

	public abstract void apply(final Body b);
}
