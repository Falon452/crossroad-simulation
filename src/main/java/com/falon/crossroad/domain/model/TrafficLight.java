package com.falon.crossroad.domain.model;

public class TrafficLight {

    public int x;
    public int y;
    public TrafficLightColor color;
    public DirectionType directionPlacement;

    public TrafficLight(int x, int y, TrafficLightColor color, DirectionType directionPlacement) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.directionPlacement = directionPlacement;
    }
}
