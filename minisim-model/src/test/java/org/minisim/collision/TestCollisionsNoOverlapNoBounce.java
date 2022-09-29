package org.minisim.collision;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.minisim.simulation.V2;
import org.minisim.simulation.body.Body;
import org.minisim.simulation.collision.CollisionManager;

public final class TestCollisionsNoOverlapNoBounce {

    private final List<Body> bodies = new LinkedList<>();
    private CollisionManager cm;

    @BeforeEach
    public void setup() {
        cm = new CollisionManager(false, false);
    }

    @AfterEach
    public void teardown() {
        bodies.clear();
    }

    @Test
    public void canDetectCollisions() {
        bodies.add(new Body(new V2(1, 1), new V2(0, 0), 1, 1));
        bodies.add(new Body(new V2(2, 2), new V2(0, 0), 1, 1));
        assertTrue(cm.detectAndResolveCollisions(bodies));
    }

    @Test
    public void noCollisions() {
        bodies.add(new Body(new V2(1, 1), new V2(0, 0), 1, 1));
        bodies.add(new Body(new V2(3, 3), new V2(0, 0), 1, 1));
        assertFalse(cm.detectAndResolveCollisions(bodies));
    }

    @Test
    public void canResolveCollisions() {
        final Body first = new Body(new V2(1, 2), new V2(0, 0), 1, 1);
        final Body second = new Body(new V2(2, 2), new V2(0, 0), 1, 1);
        bodies.add(first);
        bodies.add(second);
        assertTrue(cm.detectAndResolveCollisions(bodies));
        assertFalse(cm.detectAndResolveCollisions(bodies));
        assertEquals(new V2(0.5, 2), first.position());
        assertEquals(new V2(2.5, 2), second.position());
    }

    @Test
    public void canResolveCollisionsDifferentMasses() {
        // this test is a copy-paste of "can_resolve_collisions" because mass
        // must not interfere with collisions
        final Body first = new Body(new V2(1, 2), new V2(0, 0), 2, 1);
        final Body second = new Body(new V2(2, 2), new V2(0, 0), 3, 1);
        bodies.add(first);
        bodies.add(second);
        assertTrue(cm.detectAndResolveCollisions(bodies));
        assertFalse(cm.detectAndResolveCollisions(bodies));
        assertEquals(new V2(0.5, 2), first.position());
        assertEquals(new V2(2.5, 2), second.position());
    }

    @Test
    public void canResolveCollisionsDifferentRadii() {
        final Body first = new Body(new V2(4, 4), new V2(0, 0), 1, 2);
        final Body second = new Body(new V2(5, 4), new V2(0, 0), 1, 3);
        bodies.add(first);
        bodies.add(second);
        assertTrue(cm.detectAndResolveCollisions(bodies));
        assertFalse(cm.detectAndResolveCollisions(bodies));
        assertEquals(new V2(2, 4), first.position());
        assertEquals(new V2(7, 4), second.position());
    }
}
