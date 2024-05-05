package com.isep.appli.models;

import com.isep.appli.dbModels.Personnage;
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
