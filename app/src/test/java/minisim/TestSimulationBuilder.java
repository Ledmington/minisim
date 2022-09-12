package minisim;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSimulationBuilder {
	@Test
	public void noBodiesIfNotSpecified() {
		Simulation sim = Simulation.builder().width(100).height(100).gravity(1e-10).friction(0.99).solidBorders()
				.build();
		assertEquals(0, sim.getBodies().size());
	}

	@Test
	public void correctNumberOfBodies() {
		Simulation sim = Simulation.builder().nBodies(2).width(100).height(100).gravity(1e-10).friction(0.99)
				.solidBorders().build();
		assertEquals(2, sim.getBodies().size());
	}
}
