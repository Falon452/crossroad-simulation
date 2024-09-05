package com.falon.crossroad.presentation.factory;

import com.falon.crossroad.presentation.state.CrossroadState;
import com.falon.crossroad.domain.model.*;

import java.util.ArrayList;

import static com.falon.crossroad.domain.model.CellsSize.CELLS_IN_HEIGHT;
import static com.falon.crossroad.domain.model.CellsSize.CELLS_IN_WIDTH;
import static com.falon.crossroad.domain.model.DirectionType.*;
import static com.falon.crossroad.domain.model.LaneType.*;
import static com.falon.crossroad.domain.model.TrafficPositionType.*;

public class CrossroadStateFactory {

    public CrossroadState create() {
        CrossroadState state = new CrossroadState();
        state.cells = new Cell[CELLS_IN_WIDTH][CELLS_IN_HEIGHT];
        state.drivers = new ArrayList<>();
        state.trafficLights = new ArrayList<>();

        setGrass(state);
        setStreets(state);
        setSpawningPoints(state);
        setTrafficLights(state);

        return state;
    }

    private static void setSpawningPoints(CrossroadState state) {
        setSpawnPoint(
            state,
            0,
            LANE_FROM_WEST_TO_EAST.index(),
            EAST,
            LANE_FROM_SOUTH_TO_NORTH.index(),
            LANE_FROM_WEST_TO_EAST.index(),
            TurnType.LEFT
        );
        setSpawnPoint(
            state,
            0,
            LANE_FROM_WEST_TO_EAST_CLOSER_TO_GRASS.index(),
            EAST,
            LANE_FROM_NORTH_TO_SOUTH_CLOSER_TO_GRASS.index(),
            LANE_FROM_WEST_TO_EAST_CLOSER_TO_GRASS.index(),
            TurnType.RIGHT
        );

        setSpawnPoint(
            state,
            CELLS_IN_WIDTH - 1,
            LANE_FROM_EAST_TO_WEST.index(),
            WEST,
            LANE_FROM_NORTH_TO_SOUTH.index(),
            LANE_FROM_EAST_TO_WEST.index(),
            TurnType.LEFT
        );
        setSpawnPoint(
            state,
            CELLS_IN_WIDTH - 1,
            LANE_FROM_EAST_TO_WEST_CLOSER_TO_GRASS.index(),
            WEST,
            LANE_FROM_SOUTH_TO_NORTH_CLOSER_TO_GRASS.index(),
            LANE_FROM_EAST_TO_WEST_CLOSER_TO_GRASS.index(),
            TurnType.RIGHT
        );

        setSpawnPoint(
            state,
            LANE_FROM_NORTH_TO_SOUTH_CLOSER_TO_GRASS.index(),
            0,
            SOUTH,
            LANE_FROM_NORTH_TO_SOUTH_CLOSER_TO_GRASS.index(),
            LANE_FROM_EAST_TO_WEST_CLOSER_TO_GRASS.index(),
            TurnType.RIGHT
        );
        setSpawnPoint(
            state,
            LANE_FROM_NORTH_TO_SOUTH.index(),
            0,
            SOUTH,
            LANE_FROM_NORTH_TO_SOUTH.index(),
            LANE_FROM_WEST_TO_EAST.index(),
            TurnType.LEFT
        );

        setSpawnPoint(
            state,
            LANE_FROM_SOUTH_TO_NORTH.index(),
            CELLS_IN_HEIGHT - 1,
            NORTH,
            LANE_FROM_SOUTH_TO_NORTH.index(),
            LANE_FROM_EAST_TO_WEST.index(),
            TurnType.LEFT
        );
        setSpawnPoint(
            state,
            LANE_FROM_SOUTH_TO_NORTH_CLOSER_TO_GRASS.index(),
            CELLS_IN_HEIGHT - 1,
            NORTH,
            LANE_FROM_SOUTH_TO_NORTH_CLOSER_TO_GRASS.index(),
            LANE_FROM_WEST_TO_EAST_CLOSER_TO_GRASS.index(),
            TurnType.RIGHT
        );
    }

    private static void setSpawnPoint(CrossroadState state, int x, int y, DirectionType spawnDirection, int turnX, int turnY, TurnType turnType) {
        Cell spawnPoint = state.cells[x][y];
        spawnPoint.canSpawnCars = true;
        spawnPoint.spawnedCarDirection = spawnDirection;
        spawnPoint.turnX = turnX;
        spawnPoint.turnY = turnY;
        spawnPoint.turnType = turnType;
    }

    private static void setGrass(CrossroadState state) {
        for (int x = 0; x < state.cells.length; ++x) {
            for (int y = 0; y < state.cells[x].length; ++y) {
                state.cells[x][y] = new Cell(CellType.GRASS, x, y);
            }
        }
    }

    private static void setStreets(CrossroadState state) {
        for (int x = 0; x < state.cells.length; ++x) {
            state.cells[x][LANE_FROM_EAST_TO_WEST_CLOSER_TO_GRASS.index()].type = CellType.STREET;
            state.cells[x][LANE_FROM_EAST_TO_WEST.index()].type = CellType.STREET;
            state.cells[x][LANE_FROM_WEST_TO_EAST.index()].type = CellType.STREET;
            state.cells[x][LANE_FROM_WEST_TO_EAST_CLOSER_TO_GRASS.index()].type = CellType.STREET;
        }

        for (int y = 0; y < state.cells[0].length; ++y) {
            state.cells[LANE_FROM_NORTH_TO_SOUTH_CLOSER_TO_GRASS.index()][y].type = CellType.STREET;
            state.cells[LANE_FROM_NORTH_TO_SOUTH.index()][y].type = CellType.STREET;
            state.cells[LANE_FROM_SOUTH_TO_NORTH.index()][y].type = CellType.STREET;
            state.cells[LANE_FROM_SOUTH_TO_NORTH_CLOSER_TO_GRASS.index()][y].type = CellType.STREET;
        }
    }


    private void setTrafficLights(CrossroadState state) {
        setTrafficLight(
            state,
            EAST_TO_WEST_CLOSER_TO_GRASS.x,
            EAST_TO_WEST_CLOSER_TO_GRASS.y,
            EAST,
            true
        );
        setTrafficLight(
            state,
            EAST_TO_WEST.x,
            EAST_TO_WEST.y,
            EAST,
            false
        );
        setTrafficLight(
            state,
            WEST_TO_EAST.x,
            WEST_TO_EAST.y,
            WEST,
            false
        );
        setTrafficLight(
            state,
            WEST_TO_EAST_CLOSER_TO_GRASS.x,
            WEST_TO_EAST_CLOSER_TO_GRASS.y,
            WEST,
            true
        );
        setTrafficLight(
            state,
            NORTH_TO_SOUTH_CLOSER_TO_GRASS.x,
            NORTH_TO_SOUTH_CLOSER_TO_GRASS.y,
            NORTH,
            true
        );
        setTrafficLight(
            state,
            NORTH_TO_SOUTH.x,
            NORTH_TO_SOUTH.y,
            NORTH,
            false
        );
        setTrafficLight(
            state,
            SOUTH_TO_NORTH.x,
            SOUTH_TO_NORTH.y,
            SOUTH,
            false
        );
        setTrafficLight(
            state,
            SOUTH_TO_NORTH_CLOSER_TO_GRASS.x,
            SOUTH_TO_NORTH_CLOSER_TO_GRASS.y,
            SOUTH,
            true
        );
    }

    private static void setTrafficLight(CrossroadState state, int x, int y, DirectionType directionPlacement, boolean canHaveRightGreenArrow) {
        state.trafficLights.add(new TrafficLight(x, y, TrafficLightColorType.RED, directionPlacement, canHaveRightGreenArrow));
        state.cells[x][y].trafficLightColor = TrafficLightColorType.RED;
    }
}
