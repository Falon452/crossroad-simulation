package com.falon.crossroad.domain.specification;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class PositionInBoundsSpecificationTest {

    private final PositionInBoundsSpecification positionInBoundsSpecification = new PositionInBoundsSpecification();

    @Test
    void whenPositionIsWithinBounds_ThenReturnsTrue() {
        // GIVEN
        int x = 5;
        int y = 7;
        int maxX = 10;
        int maxY = 10;

        // WHEN
        boolean result = positionInBoundsSpecification.isSatisfiedBy(x, y, maxX, maxY);

        // THEN
        assertTrue(result);
    }

    @Test
    void whenXIsOutOfBounds_ThenReturnsFalse() {
        // GIVEN
        int x = 11;
        int y = 5;
        int maxX = 10;
        int maxY = 10;

        // WHEN
        boolean result = positionInBoundsSpecification.isSatisfiedBy(x, y, maxX, maxY);

        // THEN
        assertFalse(result);
    }

    @Test
    void whenYIsOutOfBounds_ThenReturnsFalse() {
        // GIVEN
        int x = 5;
        int y = 11;
        int maxX = 10;
        int maxY = 10;

        // WHEN
        boolean result = positionInBoundsSpecification.isSatisfiedBy(x, y, maxX, maxY);

        // THEN
        assertFalse(result);
    }

    @Test
    void whenBothXAndYAreOutOfBounds_ThenReturnsFalse() {
        // GIVEN
        int x = 11;
        int y = 12;
        int maxX = 10;
        int maxY = 10;

        // WHEN
        boolean result = positionInBoundsSpecification.isSatisfiedBy(x, y, maxX, maxY);

        // THEN
        assertFalse(result);
    }

    @Test
    void whenPositionIsOnTheLowerBoundary_ThenReturnsTrue() {
        // GIVEN
        int x = 0;
        int y = 0;
        int maxX = 10;
        int maxY = 10;

        // WHEN
        boolean result = positionInBoundsSpecification.isSatisfiedBy(x, y, maxX, maxY);

        // THEN
        assertTrue(result);
    }

    @Test
    void whenPositionIsOnTheUpperBoundary_ThenReturnsTrue() {
        // GIVEN
        int x = 10;
        int y = 10;
        int maxX = 10;
        int maxY = 10;

        // WHEN
        boolean result = positionInBoundsSpecification.isSatisfiedBy(x, y, maxX, maxY);

        // THEN
        assertTrue(result);
    }
}
