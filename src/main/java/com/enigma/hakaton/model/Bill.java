package com.enigma.hakaton.model;

import com.enigma.hakaton.model.enums.Currency;

import javax.persistence.*;

@Entity
@Table(name = "e_bill")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "e_bill_seq")
    @SequenceGenerator(name = "e_bill_seq", sequenceName = "e_bill_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "bill_number")
    private String billNumber;

    @Column(name = "bill_amount")
    private Long billAmount;

    @Column(name = "currency")
    private Currency currency;

    @ManyToOne(targetEntity = User.class)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
