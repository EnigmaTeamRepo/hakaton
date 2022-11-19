package com.enigma.hakaton.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserIdJsonRequest {

    @JsonProperty("user_id")
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
