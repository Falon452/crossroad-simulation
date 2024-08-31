package com.falon.crossroad.factory;

import com.falon.crossroad.CrossroadState;
import com.falon.crossroad.model.Cell;
import com.falon.crossroad.model.CellType;

public class CrossroadStateFactory {

    public final static int CELLS_IN_WIDTH = 60;
    public final static int CELLS_IN_HEIGHT = 60;
    public final static int LANE_FROM_RIGHT_TO_LEFT_CLOSER_TO_GRASS = 28;
    public final static int LANE_FROM_RIGHT_TO_LEFT = 29;
    public final static int LANE_FROM_LEFT_TO_RIGHT = 30;
    public final static int LANE_FROM_LEFT_TO_RIGHT_CLOSER_TO_GRASS = 31;

    public final static int LANE_FROM_TOP_TO_BOTTOM_CLOSER_TO_GRASS  = 28;
    public final static int LANE_FROM_TOP_TO_BOTTOM = 29;
    public final static int LANE_FROM_BOTTOM_TO_TOP = 30;
    public final static int LANE_FROM_BOTTOM_TO_TOP_CLOSER_TO_GRASS  = 31;


    public CrossroadState create() {
        CrossroadState state = new CrossroadState();
        state.cells = new Cell[CELLS_IN_WIDTH][CELLS_IN_HEIGHT];

        setGrass(state);
        setStreet(state);
        setSpawningPoints(state);

        return state;
    }

    private static void setSpawningPoints(CrossroadState state) {
        state.cells[0][LANE_FROM_LEFT_TO_RIGHT].canSpawnCars = true;
        state.cells[0][LANE_FROM_LEFT_TO_RIGHT_CLOSER_TO_GRASS].canSpawnCars = true;

        state.cells[CELLS_IN_WIDTH - 1][LANE_FROM_RIGHT_TO_LEFT].canSpawnCars = true;
        state.cells[CELLS_IN_WIDTH - 1][LANE_FROM_RIGHT_TO_LEFT_CLOSER_TO_GRASS].canSpawnCars = true;

        state.cells[LANE_FROM_TOP_TO_BOTTOM_CLOSER_TO_GRASS][0].canSpawnCars = true;
        state.cells[LANE_FROM_TOP_TO_BOTTOM][0].canSpawnCars = true;

        state.cells[LANE_FROM_BOTTOM_TO_TOP][CELLS_IN_HEIGHT - 1].canSpawnCars = true;
        state.cells[LANE_FROM_BOTTOM_TO_TOP_CLOSER_TO_GRASS][CELLS_IN_HEIGHT - 1].canSpawnCars = true;
    }

    private static void setGrass(CrossroadState state) {
        for (int x = 0; x < state.cells.length; ++x) {
            for (int y = 0; y < state.cells[x].length; ++y) {
                state.cells[x][y] = new Cell(CellType.GRASS);
            }
        }
    }

    private static void setStreet(CrossroadState state) {
        for (int x = 0; x < state.cells.length; ++x) {
            state.cells[x][LANE_FROM_RIGHT_TO_LEFT_CLOSER_TO_GRASS].type = CellType.STREET;
            state.cells[x][LANE_FROM_RIGHT_TO_LEFT].type = CellType.STREET;
            state.cells[x][LANE_FROM_LEFT_TO_RIGHT].type = CellType.STREET;
            state.cells[x][LANE_FROM_LEFT_TO_RIGHT_CLOSER_TO_GRASS].type = CellType.STREET;
        }

        for (int y = 0; y < state.cells[0].length; ++y) {
            state.cells[LANE_FROM_TOP_TO_BOTTOM_CLOSER_TO_GRASS][y].type = CellType.STREET;
            state.cells[LANE_FROM_TOP_TO_BOTTOM][y].type = CellType.STREET;
            state.cells[LANE_FROM_BOTTOM_TO_TOP][y].type = CellType.STREET;
            state.cells[LANE_FROM_BOTTOM_TO_TOP_CLOSER_TO_GRASS][y].type = CellType.STREET;
        }
    }
}
