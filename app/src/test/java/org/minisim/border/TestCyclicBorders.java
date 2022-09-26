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
        Body b = new Body(new V2(1, 1), V2.origin(), 1, 1);
        cb.accept(b);
        assertEquals(b.position(), new V2(1, 1));
    }

    @Test
    public void outOnRight() {
        Body b = new Body(new V2(11, 1), V2.origin(), 1, 1);
        cb.accept(b);
        assertEquals(b.position(), new V2(1, 1));
    }

    @Test
    public void outOnLeft() {
        Body b = new Body(new V2(-1, 1), V2.origin(), 1, 1);
        cb.accept(b);
        assertEquals(b.position(), new V2(9, 1));
    }

    @Test
    public void outOnTop() {
        Body b = new Body(new V2(1, -1), V2.origin(), 1, 1);
        cb.accept(b);
        assertEquals(b.position(), new V2(1, 9));
    }

    @Test
    public void outOnBottom() {
        Body b = new Body(new V2(1, 11), V2.origin(), 1, 1);
        cb.accept(b);
        assertEquals(b.position(), new V2(1, 1));
    }

    @Test
    public void bottomRightCorner() {
        Body b = new Body(new V2(11, 11), V2.origin(), 1, 1);
        cb.accept(b);
        assertEquals(b.position(), new V2(1, 1));
    }

    @Test
    public void topLeftCorner() {
        Body b = new Body(new V2(-1, -1), V2.origin(), 1, 1);
        cb.accept(b);
        assertEquals(b.position(), new V2(9, 9));
    }
}
