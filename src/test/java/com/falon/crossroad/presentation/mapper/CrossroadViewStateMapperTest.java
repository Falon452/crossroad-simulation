package com.falon.crossroad.presentation.mapper;

import com.falon.crossroad.domain.model.Cell;
import com.falon.crossroad.domain.model.CellType;
import com.falon.crossroad.domain.model.TrafficLightColorType;
import com.falon.crossroad.presentation.state.CrossroadState;
import com.falon.crossroad.presentation.viewstate.CrossroadViewState;
import com.falon.crossroad.presentation.viewstate.CellItem;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class CrossroadViewStateMapperTest {

    private final CrossroadViewStateMapper mapper = new CrossroadViewStateMapper();

    @Test
    void mapsFromStateToViewState() {
        // GIVEN
        CrossroadState state = new CrossroadState();
        state.iteration = 2;
        state.carsDriven = 4;

        state.cells = new Cell[2][2];
        state.cells[0][0] = new Cell(CellType.STREET, 0, 0);
        state.cells[0][1] = new Cell(CellType.GRASS, 0, 1);
        state.cells[1][0] = new Cell(CellType.STREET, 1, 0);
        state.cells[1][1] = new Cell(CellType.GRASS, 1, 1);

        // WHEN
        CrossroadViewState viewState = mapper.from(state);

        // THEN
        assertNotNull(viewState);
        assertEquals("Average cars per iteration: 2", viewState.averageCarsPerIteration);
        assertEquals(2, viewState.cellItems.length);
        assertEquals(2, viewState.cellItems[0].length);
        assertEquals(STREET_COLOR, viewState.cellItems[0][0].squareColor);
        assertEquals(GRASS_COLOR, viewState.cellItems[0][1].squareColor);
    }

    @Test
    void whenCellHasTrafficLightColorThenMapsToCircleColor() {
        // GIVEN
        CrossroadState state = new CrossroadState();
        state.cells = new Cell[1][1];
        state.cells[0][0] = new Cell(CellType.STREET, 0, 0);
        state.cells[0][0].trafficLightColor = TrafficLightColorType.RED;

        // WHEN
        CrossroadViewState viewState = mapper.from(state);
        CellItem cellItem = viewState.cellItems[0][0];

        // THEN
        assertNotNull(cellItem);
        assertEquals(RED_COLOR, cellItem.circleColor);
    }

    @Test
    void whenCellHasACarThenMapsToACarSquareColor() {
        // GIVEN
        CrossroadState state = new CrossroadState();
        state.cells = new Cell[1][1];
        state.cells[0][0] = new Cell(CellType.STREET, 0, 0);
        state.cells[0][0].hasACar = true;

        // WHEN
        CrossroadViewState viewState = mapper.from(state);

        // THEN
        CellItem cellItem = viewState.cellItems[0][0];
        assertEquals(CAR_COLOR, cellItem.squareColor);
    }

    private static final Color STREET_COLOR = new Color(64, 64, 64);
    private static final Color GRASS_COLOR = new Color(34, 139, 34);
    private static final Color RED_COLOR = new Color(255, 51, 51);
    private static final Color CAR_COLOR = new Color(0, 51, 102);
}
