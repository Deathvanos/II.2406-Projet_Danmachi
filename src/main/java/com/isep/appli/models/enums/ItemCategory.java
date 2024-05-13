package com.isep.appli.models.enums;

public enum ItemCategory {
    EQUIPEMENT("Equipement"),
    CONSOMMABLE("Consommable"),
    RESSOURCE("Ressource"),
    ARTEFACT("Artéfact");

    private final String displayName;

    ItemCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
