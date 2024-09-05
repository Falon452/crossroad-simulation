package com.falon.crossroad.domain.model;

import io.reactivex.rxjava3.annotations.Nullable;

import java.util.Random;

public class Cell {

    public CellType type;
    public boolean canSpawnCars = false;
    public boolean hasACar = false;

    @Nullable public DirectionType spawnedCarDirection;
    @Nullable public TrafficLightColorType trafficLightColor;

    private final int x;
    private final int y;
    public int turnX;
    public int turnY;
    public TurnType turnType;
    private final Random random = new Random();
    public int spawnDriverOccursInPercent;

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
                randomTurnOrRideForward()
            );
        } else {
            return null;
        }
    }

    private boolean occursWithProbability() {
        return random.nextInt(100) < spawnDriverOccursInPercent;
    }

    private TurnType randomTurnOrRideForward() {
        if (random.nextInt(4) < 3) {
            return turnType;
        } else {
            return TurnType.NO_TURN;
        }
    }
}
