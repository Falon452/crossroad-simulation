package com.falon.crossroad.domain.model;

public enum LaneType {
    LANE_FROM_EAST_TO_WEST_CLOSER_TO_GRASS(28),
    LANE_FROM_EAST_TO_WEST(29),
    LANE_FROM_WEST_TO_EAST(30),
    LANE_FROM_WEST_TO_EAST_CLOSER_TO_GRASS(31),

    LANE_FROM_NORTH_TO_SOUTH_CLOSER_TO_GRASS(28),
    LANE_FROM_NORTH_TO_SOUTH(29),
    LANE_FROM_SOUTH_TO_NORTH(30),
    LANE_FROM_SOUTH_TO_NORTH_CLOSER_TO_GRASS(31);

    private final int index;

    LaneType(int index) {
        this.index = index;
    }

    public int index() {
        return index;
    }
}