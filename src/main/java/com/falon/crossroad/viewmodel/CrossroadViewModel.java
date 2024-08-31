package com.falon.crossroad.viewmodel;

import com.falon.crossroad.CrossroadState;
import com.falon.crossroad.CrossroadViewStateMapper;
import com.falon.crossroad.factory.CrossroadStateFactory;

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
        CrossroadState newState = state.copy();

        for (int x = 0; x < state.cells.length; ++x) {
            for (int y = 0; y < state.cells[x].length; ++y) {
                state.cells[x][y].spawnCarIfNeeded();
            }
        }

        CrossroadViewState crossroadViewState = viewStateMapper.from(state);
        viewState.onNext(crossroadViewState);
    }

    public void onClear() {
    }
}
