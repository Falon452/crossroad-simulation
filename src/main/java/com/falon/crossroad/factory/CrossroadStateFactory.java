package com.falon.crossroad.factory;

import com.falon.crossroad.CrossroadState;
import com.falon.crossroad.model.*;

import java.util.ArrayList;

import static com.falon.crossroad.model.DirectionType.*;

public class CrossroadStateFactory {

    private final static int CELLS_IN_WIDTH = 60;
    private final static int CELLS_IN_HEIGHT = 60;
    private final static int LANE_FROM_EAST_TO_WEST_CLOSER_TO_GRASS = 28;
    private final static int LANE_FROM_EAST_TO_WEST = 29;
    private final static int LANE_FROM_WEST_TO_EAST = 30;
    private final static int LANE_FROM_WEST_TO_EAST_CLOSER_TO_GRASS = 31;

    private final static int LANE_FROM_NORTH_TO_SOUTH_CLOSER_TO_GRASS  = 28;
    private final static int LANE_FROM_NORTH_TO_SOUTH = 29;
    private final static int LANE_FROM_SOUTH_TO_NORTH = 30;
    private final static int LANE_FROM_SOUTH_TO_NORTH_CLOSER_TO_GRASS  = 31;

    private final static int OFFSET_FROM_CROSSROAD = 1;

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
        setSpawnPoint(state, 0, LANE_FROM_WEST_TO_EAST, EAST);
        setSpawnPoint(state, 0, LANE_FROM_WEST_TO_EAST_CLOSER_TO_GRASS, EAST);

        setSpawnPoint(state, CELLS_IN_WIDTH - 1, LANE_FROM_EAST_TO_WEST, WEST);
        setSpawnPoint(state, CELLS_IN_WIDTH - 1, LANE_FROM_EAST_TO_WEST_CLOSER_TO_GRASS, WEST);

        setSpawnPoint(state, LANE_FROM_NORTH_TO_SOUTH_CLOSER_TO_GRASS, 0, SOUTH);
        setSpawnPoint(state, LANE_FROM_NORTH_TO_SOUTH, 0, SOUTH);

        setSpawnPoint(state, LANE_FROM_SOUTH_TO_NORTH, CELLS_IN_HEIGHT - 1, NORTH);
        setSpawnPoint(state, LANE_FROM_SOUTH_TO_NORTH_CLOSER_TO_GRASS, CELLS_IN_HEIGHT - 1, NORTH);
    }

    private static void setSpawnPoint(CrossroadState state, int x, int y, DirectionType spawnDirection) {
        Cell spawnPoint = state.cells[x][y];
        spawnPoint.canSpawnCars = true;
        spawnPoint.spawnedCarDirection = spawnDirection;
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
            state.cells[x][LANE_FROM_EAST_TO_WEST_CLOSER_TO_GRASS].type = CellType.STREET;
            state.cells[x][LANE_FROM_EAST_TO_WEST].type = CellType.STREET;
            state.cells[x][LANE_FROM_WEST_TO_EAST].type = CellType.STREET;
            state.cells[x][LANE_FROM_WEST_TO_EAST_CLOSER_TO_GRASS].type = CellType.STREET;
        }

        for (int y = 0; y < state.cells[0].length; ++y) {
            state.cells[LANE_FROM_NORTH_TO_SOUTH_CLOSER_TO_GRASS][y].type = CellType.STREET;
            state.cells[LANE_FROM_NORTH_TO_SOUTH][y].type = CellType.STREET;
            state.cells[LANE_FROM_SOUTH_TO_NORTH][y].type = CellType.STREET;
            state.cells[LANE_FROM_SOUTH_TO_NORTH_CLOSER_TO_GRASS][y].type = CellType.STREET;
        }
    }


    private void setTrafficLights(CrossroadState state) {
        setTrafficLight(
            state,
            LANE_FROM_SOUTH_TO_NORTH_CLOSER_TO_GRASS + OFFSET_FROM_CROSSROAD,
            LANE_FROM_EAST_TO_WEST_CLOSER_TO_GRASS,
            TrafficLightColor.RED
        );
        setTrafficLight(
            state,
            LANE_FROM_SOUTH_TO_NORTH_CLOSER_TO_GRASS + OFFSET_FROM_CROSSROAD,
            LANE_FROM_EAST_TO_WEST,
            TrafficLightColor.RED
        );
        setTrafficLight(
                state,
                LANE_FROM_NORTH_TO_SOUTH_CLOSER_TO_GRASS - OFFSET_FROM_CROSSROAD,
                LANE_FROM_WEST_TO_EAST,
                TrafficLightColor.RED
        );
        setTrafficLight(
                state,
                LANE_FROM_NORTH_TO_SOUTH_CLOSER_TO_GRASS - OFFSET_FROM_CROSSROAD,
                LANE_FROM_WEST_TO_EAST_CLOSER_TO_GRASS,
                TrafficLightColor.RED
        );
        setTrafficLight(
                state,
                LANE_FROM_NORTH_TO_SOUTH_CLOSER_TO_GRASS,
                LANE_FROM_EAST_TO_WEST_CLOSER_TO_GRASS - OFFSET_FROM_CROSSROAD,
                TrafficLightColor.RED
        );
        setTrafficLight(
                state,
                LANE_FROM_NORTH_TO_SOUTH,
                LANE_FROM_EAST_TO_WEST_CLOSER_TO_GRASS - OFFSET_FROM_CROSSROAD,
                TrafficLightColor.RED
        );
        setTrafficLight(
                state,
                LANE_FROM_SOUTH_TO_NORTH,
                LANE_FROM_WEST_TO_EAST_CLOSER_TO_GRASS + OFFSET_FROM_CROSSROAD,
                TrafficLightColor.GREEN
        );
        setTrafficLight(
                state,
                LANE_FROM_SOUTH_TO_NORTH_CLOSER_TO_GRASS,
                LANE_FROM_WEST_TO_EAST_CLOSER_TO_GRASS + OFFSET_FROM_CROSSROAD,
                TrafficLightColor.GREEN
        );
    }

    private static void setTrafficLight(CrossroadState state, int x, int y, TrafficLightColor color) {
        state.trafficLights.add(new TrafficLight(x, y, color));
        state.cells[x][y].trafficLightColor = color;
    }
}
