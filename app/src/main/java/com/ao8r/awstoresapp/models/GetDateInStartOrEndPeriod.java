package com.ao8r.awstoresapp.models;

public class GetDateInStartOrEndPeriod {
    private String startDatePeriod, endDatePeriod;

    public GetDateInStartOrEndPeriod() {
    }

    public GetDateInStartOrEndPeriod(String startDatePeriod, String endDatePeriod) {
        this.startDatePeriod = startDatePeriod;
        this.endDatePeriod = endDatePeriod;
    }

    public String getStartDatePeriod() {
        return startDatePeriod;
    }

    public void setStartDatePeriod(String startDatePeriod) {
        this.startDatePeriod = startDatePeriod;
    }

    public String getEndDatePeriod() {
        return endDatePeriod;
    }

    public void setEndDatePeriod(String endDatePeriod) {
        this.endDatePeriod = endDatePeriod;
    }
}
