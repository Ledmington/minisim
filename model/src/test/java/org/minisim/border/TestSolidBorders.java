package org.minisim.border;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.minisim.simulation.V2;
import org.minisim.simulation.body.Body;
import org.minisim.simulation.border.Borders;
import org.minisim.simulation.border.SolidBorders;

public final class TestSolidBorders {

    private static Borders sb;
    private final double width = 10;
    private final double height = 10;

    @BeforeEach
    public void setup() {
        sb = new SolidBorders(width, height);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1, 0})
    public void illegalWidth(double w) {
        assertThrows(IllegalArgumentException.class, () -> new SolidBorders(w, 10));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1, 0})
    public void illegalHeight(double h) {
        assertThrows(IllegalArgumentException.class, () -> new SolidBorders(10, h));
    }

    @ParameterizedTest
    @ValueSource(doubles = {1, 2, 3, 4, 5, 6, 7, 8, 9})
    public void noChangesIfInside(double n) {
        final Body b = Body.builder().position(n, n).speed(1, 2).build();
        final V2 oldSpeed = b.speed().copy();
        sb.accept(b);
        assertEquals(V2.of(n, n), b.position());
        assertEquals(oldSpeed, b.speed());
    }

    private static Stream<Arguments> corners() {
        return Stream.of(Borders.LEFT_BORDER, sb.RIGHT_BORDER)
                .flatMap(x -> Stream.of(Arguments.of(x, Borders.BOTTOM_BORDER), Arguments.of(x, sb.UP_BORDER)));
    }

    @ParameterizedTest
    @MethodSource("corners")
    public void cornersAreInside(double xBorder, double yBorder) {
        final Body b = Body.builder().position(xBorder, yBorder).speed(1, 2).build();
        final V2 oldSpeed = b.speed().copy();
        sb.accept(b);
        assertEquals(b.position(), V2.of(xBorder, yBorder));
        assertEquals(b.speed(), oldSpeed);
    }

    @ParameterizedTest
    @ValueSource(doubles = {11, 22, 33, 44, 55, 66, 77, 88, 99})
    public void outOnRight(double x) {
        final Body b = Body.builder().position(x, 1).speed(1, 2).build();
        final V2 oldSpeed = b.speed().copy();
        sb.accept(b);
        assertEquals(b.position(), V2.of(width, 1));
        assertEquals(b.speed().x(), -oldSpeed.x(), 1e-12);
        assertEquals(b.speed().y(), oldSpeed.y(), 1e-12);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1, -11, -22, -33, -44, -55, -66, -77, -88, -99})
    public void outOnLeft(double x) {
        final Body b = Body.builder().position(x, 1).speed(1, 2).build();
        final V2 oldSpeed = b.speed().copy();
        sb.accept(b);
        assertEquals(b.position(), V2.of(0, 1));
        assertEquals(b.speed().x(), -oldSpeed.x(), 1e-12);
        assertEquals(b.speed().y(), oldSpeed.y(), 1e-12);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1, -11, -22, -33, -44, -55, -66, -77, -88, -99})
    public void outOnTop(double y) {
        final Body b = Body.builder().position(1, y).speed(1, 2).build();
        final V2 oldSpeed = b.speed().copy();
        sb.accept(b);
        assertEquals(b.position(), V2.of(1, 0));
        assertEquals(b.speed().x(), oldSpeed.x(), 1e-12);
        assertEquals(b.speed().y(), -oldSpeed.y(), 1e-12);
    }

    @ParameterizedTest
    @ValueSource(doubles = {11, 22, 33, 44, 55, 66, 77, 88, 99})
    public void outOnBottom(double y) {
        final Body b = Body.builder().position(1, y).speed(1, 2).build();
        final V2 oldSpeed = b.speed().copy();
        sb.accept(b);
        assertEquals(b.position(), V2.of(1, height));
        assertEquals(b.speed().x(), oldSpeed.x(), 1e-12);
        assertEquals(b.speed().y(), -oldSpeed.y(), 1e-12);
    }

    @ParameterizedTest
    @ValueSource(doubles = {11, 22, 33, 44, 55, 66, 77, 88, 99})
    public void bottomRightCorner(double n) {
        final Body b = Body.builder().position(n, n).speed(1, 2).build();
        final V2 oldSpeed = b.speed().copy();
        sb.accept(b);
        assertEquals(b.position(), V2.of(width, height));
        assertEquals(b.speed(), oldSpeed.mul(-1));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1, -11, -22, -33, -44, -55, -66, -77, -88, -99})
    public void topLeftCorner(double n) {
        final Body b = Body.builder().position(n, n).speed(1, 2).build();
        final V2 oldSpeed = b.speed().copy();
        sb.accept(b);
        assertEquals(b.position(), V2.of(0, 0));
        assertEquals(b.speed(), oldSpeed.mul(-1));
    }
}
