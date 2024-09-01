package com.falon.crossroad.model;

public enum ActionCommand {

    TOGGLE_START,
    UNKNOWN;

    public static ActionCommand from(String command) {
        switch (command) {
            case "TOGGLE_START" -> {
                return TOGGLE_START;
            }
            default -> {
                return UNKNOWN;
            }
        }
    }
}
