package com.falon.crossroad.domain.model;

public enum ActionCommandType {

    TOGGLE_START,
    UNKNOWN;

    public static ActionCommandType from(String command) {
        if (command.equals("TOGGLE_START")) {
            return TOGGLE_START;
        }
        return UNKNOWN;
    }
}
