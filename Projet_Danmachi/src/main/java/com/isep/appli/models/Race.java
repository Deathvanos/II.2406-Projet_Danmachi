package com.isep.appli.models;

public enum Race {
    HUMAN("humain"),
    AMAZON("Amazonne"),
    BEAST("homme-bête");

    private final String displayName;

    Race(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
