package com.servicesinvestproducts.servicesinvestproducts.model;


import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user", schema="public")
//No resuelve bien la tabla user si no le pongo el esquema public
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceUser")
    @SequenceGenerator(name = "SequenceUser", sequenceName = "SequenceUser",
            allocationSize = 1,initialValue=1)
    private int id;

    @Column(name = "forename", nullable = false, unique = true)
    private String forename;

    @Column(name = "surename", nullable = false, unique = true)
    private String surename;

    @Column(name = "uid", nullable = false, unique = true)
    private String uid;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserProduct> userProduct = new HashSet<>();


}
