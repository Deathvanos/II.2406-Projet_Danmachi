package com.isep.appli.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PersonnageDto {
    private Personnage personnage;
    private String raceString;
}
