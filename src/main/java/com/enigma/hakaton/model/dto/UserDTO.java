package com.enigma.hakaton.model.dto;

import com.enigma.hakaton.model.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Set;

public class UserDTO {

    @JsonProperty("user_id")
    private Long userId;

    private String login;

    @JsonProperty("user_status")
    private UserStatus userStatus;

    @JsonProperty("registration_date")
    private Date registrationDate;

    private String name;

    private String patronymic;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("birth_date")
    private String birthDate;

    @JsonProperty("passport")
    private String passportSerial;

    @JsonProperty("passport_issued_by")
    private String passportIssued;

    @JsonProperty("sex")
    private String gender;

    @JsonProperty("born_place")
    private String bornPlace;

    private Set<BillDTO> bills;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassportSerial() {
        return passportSerial;
    }

    public void setPassportSerial(String passportSerial) {
        this.passportSerial = passportSerial;
    }

    public String getPassportIssued() {
        return passportIssued;
    }

    public void setPassportIssued(String passportIssued) {
        this.passportIssued = passportIssued;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBornPlace() {
        return bornPlace;
    }

    public void setBornPlace(String bornPlace) {
        this.bornPlace = bornPlace;
    }

    public Set<BillDTO> getBills() {
        return bills;
    }

    public void setBills(Set<BillDTO> bills) {
        this.bills = bills;
    }
}
