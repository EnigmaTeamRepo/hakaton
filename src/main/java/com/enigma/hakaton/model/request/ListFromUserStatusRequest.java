package com.enigma.hakaton.model.request;

import com.enigma.hakaton.model.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ListFromUserStatusRequest {

    @JsonProperty("user_status")
    private UserStatus userStatus;

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }
}
