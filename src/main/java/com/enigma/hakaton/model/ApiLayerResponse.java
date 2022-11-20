package com.enigma.hakaton.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ApiLayerResponse {

    private String base;
    @JsonProperty("end_date")
    private String endDate;
    @JsonProperty("start_date")
    private String startDate;
    private Boolean success;
    private Boolean timeseries;
    private Map<String, Map<String, String>> rates;

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getTimeseries() {
        return timeseries;
    }

    public void setTimeseries(Boolean timeseries) {
        this.timeseries = timeseries;
    }

    public Map<String, Map<String, String>> getRates() {
        return rates;
    }

    public void setRates(Map<String, Map<String, String>> rates) {
        this.rates = rates;
    }
}
