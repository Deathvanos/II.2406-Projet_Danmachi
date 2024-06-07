package com.isep.appli.dbModels;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "report")
public class Report implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long userFromId;

    @Column(nullable = false)
    private String objectReportedType;

    @Column(nullable = false)
    private Long objectReportedId;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String comment;
}
