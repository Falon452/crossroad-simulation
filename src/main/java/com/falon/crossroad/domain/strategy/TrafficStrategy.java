package com.falon.crossroad.domain.strategy;

import com.falon.crossroad.presentation.state.CrossroadState;

public interface TrafficStrategy {

    void execute(CrossroadState state);
}
