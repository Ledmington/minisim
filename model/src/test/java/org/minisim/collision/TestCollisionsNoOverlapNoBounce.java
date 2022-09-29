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
        bodies.add(Body.builder().position(1, 1).build());
        bodies.add(Body.builder().position(2, 2).build());
        assertTrue(cm.detectAndResolveCollisions(bodies));
    }

    @Test
    public void noCollisions() {
        bodies.add(Body.builder().position(1, 1).build());
        bodies.add(Body.builder().position(3, 3).build());
        assertFalse(cm.detectAndResolveCollisions(bodies));
    }

    @Test
    public void canResolveCollisions() {
        final Body first = Body.builder().position(1, 2).build();
        final Body second = Body.builder().position(2, 2).build();
        bodies.add(first);
        bodies.add(second);
        assertTrue(cm.detectAndResolveCollisions(bodies));
        assertFalse(cm.detectAndResolveCollisions(bodies));
        assertEquals(V2.of(0.5, 2), first.position());
        assertEquals(V2.of(2.5, 2), second.position());
    }

    @Test
    public void canResolveCollisionsDifferentMasses() {
        // this test is a copy-paste of "can_resolve_collisions" because mass
        // must not interfere with collisions
        final Body first = Body.builder().position(1, 2).mass(2).build();
        final Body second = Body.builder().position(2, 2).mass(3).build();
        bodies.add(first);
        bodies.add(second);
        assertTrue(cm.detectAndResolveCollisions(bodies));
        assertFalse(cm.detectAndResolveCollisions(bodies));
        assertEquals(V2.of(0.5, 2), first.position());
        assertEquals(V2.of(2.5, 2), second.position());
    }

    @Test
    public void canResolveCollisionsDifferentRadii() {
        final Body first = Body.builder().position(4, 4).radius(2).build();
        final Body second = Body.builder().position(5, 4).radius(3).build();
        bodies.add(first);
        bodies.add(second);
        assertTrue(cm.detectAndResolveCollisions(bodies));
        assertFalse(cm.detectAndResolveCollisions(bodies));
        assertEquals(V2.of(2, 4), first.position());
        assertEquals(V2.of(7, 4), second.position());
    }
}
