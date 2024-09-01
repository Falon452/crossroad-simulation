package com.falon.crossroad.viewmodel;

import com.falon.crossroad.CrossroadState;
import com.falon.crossroad.CrossroadViewStateMapper;
import com.falon.crossroad.factory.CrossroadStateFactory;

import com.falon.crossroad.model.Car;
import com.falon.crossroad.model.Driver;
import com.falon.crossroad.specification.PositionInBoundsSpecification;
import com.falon.crossroad.viewstate.CrossroadViewState;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class CrossroadViewModel {

    private CrossroadState state = new CrossroadStateFactory().create();
    private final PublishSubject<CrossroadViewState> viewState = PublishSubject.create();
    private final CrossroadViewStateMapper viewStateMapper = new CrossroadViewStateMapper();
    public CrossroadViewModel() {
        CrossroadViewState crossroadViewState = viewStateMapper.from(state);
        viewState.onNext(crossroadViewState);
    }

    public Observable<CrossroadViewState> viewStateObservable() {
        return viewState;
    }

    public void onIterate() {
        CrossroadState newState = state.newStateWithCopiedCellsAndEmptyCars();

        for (Driver driver : state.drivers) {
            Driver newDriver = driver.onIterate(state.cells);
            if (newDriver != null) {
                newState.drivers.add(newDriver);
            }
        }

        for (int x = 0; x < state.cells.length; ++x) {
            for (int y = 0; y < state.cells[x].length; ++y) {
                Car car = state.cells[x][y].spawnACarIfLucky();
                if (spawned(car)) {
                    newState.drivers.add(new Driver(car));
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

    private static boolean spawned(Car car) {
        return car != null;
    }

    public void onClear() {
    }
}
