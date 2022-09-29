package org.minisim.border;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.minisim.simulation.V2;
import org.minisim.simulation.body.Body;
import org.minisim.simulation.border.Borders;
import org.minisim.simulation.border.CyclicBorders;

public final class TestCyclicBorders {

    private Borders cb;

    @BeforeEach
    public void setup() {
        cb = new CyclicBorders(10, 10);
    }

    @Test
    public void illegalWidth() {
        assertThrows(IllegalArgumentException.class, () -> new CyclicBorders(0, 10));
        assertThrows(IllegalArgumentException.class, () -> new CyclicBorders(-1, 10));
    }

    @Test
    public void illegalHeight() {
        assertThrows(IllegalArgumentException.class, () -> new CyclicBorders(10, 0));
        assertThrows(IllegalArgumentException.class, () -> new CyclicBorders(10, -1));
    }

    @Test
    public void noChangesIfInside() {
        final Body b = Body.builder().position(1, 1).build();
        cb.accept(b);
        assertEquals(b.position(), V2.of(1, 1));
    }

    @Test
    public void outOnRight() {
        final Body b = Body.builder().position(11, 1).build();
        cb.accept(b);
        assertEquals(b.position(), V2.of(1, 1));
    }

    @Test
    public void outOnLeft() {
        final Body b = Body.builder().position(-1, 1).build();
        cb.accept(b);
        assertEquals(b.position(), V2.of(9, 1));
    }

    @Test
    public void outOnTop() {
        final Body b = Body.builder().position(1, -1).build();
        cb.accept(b);
        assertEquals(b.position(), V2.of(1, 9));
    }

    @Test
    public void outOnBottom() {
        final Body b = Body.builder().position(1, 11).build();
        cb.accept(b);
        assertEquals(b.position(), V2.of(1, 1));
    }

    @Test
    public void bottomRightCorner() {
        final Body b = Body.builder().position(11, 11).build();
        cb.accept(b);
        assertEquals(b.position(), V2.of(1, 1));
    }

    @Test
    public void topLeftCorner() {
        final Body b = Body.builder().position(-1, -1).build();
        cb.accept(b);
        assertEquals(b.position(), V2.of(9, 9));
    }
}
