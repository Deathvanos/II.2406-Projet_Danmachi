package com.isep.appli.models;

import jakarta.persistence.*;

@Entity //Mettre dans la BDD
public class Familia {

    //@PersistenceContext
    //private EntityManager entityManager;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String description; // PB: Limite de taille de String dans la table, à régler

    @Column(nullable = false)
    private String embleme_image; //URL de l'image

    @Column(nullable = false)
    private Long leader_id; //(le leader est un joueur, et la photo du dieu c'est sa photo de profil)

    public Familia(){

    }
    public Familia(Long id, String description, String embleme_image, Long leader_id){
        this.id = id;
        this.description = description;
        this.embleme_image = embleme_image;
        this.leader_id = leader_id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getEmblemeImage() {
        return this.embleme_image;
    }

    public void setEmblemeImage(String embleme_image){
        this.embleme_image = embleme_image;
    }

    public Long getLeaderId() {
        return this.leader_id;
    }

    public void setLeaderId(Long leader_id){
        this.leader_id = leader_id;
    }

/* A compléter avec la classe personnage
    public String getLeaderName() {
        Familia familia = entityManager.find(Familia.class, familiaId);
    }
*/
}

