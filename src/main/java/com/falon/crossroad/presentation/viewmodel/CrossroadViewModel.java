package com.falon.crossroad.presentation.viewmodel;

import com.falon.crossroad.domain.strategy.FixedIterationCountStrategy;
import com.falon.crossroad.presentation.state.CrossroadState;
import com.falon.crossroad.presentation.mapper.CrossroadViewStateMapper;
import com.falon.crossroad.presentation.factory.CrossroadStateFactory;

import com.falon.crossroad.domain.model.Driver;
import com.falon.crossroad.domain.model.TrafficLight;
import com.falon.crossroad.domain.strategy.TrafficStrategy;
import com.falon.crossroad.presentation.viewstate.CrossroadViewState;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

import static com.falon.crossroad.domain.model.CellsSize.CELLS_IN_HEIGHT;
import static com.falon.crossroad.domain.model.CellsSize.CELLS_IN_WIDTH;
import static com.falon.crossroad.domain.model.LaneType.*;

public class CrossroadViewModel {

    private CrossroadState state = new CrossroadStateFactory().create();
    private final PublishSubject<CrossroadViewState> viewState = PublishSubject.create();
    private final CrossroadViewStateMapper viewStateMapper = new CrossroadViewStateMapper();
    private TrafficStrategy trafficStrategy = new FixedIterationCountStrategy();


    public CrossroadViewModel() {
        CrossroadViewState crossroadViewState = viewStateMapper.from(state);
        viewState.onNext(crossroadViewState);
    }

    public Observable<CrossroadViewState> viewStateObservable() {
        return viewState;
    }

    public void onIterate() {
        state.iteration += 1;
        trafficStrategy.execute(state);

        for (TrafficLight trafficLight : state.trafficLights) {
            state.cells[trafficLight.x][trafficLight.y].trafficLightColor = trafficLight.color;
        }

        CrossroadState newState = state.newStateWithCopiedCellsAndEmptyCars();

        for (Driver driver : state.drivers) {
            Driver newDriver = driver.onIterate(state.cells);
            if (newDriver != null) {
                newState.drivers.add(newDriver);
            }
        }

        for (int x = 0; x < state.cells.length; ++x) {
            for (int y = 0; y < state.cells[x].length; ++y) {
                Driver driver = state.cells[x][y].spawnADriverIfLucky();
                if (spawned(driver)) {
                    newState.drivers.add(driver);
                }
            }
        }

        for (int x = 0; x < newState.cells.length; ++x) {
            for (int y = 0; y < newState.cells[x].length; ++y) {
                newState.cells[x][y].hasACar = false;
            }
        }

        for (Driver driver : newState.drivers) {
            newState.cells[driver.getX()][driver.getY()].hasACar = true;
        }

        state = newState;
        CrossroadViewState crossroadViewState = viewStateMapper.from(state);
        viewState.onNext(crossroadViewState);
    }

    private static boolean spawned(Driver driver) {
        return driver != null;
    }

    public void onTrafficEastSliderChange(int value) {
        state.cells[CELLS_IN_WIDTH - 1][LANE_FROM_EAST_TO_WEST.index()].spawnDriverOccursInPercent = value;
        state.cells[CELLS_IN_WIDTH - 1][LANE_FROM_EAST_TO_WEST_CLOSER_TO_GRASS.index()].spawnDriverOccursInPercent = value;
    }

    public void onTrafficSouthSliderChange(int value) {
        state.cells[LANE_FROM_SOUTH_TO_NORTH.index()][CELLS_IN_HEIGHT - 1].spawnDriverOccursInPercent = value;
        state.cells[LANE_FROM_SOUTH_TO_NORTH_CLOSER_TO_GRASS.index()][CELLS_IN_HEIGHT - 1].spawnDriverOccursInPercent = value;
    }

    public void onTrafficWestSliderChange(int value) {
        state.cells[0][LANE_FROM_WEST_TO_EAST.index()].spawnDriverOccursInPercent = value;
        state.cells[0][LANE_FROM_WEST_TO_EAST_CLOSER_TO_GRASS.index()].spawnDriverOccursInPercent = value;
    }

    public void onTrafficNorthSliderChange(int value) {
        state.cells[LANE_FROM_NORTH_TO_SOUTH.index()][0].spawnDriverOccursInPercent = value;
        state.cells[LANE_FROM_NORTH_TO_SOUTH_CLOSER_TO_GRASS.index()][0].spawnDriverOccursInPercent = value;
    }

    public void setStrategy(TrafficStrategy trafficStrategy) {
        this.trafficStrategy = trafficStrategy;
    }
}
