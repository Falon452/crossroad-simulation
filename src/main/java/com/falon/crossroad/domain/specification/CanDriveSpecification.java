package com.falon.crossroad.domain.specification;

import com.falon.crossroad.domain.model.Cell;
import com.falon.crossroad.domain.model.TrafficLightColor;

public class CanDriveSpecification {

    public boolean isSatisfiedBy(Cell cell) {
        return !cell.hasACar && cell.trafficLightColor != TrafficLightColor.RED;
    }
}
