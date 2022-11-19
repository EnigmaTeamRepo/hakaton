package com.enigma.hakaton.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BillDTO {

    private Long id;

    @JsonProperty("bill_number")
    private String billNumber;

    @JsonProperty("bill_amount")
    private Long billAmount;

    private Integer currency;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public Long getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(Long billAmount) {
        this.billAmount = billAmount;
    }

    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }
}
