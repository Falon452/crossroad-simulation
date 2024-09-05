package com.falon.crossroad.domain.model;

import static com.falon.crossroad.domain.model.LaneType.*;

public enum TrafficPositionType {
    EAST_TO_WEST_CLOSER_TO_GRASS(
        LANE_FROM_SOUTH_TO_NORTH_CLOSER_TO_GRASS.index() + OffsetFromCrossroad.SHORT,
        LANE_FROM_EAST_TO_WEST_CLOSER_TO_GRASS.index()
    ),
    EAST_TO_WEST(
        LANE_FROM_SOUTH_TO_NORTH_CLOSER_TO_GRASS.index() + OffsetFromCrossroad.SHORT,
        LANE_FROM_EAST_TO_WEST.index()
    ),
    WEST_TO_EAST(
        LANE_FROM_NORTH_TO_SOUTH_CLOSER_TO_GRASS.index() - OffsetFromCrossroad.SHORT,
        LANE_FROM_WEST_TO_EAST.index()
    ),
    WEST_TO_EAST_CLOSER_TO_GRASS(
        LANE_FROM_NORTH_TO_SOUTH_CLOSER_TO_GRASS.index() - OffsetFromCrossroad.SHORT,
        LANE_FROM_WEST_TO_EAST_CLOSER_TO_GRASS.index()
    ),

    NORTH_TO_SOUTH_CLOSER_TO_GRASS(
        LANE_FROM_NORTH_TO_SOUTH_CLOSER_TO_GRASS.index(),
        LANE_FROM_EAST_TO_WEST_CLOSER_TO_GRASS.index() - OffsetFromCrossroad.SHORT
    ),
    NORTH_TO_SOUTH(
        LANE_FROM_NORTH_TO_SOUTH.index(),
        LANE_FROM_EAST_TO_WEST_CLOSER_TO_GRASS.index() - OffsetFromCrossroad.SHORT
    ),
    SOUTH_TO_NORTH(
        LANE_FROM_SOUTH_TO_NORTH.index(),
        LANE_FROM_WEST_TO_EAST_CLOSER_TO_GRASS.index() + OffsetFromCrossroad.SHORT
    ),
    SOUTH_TO_NORTH_CLOSER_TO_GRASS(
        LANE_FROM_SOUTH_TO_NORTH_CLOSER_TO_GRASS.index(),
        LANE_FROM_WEST_TO_EAST_CLOSER_TO_GRASS.index() + OffsetFromCrossroad.SHORT
    );

    public final int x;
    public final int y;

    TrafficPositionType(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
