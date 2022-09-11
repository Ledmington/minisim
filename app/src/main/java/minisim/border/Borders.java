package minisim.border;

import minisim.Body;

public abstract class Borders {
	protected final double w;
	protected final double h;

	public Borders(final double width, final double height) {
		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException("Width and height must be strictly positive");
		}
		w = width;
		h = height;
	}

	abstract void apply(final Body b);
}
