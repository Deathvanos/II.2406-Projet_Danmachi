package com.isep.appli.dbModels;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Familia {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String embleme_image;

    @Column(nullable = false)
    private Long leader_id;

    @Override
    public String toString() {
        return "Familia{" +
                "id=" + id +
                ", description='" + description + '\'' +
                //", embleme_image='" + embleme_image + '\'' +
                ", leader_id=" + leader_id +
                '}';
    }
}