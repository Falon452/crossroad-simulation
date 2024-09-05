package com.falon.crossroad.domain.model;

public class TrafficLight {

    public int x;
    public int y;
    public TrafficLightColorType color;
    public DirectionType directionPlacement;
    public boolean canBeGreenArrowForRight;

    public TrafficLight(int x, int y, TrafficLightColorType color, DirectionType directionPlacement, boolean canBeGreenArrowForRight) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.directionPlacement = directionPlacement;
        this.canBeGreenArrowForRight = canBeGreenArrowForRight;
    }
}
