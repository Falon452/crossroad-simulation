package com.falon.crossroad.model;

import static com.falon.crossroad.model.DirectionType.*;

public class Car {

    private final DirectionType direction;
    private final int x;
    private final int y;
    private static int VELOCITY_IN_CELLS = 1;

    public Car(int x, int y, DirectionType direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public Car copyAndDrive() {
        int newX = x;
        int newY = y;
        switch (direction) {
            case EAST -> newX += VELOCITY_IN_CELLS;
            case SOUTH -> newY += VELOCITY_IN_CELLS;
            case WEST -> newX -= VELOCITY_IN_CELLS;
            case NORTH -> newY -= VELOCITY_IN_CELLS;
        }
        return new Car(newX, newY, direction);
    }

    public Car copyAndStop() {
        return new Car(x, y, direction);
    }

    public Car copyAndTurnLeft() {
        DirectionType newDirection;
        switch (direction) {
            case EAST -> newDirection = NORTH;
            case SOUTH -> newDirection = EAST;
            case WEST -> newDirection = SOUTH;
            case NORTH -> newDirection = WEST;
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        }
        return new Car(x, y, newDirection);
    }

    public Car copyAndTurnRight() {
        DirectionType newDirection;
        switch (direction) {
            case EAST -> newDirection = SOUTH;
            case SOUTH -> newDirection = WEST;
            case WEST -> newDirection = NORTH;
            case NORTH -> newDirection = EAST;
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        }
        return new Car(x, y, newDirection);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getNextPositionX() {
        switch (direction) {
            case EAST -> {
                return x + VELOCITY_IN_CELLS;
            }
            case WEST -> {
                return x - VELOCITY_IN_CELLS;
            }
            default -> {
                return x;
            }
        }
    }

    public int getNextPositionY() {
        switch (direction) {
            case NORTH -> {
                return y - VELOCITY_IN_CELLS;
            }
            case SOUTH -> {
                return y + VELOCITY_IN_CELLS;
            }
            default -> {
                return y;
            }
        }
    }
}
