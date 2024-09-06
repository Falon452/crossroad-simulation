package com.falon.crossroad.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

class CarTest {

    private static final int INITIAL_X = 5;
    private static final int INITIAL_Y = 5;

    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car(INITIAL_X, INITIAL_Y, DirectionType.EAST);
    }

    @Test
    void whenCopyAndDrive_ThenMovesInDirection() {
        // Move EAST
        Car movedCar = car.copyAndDrive();
        assertEquals(INITIAL_X + 1, movedCar.getX());
        assertEquals(INITIAL_Y, movedCar.getY());

        // Move SOUTH
        car = new Car(INITIAL_X, INITIAL_Y, DirectionType.SOUTH);
        movedCar = car.copyAndDrive();
        assertEquals(INITIAL_X, movedCar.getX());
        assertEquals(INITIAL_Y + 1, movedCar.getY());

        // Move WEST
        car = new Car(INITIAL_X, INITIAL_Y, DirectionType.WEST);
        movedCar = car.copyAndDrive();
        assertEquals(INITIAL_X - 1, movedCar.getX());
        assertEquals(INITIAL_Y, movedCar.getY());

        // Move NORTH
        car = new Car(INITIAL_X, INITIAL_Y, DirectionType.NORTH);
        movedCar = car.copyAndDrive();
        assertEquals(INITIAL_X, movedCar.getX());
        assertEquals(INITIAL_Y - 1, movedCar.getY());
    }

    @Test
    void whenCopyAndStop_ThenPositionRemainsUnchanged() {
        Car stoppedCar = car.copyAndStop();
        assertEquals(INITIAL_X, stoppedCar.getX());
        assertEquals(INITIAL_Y, stoppedCar.getY());
    }

    @Test
    void whenCopyAndTurnLeft_ThenCarTurnsLeft() {
        // Turn left from EAST (new direction: NORTH)
        Car turnedCar = car.copyAndTurnLeft();
        assertEquals(DirectionType.NORTH, turnedCar.copyAndDrive().getDirection());

        // Turn left from NORTH (new direction: WEST)
        car = new Car(INITIAL_X, INITIAL_Y, DirectionType.NORTH);
        turnedCar = car.copyAndTurnLeft();
        assertEquals(DirectionType.WEST, turnedCar.copyAndDrive().getDirection());

        // Turn left from WEST (new direction: SOUTH)
        car = new Car(INITIAL_X, INITIAL_Y, DirectionType.WEST);
        turnedCar = car.copyAndTurnLeft();
        assertEquals(DirectionType.SOUTH, turnedCar.copyAndDrive().getDirection());

        // Turn left from SOUTH (new direction: EAST)
        car = new Car(INITIAL_X, INITIAL_Y, DirectionType.SOUTH);
        turnedCar = car.copyAndTurnLeft();
        assertEquals(DirectionType.EAST, turnedCar.copyAndDrive().getDirection());
    }

    @Test
    void whenCopyAndTurnRight_ThenCarTurnsRight() {
        // Turn right from EAST (new direction: SOUTH)
        Car turnedCar = car.copyAndTurnRight();
        assertEquals(DirectionType.SOUTH, turnedCar.copyAndDrive().getDirection());

        // Turn right from SOUTH (new direction: WEST)
        car = new Car(INITIAL_X, INITIAL_Y, DirectionType.SOUTH);
        turnedCar = car.copyAndTurnRight();
        assertEquals(DirectionType.WEST, turnedCar.copyAndDrive().getDirection());

        // Turn right from WEST (new direction: NORTH)
        car = new Car(INITIAL_X, INITIAL_Y, DirectionType.WEST);
        turnedCar = car.copyAndTurnRight();
        assertEquals(DirectionType.NORTH, turnedCar.copyAndDrive().getDirection());

        // Turn right from NORTH (new direction: EAST)
        car = new Car(INITIAL_X, INITIAL_Y, DirectionType.NORTH);
        turnedCar = car.copyAndTurnRight();
        assertEquals(DirectionType.EAST, turnedCar.copyAndDrive().getDirection());
    }

    @Test
    void whenGetNextPositionX_ThenReturnsCorrectNextXPosition() {
        // Move EAST (increase X)
        assertEquals(INITIAL_X + 1, car.getNextPositionX());

        // Move WEST (decrease X)
        car = new Car(INITIAL_X, INITIAL_Y, DirectionType.WEST);
        assertEquals(INITIAL_X - 1, car.getNextPositionX());

        // Move NORTH or SOUTH (X remains unchanged)
        car = new Car(INITIAL_X, INITIAL_Y, DirectionType.NORTH);
        assertEquals(INITIAL_X, car.getNextPositionX());

        car = new Car(INITIAL_X, INITIAL_Y, DirectionType.SOUTH);
        assertEquals(INITIAL_X, car.getNextPositionX());
    }

    @Test
    void whenGetNextPositionY_ThenReturnsCorrectNextYPosition() {
        // Move SOUTH (increase Y)
        car = new Car(INITIAL_X, INITIAL_Y, DirectionType.SOUTH);
        assertEquals(INITIAL_Y + 1, car.getNextPositionY());

        // Move NORTH (decrease Y)
        car = new Car(INITIAL_X, INITIAL_Y, DirectionType.NORTH);
        assertEquals(INITIAL_Y - 1, car.getNextPositionY());

        // Move EAST or WEST (Y remains unchanged)
        car = new Car(INITIAL_X, INITIAL_Y, DirectionType.EAST);
        assertEquals(INITIAL_Y, car.getNextPositionY());

        car = new Car(INITIAL_X, INITIAL_Y, DirectionType.WEST);
        assertEquals(INITIAL_Y, car.getNextPositionY());
    }
}
