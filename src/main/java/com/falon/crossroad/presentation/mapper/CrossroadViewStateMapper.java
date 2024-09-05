package com.falon.crossroad.presentation.mapper;


import com.falon.crossroad.domain.model.Cell;
import com.falon.crossroad.domain.model.TrafficLightColorType;
import com.falon.crossroad.presentation.state.CrossroadState;
import com.falon.crossroad.presentation.viewstate.CellItem;
import com.falon.crossroad.presentation.viewstate.CrossroadViewState;

import java.awt.*;
import java.text.DecimalFormat;

public class CrossroadViewStateMapper {

    public CrossroadViewState from(CrossroadState state) {
        CrossroadViewState viewState = new CrossroadViewState();
        viewState.cellItems = new CellItem[state.cells.length][state.cells[0].length];
        Double averageCarsPerIteration = state.carsDriven / (double) state.iteration;
        String formattedAverageCarsPerIteration = new DecimalFormat("#.##").format(averageCarsPerIteration);
        viewState.averageCarsPerIteration = "Average cars per iteration: " + formattedAverageCarsPerIteration;
        for (int x = 0; x < state.cells.length; ++x) {
            for (int y = 0; y < state.cells[x].length; ++y) {
                viewState.cellItems[x][y] = from(state.cells[x][y]);
            }
        }
        return viewState;
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

    private Color getCellCircleColor(TrafficLightColorType trafficLightColorType) {
        if (trafficLightColorType == null) {
            return null;
        }

        return switch (trafficLightColorType) {
            case GREEN_FOR_RIGHT -> new Color(0, 153, 0);
            case RED -> new Color(255, 51, 51);
            case GREEN -> new Color(0, 204, 0);
        };
    }
}
