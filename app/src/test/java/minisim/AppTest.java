package minisim;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class AppTest {
	@Test
	public void dumb() {
		assertTrue(true);
	}

	@Test
	public void banana() {
		final List<Integer> l = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 3, 4, 2, 5);
		final long n = Stream.iterate(0, i -> i + 1).map(i -> i * i).limit(10)
				.flatMap(
						k -> Stream.iterate(0, x -> x + 1).limit(k).map(i -> i + 1).map(i -> i * i).map(i -> i * 2 + 1))
				.count();
	}
}
