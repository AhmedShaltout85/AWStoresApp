package com.ao8r.awstoresapp.models;

public class FavoritesModel {
    //    declare vars

    private String favItemName, favItemNumber;
    private String favTotalAllStoreQuantity;
    private long favUserId;

    public FavoritesModel() {
    }

    public FavoritesModel(String storeName, int itemNumber, String storeTotalQuantity) {
    }

    public FavoritesModel(String favItemNumber, long favUserId) {
        this.favItemNumber = favItemNumber;
        this.favUserId = favUserId;
    }

    public FavoritesModel(String favItemNumber, String favTotalAllStoreQuantity) {
        this.favItemNumber = favItemNumber;
        this.favTotalAllStoreQuantity = favTotalAllStoreQuantity;
    }

    public FavoritesModel(String favItemName, String favItemNumber, String favTotalAllStoreQuantity) {
        this.favItemName = favItemName;
        this.favItemNumber = favItemNumber;
        this.favTotalAllStoreQuantity = favTotalAllStoreQuantity;
    }

    public FavoritesModel(String favItemName, String favItemNumber, String favTotalAllStoreQuantity, long favUserId) {
        this.favItemName = favItemName;
        this.favItemNumber = favItemNumber;
        this.favTotalAllStoreQuantity = favTotalAllStoreQuantity;
        this.favUserId = favUserId;
    }

    public String getFavItemName() {
        return favItemName;
    }

    public void setFavItemName(String favItemName) {
        this.favItemName = favItemName;
    }

    public String getFavItemNumber() {
        return favItemNumber;
    }

    public void setFavItemNumber(String favItemNumber) {
        this.favItemNumber = favItemNumber;
    }

    public String getFavTotalAllStoreQuantity() {
        return favTotalAllStoreQuantity;
    }

    public void setFavTotalAllStoreQuantity(String favTotalAllStoreQuantity) {
        this.favTotalAllStoreQuantity = favTotalAllStoreQuantity;
    }

    public long getFavUserId() {
        return favUserId;
    }

    public void setFavUserId(long favUserId) {
        this.favUserId = favUserId;
    }
}
