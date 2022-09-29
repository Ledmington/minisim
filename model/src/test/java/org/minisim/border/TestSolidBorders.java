package org.minisim.border;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.minisim.simulation.V2;
import org.minisim.simulation.body.Body;
import org.minisim.simulation.border.Borders;
import org.minisim.simulation.border.SolidBorders;

public final class TestSolidBorders {

    private Borders sb;

    @BeforeEach
    public void setup() {
        sb = new SolidBorders(10, 10);
    }

    @Test
    public void illegalWidth() {
        assertThrows(IllegalArgumentException.class, () -> new SolidBorders(0, 10));
        assertThrows(IllegalArgumentException.class, () -> new SolidBorders(-1, 10));
    }

    @Test
    public void illegalHeight() {
        assertThrows(IllegalArgumentException.class, () -> new SolidBorders(10, 0));
        assertThrows(IllegalArgumentException.class, () -> new SolidBorders(10, -1));
    }

    @Test
    public void noChangesIfInside() {
        final Body b = Body.builder().position(1, 1).speed(1, 2).build();
        final V2 oldSpeed = b.speed().copy();
        sb.accept(b);
        assertEquals(b.position(), V2.of(1, 1));
        assertEquals(b.speed(), oldSpeed);
    }

    @Test
    public void cornersAreInside() {
        for (Double x : List.of(Borders.LEFT_BORDER, sb.RIGHT_BORDER)) {
            for (Double y : List.of(Borders.BOTTOM_BORDER, sb.UP_BORDER)) {
                final Body b = Body.builder().position(x, y).speed(1, 2).build();
                final V2 oldSpeed = b.speed().copy();
                sb.accept(b);
                assertEquals(b.position(), V2.of(x, y));
                assertEquals(b.speed(), oldSpeed);
            }
        }
    }

    @Test
    public void outOnRight() {
        final Body b = Body.builder().position(11, 1).speed(1, 2).build();
        final V2 oldSpeed = b.speed().copy();
        sb.accept(b);
        assertEquals(b.position(), V2.of(10, 1));
        assertEquals(b.speed().x(), -oldSpeed.x(), 1e-12);
        assertEquals(b.speed().y(), oldSpeed.y(), 1e-12);
    }

    @Test
    public void outOnLeft() {
        final Body b = Body.builder().position(-1, 1).speed(1, 2).build();
        final V2 oldSpeed = b.speed().copy();
        sb.accept(b);
        assertEquals(b.position(), V2.of(0, 1));
        assertEquals(b.speed().x(), -oldSpeed.x(), 1e-12);
        assertEquals(b.speed().y(), oldSpeed.y(), 1e-12);
    }

    @Test
    public void outOnTop() {
        final Body b = Body.builder().position(1, -1).speed(1, 2).build();
        final V2 oldSpeed = b.speed().copy();
        sb.accept(b);
        assertEquals(b.position(), V2.of(1, 0));
        assertEquals(b.speed().x(), oldSpeed.x(), 1e-12);
        assertEquals(b.speed().y(), -oldSpeed.y(), 1e-12);
    }

    @Test
    public void outOnBottom() {
        final Body b = Body.builder().position(1, 11).speed(1, 2).build();
        final V2 oldSpeed = b.speed().copy();
        sb.accept(b);
        assertEquals(b.position(), V2.of(1, 10));
        assertEquals(b.speed().x(), oldSpeed.x(), 1e-12);
        assertEquals(b.speed().y(), -oldSpeed.y(), 1e-12);
    }

    @Test
    public void bottomRightCorner() {
        final Body b = Body.builder().position(11, 11).speed(1, 2).build();
        final V2 oldSpeed = b.speed().copy();
        sb.accept(b);
        assertEquals(b.position(), V2.of(10, 10));
        assertEquals(b.speed(), oldSpeed.mul(-1));
    }

    @Test
    public void topLeftCorner() {
        final Body b = Body.builder().position(-1, -1).speed(1, 2).build();
        final V2 oldSpeed = b.speed().copy();
        sb.accept(b);
        assertEquals(b.position(), V2.of(0, 0));
        assertEquals(b.speed(), oldSpeed.mul(-1));
    }
}
