package com.falon.crossroad.domain.strategy;

import com.falon.crossroad.presentation.state.CrossroadState;
import com.falon.crossroad.domain.model.DirectionType;
import com.falon.crossroad.domain.model.TrafficLight;
import com.falon.crossroad.domain.model.TrafficLightColorType;
import com.falon.crossroad.domain.model.TrafficEnabledLaneType;

import java.util.Arrays;
import java.util.List;

import static com.falon.crossroad.domain.model.DirectionType.*;

public class FixedIterationCountStrategy implements TrafficStrategy {

    private final static int ITERATIONS_PER_STRATEGY = 20;
    private final static int ITERATIONS_PER_STRATEGY_CHANGE_DELAY = 5;
    private final static int ITERATIONS_PER_STRATEGY_WITH_DELAY = ITERATIONS_PER_STRATEGY + ITERATIONS_PER_STRATEGY_CHANGE_DELAY;

    @Override
    public void execute(CrossroadState state) {
        updateTrafficEnabledLane(state);
        switch (state.trafficEnabledLaneType) {
            case EAST_ENABLED -> {
                setGreenLightForAll(state, EAST);
                setGreenArrowFor(state, Arrays.asList(WEST, SOUTH));
            }
            case SOUTH_ENABLED -> {
                setGreenLightForAll(state, SOUTH);
                setGreenArrowFor(state, Arrays.asList(NORTH, WEST));
            }
            case WEST_ENABLED -> {
                setGreenLightForAll(state, WEST);
                setGreenArrowFor(state, Arrays.asList(NORTH, EAST));
            }
            case NORTH_ENABLED -> {
                setGreenLightForAll(state, NORTH);
                setGreenArrowFor(state, Arrays.asList(SOUTH, EAST));
            }
            case ALL_DISABLED -> setAllRed(state);
        }
    }

    private static void updateTrafficEnabledLane(CrossroadState state) {
        int strategiesCount = (TrafficEnabledLaneType.values().length - 1);
        int iterationsForCycleOfStrategies = (ITERATIONS_PER_STRATEGY + ITERATIONS_PER_STRATEGY_CHANGE_DELAY) * strategiesCount;
        int iteration = state.iteration % ITERATIONS_PER_STRATEGY_WITH_DELAY;
        if (iteration < ITERATIONS_PER_STRATEGY) {
            int indexOfStrategy = (state.iteration % iterationsForCycleOfStrategies) / ITERATIONS_PER_STRATEGY_WITH_DELAY;
            state.trafficEnabledLaneType = TrafficEnabledLaneType.values()[indexOfStrategy];
        } else {
            state.trafficEnabledLaneType = TrafficEnabledLaneType.ALL_DISABLED;
        }
    }

    private static void setGreenLightForAll(CrossroadState state, DirectionType directionPlacement) {
        for (TrafficLight trafficLight : state.trafficLights) {
            if (trafficLight.directionPlacement == directionPlacement) {
                trafficLight.color = TrafficLightColorType.GREEN;
            } else {
                trafficLight.color = TrafficLightColorType.RED;
            }
        }
    }

    private static void setGreenArrowFor(CrossroadState state, List<DirectionType> directions) {
        for (TrafficLight trafficLight : state.trafficLights) {
            if (directions.contains(trafficLight.directionPlacement) && trafficLight.canBeGreenArrowForRight) {
                trafficLight.color = TrafficLightColorType.GREEN_FOR_RIGHT;
            }
        }
    }

    private static void setAllRed(CrossroadState state) {
        for (TrafficLight trafficLight : state.trafficLights) {
            trafficLight.color = TrafficLightColorType.RED;
        }
    }
}
