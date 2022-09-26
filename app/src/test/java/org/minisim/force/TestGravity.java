package org.minisim.force;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.minisim.simulation.V2;
import org.minisim.simulation.body.Body;
import org.minisim.simulation.force.Gravity;

public final class TestGravity {

    private Gravity gravity;
    private final double EPSILON = 1e-12;

    @BeforeEach
    public void setup() {
        gravity = new Gravity();
    }

    @Test
    public void forceShouldBeEqual() {
        final Body first = Body.builder().position(new V2(3, 5)).build();
        final Body second = Body.builder().position(new V2(-7, 9)).build();
        gravity.accept(first, second);
        assertEquals(first.force().mod(), second.force().mod(), EPSILON);
        assertEquals(first.force().x(), -second.force().x(), EPSILON);
        assertEquals(first.force().y(), -second.force().y(), EPSILON);
    }

    @Test
    public void forceShouldBeEqualWithDifferentMasses() {
        final Body first = Body.builder().position(new V2(3, 5)).mass(2).build();
        final Body second = Body.builder().position(new V2(-7, 9)).mass(3).build();
        gravity.accept(first, second);
        assertEquals(first.force().mod(), second.force().mod(), EPSILON);
        assertEquals(first.force().x(), -second.force().x(), EPSILON);
        assertEquals(first.force().y(), -second.force().y(), EPSILON);
    }

    @Test
    public void forceShouldBeEqualWithDifferentRadii() {
        final Body first = Body.builder().position(new V2(3, 5)).radius(2).build();
        final Body second = Body.builder().position(new V2(-7, 9)).radius(3).build();
        gravity.accept(first, second);
        assertEquals(first.force().mod(), second.force().mod(), EPSILON);
        assertEquals(first.force().x(), -second.force().x(), EPSILON);
        assertEquals(first.force().y(), -second.force().y(), EPSILON);
    }

    @Test
    public void distanceShouldDecrease() {
        final Body first = Body.builder().position(new V2(3, 5)).build();
        final Body second = Body.builder().position(new V2(-7, 9)).build();

        final double initialDistance = first.dist(second);

        gravity.accept(first, second);
        first.applyForce();
        second.applyForce();

        final double finalDistance = first.dist(second);

        assertTrue(finalDistance < initialDistance);
    }

    @Test
    public void heavierObjectsShouldProduceStrongerForce() {
        final Body left = Body.builder().position(new V2(-6, 5)).build();
        final Body middle = Body.builder().position(new V2(0, 5)).mass(2).build();
        final Body rightAndHeavy = Body.builder().position(new V2(6, 5)).mass(6).build();

        gravity.accept(left, middle);

        final double lighterForce = middle.force().mod();

        middle.setForce(V2.origin());

        gravity.accept(middle, rightAndHeavy);

        final double heavyForce = middle.force().mod();

        assertEquals(lighterForce * rightAndHeavy.mass(), heavyForce, EPSILON);
    }

    @Test
    public void furtherObjectsShouldProduceWeakerForce() {
        final Body left = Body.builder().position(new V2(-1, 5)).build();
        final Body middle = Body.builder().position(new V2(0, 5)).build();
        final Body rightAndFurther = Body.builder().position(new V2(2, 5)).build();

        gravity.accept(left, middle);

        final double closerForce = middle.force().mod();

        middle.setForce(V2.origin());

        gravity.accept(middle, rightAndFurther);

        final double furtherForce = middle.force().mod();

        assertEquals(closerForce, furtherForce * 4, EPSILON);
    }

    @Test
    public void twoBodies() {
        final double up = 40;
        final double down = -40;
        final double left = -30;
        final double right = 30;

        final Body first = Body.builder().position(new V2(left, down)).build();
        final Body second = Body.builder().position(new V2(right, up)).build();

        gravity.accept(first, second);
        first.applyForce();
        second.applyForce();

        assertTrue(first.position().x() > left);
        assertTrue(second.position().x() < right);

        assertTrue(first.position().y() > down);
        assertTrue(second.position().y() < up);
    }
}
