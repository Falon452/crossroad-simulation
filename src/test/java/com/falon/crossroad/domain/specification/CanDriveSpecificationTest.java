package com.falon.crossroad.domain.specification;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.falon.crossroad.domain.model.Cell;
import com.falon.crossroad.domain.model.CellType;
import com.falon.crossroad.domain.model.Driver;
import com.falon.crossroad.domain.model.TrafficLightColorType;
import com.falon.crossroad.domain.model.TurnType;

class CanDriveSpecificationTest {

    private final CanDriveSpecification canDriveSpecification = new CanDriveSpecification();

    @Test
    void whenCellHasACar_ThenReturnsFalse() {
        // GIVEN
        Cell cell = new Cell(CellType.STREET, 0, 0);
        cell.hasACar = true;
        Driver driver = new Driver(null, 0, 0, TurnType.NO_TURN);

        // WHEN
        boolean result = canDriveSpecification.isSatisfiedBy(cell, driver);

        // THEN
        assertFalse(result);
    }

    @Test
    void whenTrafficLightIsGreenAndCellHasNoCar_ThenReturnsTrue() {
        // GIVEN
        Cell cell = new Cell(CellType.STREET, 0, 0);
        cell.hasACar = false;
        cell.trafficLightColor = TrafficLightColorType.GREEN;
        Driver driver = new Driver(null, 0, 0, TurnType.NO_TURN);

        // WHEN
        boolean result = canDriveSpecification.isSatisfiedBy(cell, driver);

        // THEN
        assertTrue(result);
    }

    @Test
    void whenTrafficLightIsRedAndCellHasNoCar_ThenReturnsFalse() {
        // GIVEN
        Cell cell = new Cell(CellType.STREET, 0, 0);
        cell.hasACar = false;
        cell.trafficLightColor = TrafficLightColorType.RED;
        Driver driver = new Driver(null, 0, 0, TurnType.NO_TURN);

        // WHEN
        boolean result = canDriveSpecification.isSatisfiedBy(cell, driver);

        // THEN
        assertFalse(result);
    }

    @Test
    void whenTrafficLightIsGreenForRightTurnAndDriverTurnsRight_ThenReturnsTrue() {
        // GIVEN
        Cell cell = new Cell(CellType.STREET, 0, 0);
        cell.hasACar = false;
        cell.trafficLightColor = TrafficLightColorType.GREEN_FOR_RIGHT;
        Driver driver = new Driver(null, 0, 0, TurnType.RIGHT);

        // WHEN
        boolean result = canDriveSpecification.isSatisfiedBy(cell, driver);

        // THEN
        assertTrue(result);
    }

    @Test
    void whenTrafficLightIsGreenForRightTurnAndDriverTurnsLeft_ThenReturnsFalse() {
        // GIVEN
        Cell cell = new Cell(CellType.STREET, 0, 0);
        cell.hasACar = false;
        cell.trafficLightColor = TrafficLightColorType.GREEN_FOR_RIGHT;
        Driver driver = new Driver(null, 0, 0, TurnType.LEFT);

        // WHEN
        boolean result = canDriveSpecification.isSatisfiedBy(cell, driver);

        // THEN
        assertFalse(result);
    }

    @Test
    void whenNoTrafficLight_ThenReturnsTrue() {
        // GIVEN
        Cell cell = new Cell(CellType.STREET, 0, 0);
        cell.hasACar = false;
        cell.trafficLightColor = null;
        Driver driver = new Driver(null, 0, 0, TurnType.NO_TURN);

        // WHEN
        boolean result = canDriveSpecification.isSatisfiedBy(cell, driver);

        // THEN
        assertTrue(result);
    }
}