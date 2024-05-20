package com.isep.appli.dbModels;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity //Mettre dans la BDD
public class Familia {

    //@PersistenceContext
    //private EntityManager entityManager;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description; // PB: Limite de taille de String dans la table, à régler

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String embleme_image;

    @Column(nullable = false)
    private Long leader_id;


}