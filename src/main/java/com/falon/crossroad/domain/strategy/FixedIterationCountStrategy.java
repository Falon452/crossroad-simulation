package com.falon.crossroad.domain.strategy;

import com.falon.crossroad.presentation.state.CrossroadState;
import com.falon.crossroad.domain.model.DirectionType;
import com.falon.crossroad.domain.model.TrafficLight;
import com.falon.crossroad.domain.model.TrafficLightColor;
import com.falon.crossroad.domain.model.TrafficStrategyType;

import static com.falon.crossroad.domain.model.DirectionType.*;

public class FixedIterationCountStrategy implements TrafficStrategy {

    private final static int ITERATIONS_PER_STRATEGY = 20;
    private final static int ITERATIONS_PER_STRATEGY_CHANGE_DELAY = 5;
    private final static int ITERATIONS_PER_STRATEGY_WITH_DELAY = ITERATIONS_PER_STRATEGY + ITERATIONS_PER_STRATEGY_CHANGE_DELAY;

    @Override
    public void execute(CrossroadState state) {
        updateTrafficStrategyType(state);
        switch (state.trafficStrategyType) {
            case EAST_ENABLED -> setGreenLightFor(state, EAST);
            case SOUTH_ENABLED -> setGreenLightFor(state, SOUTH);
            case WEST_ENABLED -> setGreenLightFor(state, WEST);
            case NORTH_ENABLED -> setGreenLightFor(state, NORTH);
            case ALL_DISABLED -> setAllRed(state);
        }
    }

    private static void setGreenLightFor(CrossroadState state, DirectionType directionPlacement) {
        for (TrafficLight trafficLight : state.trafficLights) {
            if (trafficLight.directionPlacement == directionPlacement) {
                trafficLight.color = TrafficLightColor.GREEN;
            } else {
                trafficLight.color = TrafficLightColor.RED;
            }
        }
    }

    private static void setAllRed(CrossroadState state) {
        for (TrafficLight trafficLight : state.trafficLights) {
            trafficLight.color = TrafficLightColor.RED;
        }
    }

    private static void updateTrafficStrategyType(CrossroadState state) {
        int strategiesCount = (TrafficStrategyType.values().length - 1);
        int iterationsForCycleOfStrategies = (ITERATIONS_PER_STRATEGY + ITERATIONS_PER_STRATEGY_CHANGE_DELAY) * strategiesCount;
        int iteration = state.iteration % ITERATIONS_PER_STRATEGY_WITH_DELAY;
        if (iteration < ITERATIONS_PER_STRATEGY) {
            int indexOfStrategy = (state.iteration % iterationsForCycleOfStrategies) / ITERATIONS_PER_STRATEGY_WITH_DELAY;
            state.trafficStrategyType = TrafficStrategyType.values()[indexOfStrategy];
        } else {
            state.trafficStrategyType = TrafficStrategyType.ALL_DISABLED;
        }
    }
}
