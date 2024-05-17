package com.isep.appli.dbModels;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "discussion")
public class Discussion implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String conversationType;

    @Column
    private Long familiaId;

    @Column
    private Long firstPersonnageId;

    @Column
    private Long secondPersonnageId;
}