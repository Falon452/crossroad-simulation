package com.falon.crossroad.presentation.state;

import com.falon.crossroad.domain.model.Cell;
import com.falon.crossroad.domain.model.Driver;
import com.falon.crossroad.domain.model.TrafficLight;
import com.falon.crossroad.domain.model.TrafficStrategyType;

import java.util.ArrayList;

public class CrossroadState {

    public Cell[][] cells;
    public ArrayList<Driver> drivers;
    public ArrayList<TrafficLight> trafficLights;
    public TrafficStrategyType trafficStrategyType;
    public int iteration = 0;

    public CrossroadState newStateWithCopiedCellsAndEmptyCars() {
        CrossroadState newState = new CrossroadState();
        newState.cells = cells.clone();
        newState.drivers = new ArrayList<>();
        newState.trafficLights = trafficLights;
        newState.trafficStrategyType = trafficStrategyType;
        newState.iteration = iteration;
        return newState;
    }
}
