package com.falon.crossroad.model;

import io.reactivex.rxjava3.annotations.Nullable;

import java.util.Random;

public class Cell {

    public CellType type;
    public boolean canSpawnCars = false;
    @Nullable public Car car = null;

    public Cell(CellType type) {
        this.type = type;
    }

    public void spawnCarIfNeeded() {
        if (canSpawnCars && car == null && occursWith25PercentProbability()) {
            car = new Car();
        }
    }
    private boolean occursWith25PercentProbability() {
        return (new Random()).nextInt(4) < 1;
    }

    public boolean hasCar() {
        return car != null;
    }
}
