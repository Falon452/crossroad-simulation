package com.falon.crossroad;


import com.falon.crossroad.model.Cell;
import com.falon.crossroad.viewstate.CellItem;
import com.falon.crossroad.viewstate.CrossroadViewState;

import java.awt.*;

public class CrossroadViewStateMapper {

    public CrossroadViewState from(CrossroadState state) {
        CrossroadViewState crossroadViewState = new CrossroadViewState();
        crossroadViewState.cellItems = new CellItem[state.cells.length][state.cells[0].length];
        for (int x = 0; x < state.cells.length; ++x) {
            for (int y = 0; y < state.cells[x].length; ++y) {
                crossroadViewState.cellItems[x][y] = from(state.cells[x][y]);
            }
        }
        return crossroadViewState;
    }

    private CellItem from(Cell cell) {
        Color color;

        if (cell.hasCar()) {
            return new CellItem(new Color(0, 51, 102));
        }

        switch (cell.type) {
            case STREET -> color = new Color(64, 64, 64);
            case GRASS -> color = new Color(34, 139, 34);
            default -> throw new IllegalStateException("Unexpected value: " + cell.type);
        }
        return new CellItem(color);
    }
}
