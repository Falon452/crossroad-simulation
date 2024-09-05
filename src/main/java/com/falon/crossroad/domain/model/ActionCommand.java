package com.falon.crossroad.domain.model;

public enum ActionCommand {

    TOGGLE_START,
    UNKNOWN;

    public static ActionCommand from(String command) {
        if (command.equals("TOGGLE_START")) {
            return TOGGLE_START;
        }
        return UNKNOWN;
    }
}
