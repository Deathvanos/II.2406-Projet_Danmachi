package com.isep.appli.models;

public enum Race {
    HUMAN("Humain"),
    AMAZONESS("Amazonne"),
    BOAZ("Homme-bête"),
    DARKELF("Elfe noir"),
    DWARF("Nain"),
    ELF("Elfe"),
    GOD("Dieu"),
    PALLUM("Pallum"),
    SPIRIT("Esprit");

    private final String displayName;

    Race(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
