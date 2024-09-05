package com.falon.crossroad.presentation.mapper;


import com.falon.crossroad.domain.model.Cell;
import com.falon.crossroad.domain.model.TrafficLightColor;
import com.falon.crossroad.presentation.state.CrossroadState;
import com.falon.crossroad.presentation.viewstate.CellItem;
import com.falon.crossroad.presentation.viewstate.CrossroadViewState;

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
        Color cellColor = getCellColor(cell);
        Color circleColor = getCellCircleColor(cell.trafficLightColor);

        return new CellItem(cellColor, circleColor);
    }

    private Color getCellColor(Cell cell) {
        if (cell.hasACar) {
            return new Color(0, 51, 102);
        }

        return switch (cell.type) {
            case STREET -> new Color(64, 64, 64);
            case GRASS -> new Color(34, 139, 34);
        };
    }

    private Color getCellCircleColor(TrafficLightColor trafficLightColor) {
        if (trafficLightColor == null) {
            return null;
        }

        return switch (trafficLightColor) {
            case RED -> new Color(255, 51, 51);
            case GREEN -> new Color(0, 204, 0);
        };
    }
}
