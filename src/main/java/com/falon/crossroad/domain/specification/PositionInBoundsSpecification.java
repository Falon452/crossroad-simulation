package com.falon.crossroad.domain.specification;

public class PositionInBoundsSpecification {

    public boolean isSatisfiedBy(int x, int y, int maxX, int maxY) {
        return isPositionInBounds(x, maxX) && isPositionInBounds(y, maxY);
    }

    private boolean isPositionInBounds(int position, int lastIndex) {
        return 0 <= position && position <= lastIndex;
    }
}
