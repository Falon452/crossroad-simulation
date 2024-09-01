package com.falon.crossroad;

import com.falon.crossroad.model.Cell;
import com.falon.crossroad.model.Driver;
import com.falon.crossroad.model.TrafficLight;

import java.util.ArrayList;

public class CrossroadState {

    public Cell[][] cells;
    public ArrayList<Driver> drivers;
    public ArrayList<TrafficLight> trafficLights;

    public CrossroadState newStateWithCopiedCellsAndEmptyCars() {
        CrossroadState newState = new CrossroadState();
        newState.cells = cells.clone();
        newState.drivers = new ArrayList<>();
        newState.trafficLights = trafficLights;
        return newState;
    };
}
