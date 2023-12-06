package com.ao8r.awstoresapp.models;

public class StoresNamesModel {
    private String storeNameReport;

    public StoresNamesModel() {
    }

    public StoresNamesModel(String storeNameReport) {
        this.storeNameReport = storeNameReport;
    }

    public String getStoreNameReport() {
        return storeNameReport;
    }

    public void setStoreNameReport(String storeNameReport) {
        this.storeNameReport = storeNameReport;
    }

    @Override
    public String toString() {
        return storeNameReport;

    }
}
