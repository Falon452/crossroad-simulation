package com.falon.crossroad.model;

import com.falon.crossroad.specification.CanDriveSpecification;
import com.falon.crossroad.specification.PositionInBoundsSpecification;
import io.reactivex.rxjava3.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

public class Driver {

    private final Car car;
    private final CanDriveSpecification canDriveSpec = new CanDriveSpecification();
    private final PositionInBoundsSpecification positionInBoundsSpec = new PositionInBoundsSpecification();
    private static final int INDEX_SHIFT = 1;

    public Driver(Car car) {
        this.car = car;
    }

    public int getX() {
        return car.getX();
    }

    public int getY() {
        return car.getY();
    }

    @Nullable
    public Driver onIterate(Cell[][] cells) {
        int nextX = car.getNextPositionX();
        int nextY = car.getNextPositionY();
        if (positionInBoundsSpec.isSatisfiedBy(nextX, nextY, cells.length - INDEX_SHIFT, cells[0].length - INDEX_SHIFT)) {
            Cell nextCell = cells[nextX][nextY];
            Car newCar = driveIfPossibleAndGetNewCar(nextCell);
            return new Driver(newCar);
        } else {
            return null;
        }
    }

    @NotNull
    private Car driveIfPossibleAndGetNewCar(Cell nextCell) {
        Car newCar;
        if (canDriveSpec.isSatisfiedBy(nextCell)) {
            newCar = car.copyAndDrive();
        } else {
            newCar = car.copyAndStop();
        }

        return newCar;
    }
}
