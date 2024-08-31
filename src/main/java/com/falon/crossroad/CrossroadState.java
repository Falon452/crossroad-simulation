package com.falon.crossroad;

import com.falon.crossroad.model.Cell;

public class CrossroadState {

    public Cell[][] cells;

    public CrossroadState copy() {
        CrossroadState newState = new CrossroadState();
        newState.cells = this.cells.clone();
        return newState;
    };
}
