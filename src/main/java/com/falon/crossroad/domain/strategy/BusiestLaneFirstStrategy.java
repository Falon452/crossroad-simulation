package com.falon.crossroad.domain.strategy;

import com.falon.crossroad.domain.model.*;
import com.falon.crossroad.presentation.state.CrossroadState;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.falon.crossroad.domain.model.DirectionType.*;
import static com.falon.crossroad.domain.model.TrafficEnabledLaneType.*;
import static com.falon.crossroad.domain.model.TrafficPositionType.*;

public class BusiestLaneFirstStrategy implements TrafficStrategy {

    private final static int MAX_ITERATIONS_FOR_LANE = 70;
    private final static int MIN_ITERATIONS_FOR_LANE = 20;
    private final static int ITERATIONS_RED_DELAY = 5;
    private final static int NUMBER_OF_CELLS_OBSERVED_PER_LANE = 8;
    private final static int MINIMUM_CARS_TO_KEEP_LANE_ACTIVE = 4;
    private final static int MAX_ITERATIONS_WAITING = 260;

    private int northIterationsWaiting = 0;
    private int southIterationsWaiting = 0;
    private int eastIterationsWaiting = 0;
    private int westIterationsWaiting = 0;

    private int activeLaneIterations = 0;
    private int redLightIterations = 0;

    @Override
    public void execute(CrossroadState state) {
        updateActiveLane(state);
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

    private void updateActiveLane(CrossroadState state) {
        Map<TrafficEnabledLaneType, Integer> carCounts = getCarCounts(state);
        TrafficEnabledLaneType busiestLane = getBusiestLane(carCounts);

        switch (state.trafficEnabledLaneType) {
            case NORTH_ENABLED -> {
                if (shouldChangeActiveLane(carCounts.get(NORTH_ENABLED))) {
                    if (state.trafficEnabledLaneType == NORTH_ENABLED) {
                        prepareForChangingActiveLane(state);
                    }
                } else {
                    keepActiveLane();
                    southIterationsWaiting++;
                    eastIterationsWaiting++;
                    westIterationsWaiting++;
                }
            }
            case WEST_ENABLED -> {
                if (shouldChangeActiveLane(carCounts.get(WEST_ENABLED))) {
                    prepareForChangingActiveLane(state);
                } else {
                    keepActiveLane();
                    northIterationsWaiting++;
                    southIterationsWaiting++;
                    eastIterationsWaiting++;
                }
            }
            case SOUTH_ENABLED -> {
                if (shouldChangeActiveLane(carCounts.get(SOUTH_ENABLED))) {
                    prepareForChangingActiveLane(state);
                } else {
                    keepActiveLane();
                    northIterationsWaiting++;
                    eastIterationsWaiting++;
                    westIterationsWaiting++;
                }
            }
            case EAST_ENABLED -> {
                if (shouldChangeActiveLane(carCounts.get(EAST_ENABLED))) {
                    prepareForChangingActiveLane(state);
                } else {
                    keepActiveLane();
                    northIterationsWaiting++;
                    southIterationsWaiting++;
                    westIterationsWaiting++;
                }
            }
            case ALL_DISABLED -> {
                if (redLightIterations > ITERATIONS_RED_DELAY) {
                    setActiveLane(state, busiestLane);
                    switch (busiestLane) {
                        case EAST_ENABLED -> eastIterationsWaiting = 0;
                        case SOUTH_ENABLED -> southIterationsWaiting = 0;
                        case WEST_ENABLED -> westIterationsWaiting = 0;
                        case NORTH_ENABLED -> northIterationsWaiting = 0;
                        case ALL_DISABLED -> {}
                    }
                } else {
                    keepAllDisabled();
                }
            }
        }
    }

    private static void setActiveLane(CrossroadState state, TrafficEnabledLaneType lane) {
        state.trafficEnabledLaneType = lane;
    }

    private boolean shouldChangeActiveLane(Integer cars) {
        return (tooLittle(cars) || tooMuchIterations() || anyLaneIsWaitingTooLong()) && atLeastMinIterations();
    }

    private boolean anyLaneIsWaitingTooLong() {
        return northIterationsWaiting > MAX_ITERATIONS_WAITING ||
            southIterationsWaiting > MAX_ITERATIONS_WAITING ||
            westIterationsWaiting > MAX_ITERATIONS_WAITING ||
            eastIterationsWaiting > MAX_ITERATIONS_WAITING;
    }

    private boolean atLeastMinIterations() {
        return activeLaneIterations > MIN_ITERATIONS_FOR_LANE;
    }

    private boolean tooMuchIterations() {
        return activeLaneIterations > MAX_ITERATIONS_FOR_LANE;
    }

    private static boolean tooLittle(Integer cars) {
        return cars <= MINIMUM_CARS_TO_KEEP_LANE_ACTIVE;
    }

    private void keepActiveLane() {
        activeLaneIterations++;
    }

    private void keepAllDisabled() {
        redLightIterations++;
    }

    private Map<TrafficEnabledLaneType, Integer> getCarCounts(CrossroadState state) {
        Map<TrafficEnabledLaneType, Integer> carCounts = new HashMap<>();
        carCounts.put(NORTH_ENABLED, 0);
        carCounts.put(SOUTH_ENABLED, 0);
        carCounts.put(EAST_ENABLED, 0);
        carCounts.put(WEST_ENABLED, 0);

        for (int i = 1; i <= NUMBER_OF_CELLS_OBSERVED_PER_LANE; i++) {
            if (state.cells[EAST_TO_WEST.x + i][EAST_TO_WEST.y].hasACar) {
                carCounts.put(EAST_ENABLED, carCounts.get(EAST_ENABLED) + 1);
            }
            if (state.cells[EAST_TO_WEST_CLOSER_TO_GRASS.x + i][EAST_TO_WEST_CLOSER_TO_GRASS.y].hasACar) {
                carCounts.put(EAST_ENABLED, carCounts.get(EAST_ENABLED) + 1);
            }

            if (state.cells[WEST_TO_EAST.x - i][WEST_TO_EAST.y].hasACar) {
                carCounts.put(WEST_ENABLED, carCounts.get(WEST_ENABLED) + 1);
            }
            if (state.cells[WEST_TO_EAST_CLOSER_TO_GRASS.x - i][WEST_TO_EAST_CLOSER_TO_GRASS.y].hasACar) {
                carCounts.put(WEST_ENABLED, carCounts.get(WEST_ENABLED) + 1);
            }

            if (state.cells[NORTH_TO_SOUTH_CLOSER_TO_GRASS.x][NORTH_TO_SOUTH_CLOSER_TO_GRASS.y - i].hasACar) {
                carCounts.put(NORTH_ENABLED, carCounts.get(NORTH_ENABLED) + 1);
            }
            if (state.cells[NORTH_TO_SOUTH.x][NORTH_TO_SOUTH.y - i].hasACar) {
                carCounts.put(NORTH_ENABLED, carCounts.get(NORTH_ENABLED) + 1);
            }

            if (state.cells[SOUTH_TO_NORTH.x][SOUTH_TO_NORTH.y + i].hasACar) {
                carCounts.put(SOUTH_ENABLED, carCounts.get(SOUTH_ENABLED) + 1);
            }
            if (state.cells[SOUTH_TO_NORTH_CLOSER_TO_GRASS.x][SOUTH_TO_NORTH_CLOSER_TO_GRASS.y + i].hasACar) {
                carCounts.put(SOUTH_ENABLED, carCounts.get(SOUTH_ENABLED) + 1);
            }
        }
        return carCounts;
    }

    private TrafficEnabledLaneType getBusiestLane(Map<TrafficEnabledLaneType, Integer> carCounts) {
        if (northIterationsWaiting > MAX_ITERATIONS_WAITING) {
            return NORTH_ENABLED;
        }
        if (southIterationsWaiting > MAX_ITERATIONS_WAITING) {
            return SOUTH_ENABLED;
        }
        if (westIterationsWaiting > MAX_ITERATIONS_WAITING) {
            return WEST_ENABLED;
        }
        if (eastIterationsWaiting > MAX_ITERATIONS_WAITING) {
            return EAST_ENABLED;
        }

        return carCounts
            .entrySet()
            .stream()
            .max(Map.Entry.comparingByValue())
            .orElseThrow()
            .getKey();
    }

    private void prepareForChangingActiveLane(CrossroadState state) {
        setActiveLane(state, ALL_DISABLED);
        activeLaneIterations = 0;
        redLightIterations = 0;
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
