package minisim.v2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;
import minisim.Pair;
import minisim.simulation.V2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class TestV2Equals {

    private static final double EPSILON = 1e-12;

    @Test
    public void vectorEqualsItself() {
        V2 v = new V2(3, 5);
        assertEquals(v, v);
    }

    @Test
    public void vectorDoesNotEqualObject() {
        V2 v = new V2(3, 5);
        assertFalse(v.equals(new Object()));
    }

    @ParameterizedTest
    @MethodSource("provideDifferentV2ForEquals")
    public void twoVectorsWithDifferentCoordinatesShouldNotBeEqual(double x, double y) {
        final V2 v = new V2(3, 5);
        final V2 w = new V2(x, y);
        assertFalse(v.equals(w));
    }

    public static Stream<Arguments> provideDifferentV2ForEquals() {
        return Stream.of(Arguments.of(3, 6), Arguments.of(2, 5), Arguments.of(2, 6));
    }

    @ParameterizedTest
    @MethodSource("provideSlightlyDifferentV2ForEquals")
    public void twoVectorsWithSlightlyDifferentCoordinatesShouldNotBeEqual(double x, double y) {
        final V2 v = new V2(3, 5);
        final V2 w = new V2(x, y);
        assertFalse(v.equals(w, EPSILON));
    }

    public static Stream<Arguments> provideSlightlyDifferentV2ForEquals() {
        return Stream.of(new Pair<>(2, 6), new Pair<>(2, 5), new Pair<>(3, 6))
                .flatMap(p -> Stream.of(
                        Arguments.of(p.x() + EPSILON * 2, p.y()),
                        Arguments.of(p.x() - EPSILON * 2, p.y()),
                        Arguments.of(p.x(), p.y() + EPSILON * 2),
                        Arguments.of(p.x(), p.y() - EPSILON * 2)));
    }

    @Test
    public void twoVectorsWithSameCoordinatesAreTheSameVector() {
        V2 v = new V2(3, 5);
        V2 w = new V2(3, 5);
        assertNotSame(v, w);
        assertEquals(v, w);
        assertTrue(v.equals(w, EPSILON));
    }

    @ParameterizedTest
    @MethodSource("provideSlightlyDifferentV2ForEquals")
    public void twoV2WithAtLeastEpsilonDifferenceAreDifferent(double x, double y) {
        V2 v = new V2(3, 5);
        V2 w = new V2(x, y);
        assertNotSame(v, w);
        assertNotEquals(v, w);
        assertFalse(v.equals(w, EPSILON));
    }

    public static Stream<Arguments> provideV2WithEpsilons() {
        return Stream.of(new Pair<Double, Double>(3.0, 5.0))
                .flatMap(p -> Stream.of(
                        new Pair<>(p.x(), p.y()),
                        new Pair<>(p.x() + EPSILON, p.y()),
                        new Pair<>(p.x() - EPSILON, p.y())))
                .flatMap(p -> Stream.of(
                        new Pair<>(p.x(), p.y()),
                        new Pair<>(p.x(), p.y() + EPSILON),
                        new Pair<>(p.x(), p.y() - EPSILON)))
                .map(p -> Arguments.of(p.x(), p.y()));
    }

    @Test
    public void vectorToString() {
        final V2 v = new V2(3, 5);
        assertEquals("V2(" + v.x + "," + v.y + ")", v.toString());
    }
}
