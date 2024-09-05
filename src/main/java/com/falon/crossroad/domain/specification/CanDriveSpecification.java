package com.falon.crossroad.domain.specification;

import com.falon.crossroad.domain.model.Cell;
import com.falon.crossroad.domain.model.Driver;
import com.falon.crossroad.domain.model.TurnType;

public class CanDriveSpecification {

    public boolean isSatisfiedBy(Cell cell, Driver driver) {
        return !cell.hasACar && trafficColor(cell, driver);
    }

    private boolean trafficColor(Cell cell, Driver driver) {
        if (cell.trafficLightColor == null) {
            return true;
        }
        switch (cell.trafficLightColor) {
            case RED -> {
                return false;
            }
            case GREEN -> {
                return true;
            }
            case GREEN_FOR_RIGHT -> {
                return driver.getTurnType() == TurnType.RIGHT;
            }
        }
        return true;
    }
}
