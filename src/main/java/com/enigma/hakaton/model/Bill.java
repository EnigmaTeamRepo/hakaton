package com.enigma.hakaton.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Bill {

    private Long id;

    @ManyToOne
    private Long userId;
}
