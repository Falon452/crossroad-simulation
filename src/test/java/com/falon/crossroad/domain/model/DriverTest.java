package com.falon.crossroad.domain.model;

import com.falon.crossroad.domain.specification.CanDriveSpecification;
import com.falon.crossroad.domain.specification.PositionInBoundsSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DriverTest {

    @Mock
    private Car car;

    @Mock
    private CanDriveSpecification canDriveSpec;

    @Mock
    private PositionInBoundsSpecification positionInBoundsSpec;

    private Driver driver;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        driver = new Driver(car, 1, 1, TurnType.LEFT, canDriveSpec, positionInBoundsSpec); // Manually instantiate Driver
    }

    @Test
    void whenOnIterate_AtTurnPointAndTurningLeft_ThenTurnsLeft() {
        // GIVEN
        Car turnedCar = mock(Car.class);

        when(car.copyAndTurnLeft()).thenReturn(turnedCar);
        when(car.getX()).thenReturn(1);
        when(car.getY()).thenReturn(1);
        when(canDriveSpec.isSatisfiedBy(any(), any())).thenReturn(true);
        when(positionInBoundsSpec.isSatisfiedBy(anyInt(), anyInt(), anyInt(), anyInt())).thenReturn(true);

        // WHEN
        Driver resultDriver = driver.onIterate(createCellArray());

        // THEN
        assertNotNull(resultDriver);
        verify(car).copyAndTurnLeft();
    }

    @Test
    void whenOnIterateAndDriveNotPossible_ThenReturnsDriverWithStoppedCar() {
        // GIVEN
        Car stoppedCar = mock(Car.class);

        when(car.copyAndStop()).thenReturn(stoppedCar);
        when(car.getNextPositionX()).thenReturn(1);
        when(car.getNextPositionY()).thenReturn(1);
        when(canDriveSpec.isSatisfiedBy(any(), any())).thenReturn(false);
        when(positionInBoundsSpec.isSatisfiedBy(anyInt(), anyInt(), anyInt(), anyInt())).thenReturn(true);

        // WHEN
        Driver resultDriver = driver.onIterate(createCellArray());

        // THEN
        assertNotNull(resultDriver);
        assertEquals(stoppedCar, resultDriver.getCar());
    }

    private Cell[][] createCellArray() {
        Cell[][] cells = new Cell[2][2];
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 2; y++) {
                cells[x][y] = new Cell(CellType.GRASS, x, y);
            }
        }
        return cells;
    }
}
