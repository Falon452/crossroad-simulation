package com.falon.crossroad.specification;

import com.falon.crossroad.model.Cell;
import com.falon.crossroad.model.TrafficLightColor;

public class CanDriveSpecification {

    public boolean isSatisfiedBy(Cell cell) {
        return !cell.hasACar && cell.trafficLightColor != TrafficLightColor.RED;
    }
}
