package com.falon.crossroad.domain.model;

import com.falon.crossroad.domain.specification.CanDriveSpecification;
import com.falon.crossroad.domain.specification.PositionInBoundsSpecification;
import io.reactivex.rxjava3.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

public class Driver {

    private Car car;
    private final CanDriveSpecification canDriveSpec;
    private final PositionInBoundsSpecification positionInBoundsSpec;
    private static final int INDEX_SHIFT = 1;
    private final int turnX;
    private final int turnY;
    private final TurnType turnType;

    public Driver(Car car, int turnX, int turnY, TurnType turnType) {
        this.car = car;
        this.turnX = turnX;
        this.turnY = turnY;
        this.turnType = turnType;
        this.canDriveSpec = new CanDriveSpecification();
        this.positionInBoundsSpec = new PositionInBoundsSpecification();
    }

    public Driver(
        Car car,
        int turnX,
        int turnY,
        TurnType turnType,
        CanDriveSpecification canDriveSpec,
        PositionInBoundsSpecification positionInBoundsSpec
    ) {
        this.car = car;
        this.turnX = turnX;
        this.turnY = turnY;
        this.turnType = turnType;
        this.canDriveSpec = canDriveSpec;
        this.positionInBoundsSpec = positionInBoundsSpec;
    }

    public int getX() {
        return car.getX();
    }

    public int getY() {
        return car.getY();
    }

    public Car getCar() {
        return car;
    }

    public TurnType getTurnType() {
        return turnType;
    }

    @Nullable
    public Driver onIterate(Cell[][] cells) {
        Car turnedCar = car;
        if (car.getX() == turnX && car.getY() == turnY) {
            switch (turnType) {
                case LEFT -> turnedCar = car.copyAndTurnLeft();
                case RIGHT -> turnedCar = car.copyAndTurnRight();
                default -> {
                }
            }
        }
        car = turnedCar;

        int nextX = turnedCar.getNextPositionX();
        int nextY = turnedCar.getNextPositionY();
        if (positionInBoundsSpec.isSatisfiedBy(nextX, nextY, cells.length - INDEX_SHIFT, cells[0].length - INDEX_SHIFT)) {
            Cell nextCell = cells[nextX][nextY];
            Car newCar = driveIfPossibleAndGetNewCar(nextCell);
            return new Driver(newCar, turnX, turnY, turnType);
        } else {
            return null;
        }
    }

    @NotNull
    private Car driveIfPossibleAndGetNewCar(Cell nextCell) {
        Car newCar;
        if (canDriveSpec.isSatisfiedBy(nextCell, this)) {
            newCar = car.copyAndDrive();
        } else {
            newCar = car.copyAndStop();
        }

        return newCar;
    }
}
