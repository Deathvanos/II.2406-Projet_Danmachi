package com.isep.appli.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PersonnageDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String image;
    private int level;
    private int money;
    private long userId;
    private String raceString;
    private String description;
    private String story;
    private String userUsername;
}
