package minisim.simulation.force;

import java.util.function.BiConsumer;
import minisim.simulation.Body;

public interface Force extends BiConsumer<Body, Body> {}
