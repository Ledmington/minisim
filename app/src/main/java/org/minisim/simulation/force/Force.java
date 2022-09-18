package org.minisim.simulation.force;

import java.util.function.BiConsumer;
import org.minisim.simulation.Body;

public interface Force extends BiConsumer<Body, Body> {}
