package minisim.simulation.force;

import minisim.simulation.Body;

import java.util.function.BiConsumer;

public interface Force extends BiConsumer<Body, Body> {}
