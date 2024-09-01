package com.falon.crossroad.model;

import io.reactivex.rxjava3.annotations.Nullable;

import java.util.Random;

public class Cell {

    public CellType type;
    public boolean canSpawnCars = false;
    public boolean hasACar = false;

    @Nullable public DirectionType spawnedCarDirection;
    @Nullable public TrafficLightColor trafficLightColor;

    private final int x;
    private final int y;
    private final Random random = new Random();


    public Cell(CellType type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    @Nullable
    public Car spawnACarIfLucky() {
        if (canSpawnCars && occursWith25PercentProbability()) {
            return new Car(x, y, spawnedCarDirection);
        } else {
            return null;
        }
    }

    private boolean occursWith25PercentProbability() {
        return random.nextInt(4) < 1;
    }
}
