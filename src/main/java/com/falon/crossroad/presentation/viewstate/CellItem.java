package com.falon.crossroad.presentation.viewstate;

import java.awt.*;

public class CellItem {

    public Color squareColor;

    public Color circleColor;

    public CellItem(Color squareColor, Color circleColor) {
        this.squareColor = squareColor;
        this.circleColor = (circleColor != null) ? circleColor : squareColor;
    }
}

