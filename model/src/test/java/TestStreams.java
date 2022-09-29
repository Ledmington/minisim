import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.minisim.utils.Pair;

// TODO: these tests exists just to test performance, delete it when no more needed
public final class TestStreams {
    // @Test // uncomment to execute
    public void mammamia() {
        // This test checks what stream is faster to iterate over unique pairs of
        // coordinates
        final int n = 10;

        final Supplier<Stream<Pair<Integer, Integer>>> bruteForceAndFilter = () -> Stream.iterate(0, i -> i + 1)
                .limit(n)
                .flatMap(x -> Stream.iterate(0, i -> i + 1).limit(n).map(i -> new Pair<>(x, i)))
                .filter(p -> p.first() < p.second());

        final Supplier<Stream<Pair<Integer, Integer>>> lessIterations = () -> Stream.iterate(0, i -> i + 1)
                .limit(n)
                .flatMap(x -> Stream.iterate(0, i -> i + 1).limit(x).map(i -> new Pair<>(x, i)));

        long start = System.nanoTime();
        bruteForceAndFilter.get().count();
        System.out.println(System.nanoTime() - start);

        start = System.nanoTime();
        lessIterations.get().count();
        System.out.println(System.nanoTime() - start);

        assertEquals(bruteForceAndFilter.get().count(), lessIterations.get().count());
        final Set<Pair<Integer, Integer>> first = bruteForceAndFilter.get().collect(Collectors.toSet());
        final Set<Pair<Integer, Integer>> second = lessIterations.get().collect(Collectors.toSet());

        assertTrue(first.containsAll(second));
        assertTrue(second.containsAll(first));
    }
}
