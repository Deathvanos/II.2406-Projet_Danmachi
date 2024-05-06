package com.isep.appli.models;

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

    @Column(nullable = false)
    private String description; // PB: Limite de taille de String dans la table, à régler

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String embleme_image;

    @Column(nullable = false)
    private Long leader_id; //(le leader est un joueur, et la photo du dieu c'est sa photo de profil)


}