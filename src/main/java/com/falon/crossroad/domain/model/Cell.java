package com.falon.crossroad.domain.model;

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
    public int turnX;
    public int turnY;
    public TurnType turnType;
    private final Random random = new Random();
    public int occursInProbabilityFrom0To100 = 25;

    public Cell(CellType type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    @Nullable
    public Driver spawnADriverIfLucky() {
        if (canSpawnCars && occursWithProbability()) {
            return new Driver(
                new Car(x, y, spawnedCarDirection),
                turnX,
                turnY,
                turnType
            );
        } else {
            return null;
        }
    }

    private boolean occursWithProbability() {
        return random.nextInt(100) < occursInProbabilityFrom0To100;
    }
}
