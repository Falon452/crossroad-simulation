package com.falon.crossroad.presentation.model;

public enum StrategyItem {
    BUSIEST("Busiest Lane First"),
    FIXED_ITERARTIONS("Fixed Iteration Count");

    public final String stringPresentation;

    StrategyItem(String stringPresentation) {
        this.stringPresentation = stringPresentation;
    }
}
