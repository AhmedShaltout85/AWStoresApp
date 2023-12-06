package com.ao8r.awstoresapp.models;

import java.util.ArrayList;

public class StoreReportModel{

    private String itemCode, itemNameReport, totalQtyNow,
    totalQtyInStartedPoint, totalQtyIncome, totalQtyOutcome, totalQtyEndPoint;

    public StoreReportModel() {
    }

    public StoreReportModel(String itemCode, String itemNameReport, String totalQtyNow, String totalQtyInStartedPoint, String totalQtyIncome, String totalQtyOutcome, String totalQtyEndPoint) {
        this.itemCode = itemCode;
        this.itemNameReport = itemNameReport;
        this.totalQtyNow = totalQtyNow;
        this.totalQtyInStartedPoint = totalQtyInStartedPoint;
        this.totalQtyIncome = totalQtyIncome;
        this.totalQtyOutcome = totalQtyOutcome;
        this.totalQtyEndPoint = totalQtyEndPoint;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemNameReport() {
        return itemNameReport;
    }

    public void setItemNameReport(String itemNameReport) {
        this.itemNameReport = itemNameReport;
    }

    public String getTotalQtyNow() {
        return totalQtyNow;
    }

    public void setTotalQtyNow(String totalQtyNow) {
        this.totalQtyNow = totalQtyNow;
    }

    public String getTotalQtyInStartedPoint() {
        return totalQtyInStartedPoint;
    }

    public void setTotalQtyInStartedPoint(String totalQtyInStartedPoint) {
        this.totalQtyInStartedPoint = totalQtyInStartedPoint;
    }

    public String getTotalQtyIncome() {
        return totalQtyIncome;
    }

    public void setTotalQtyIncome(String totalQtyIncome) {
        this.totalQtyIncome = totalQtyIncome;
    }

    public String getTotalQtyOutcome() {
        return totalQtyOutcome;
    }

    public void setTotalQtyOutcome(String totalQtyOutcome) {
        this.totalQtyOutcome = totalQtyOutcome;
    }

    public String getTotalQtyEndPoint() {
        return totalQtyEndPoint;
    }

    public void setTotalQtyEndPoint(String totalQtyEndPoint) {
        this.totalQtyEndPoint = totalQtyEndPoint;
    }
}
