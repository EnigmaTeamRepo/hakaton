package com.enigma.hakaton.model;

import com.enigma.hakaton.model.enums.OperationType;

import javax.persistence.*;

@Entity
@Table(name = "e_operation")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "e_operation_seq")
    @SequenceGenerator(name = "e_operation_seq", sequenceName = "e_operation_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "operation_type")
    private OperationType operationType;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "bill_from")
    private Long billFrom;

    @Column(name = "bill_to")
    private Long billTo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBillFrom() {
        return billFrom;
    }

    public void setBillFrom(Long billFrom) {
        this.billFrom = billFrom;
    }

    public Long getBillTo() {
        return billTo;
    }

    public void setBillTo(Long billTo) {
        this.billTo = billTo;
    }
}
