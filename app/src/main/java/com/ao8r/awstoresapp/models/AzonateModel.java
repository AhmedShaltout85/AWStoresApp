package com.ao8r.awstoresapp.models;

public class AzonateModel {
//    declare vars
    private String itemName, storeName;
    private int storeNumber;
    private String storeTotalQuantity;
    private String lastDateSend;
    private int itemNumber;

    public AzonateModel(String itemName,
                        String storeName,
                        int storeNumber,
                        String storeTotalQuantity,
                        String lastDateSend,
                        int itemNumber) {
        this.itemName = itemName;
        this.storeName = storeName;
        this.storeNumber = storeNumber;
        this.storeTotalQuantity = storeTotalQuantity;
        this.lastDateSend = lastDateSend;
        this.itemNumber = itemNumber;
    }

    //constructors
    public AzonateModel() {
    }

    public AzonateModel(String itemName,
                        String storeName,
                        int storeNumber,
                        String storeTotalQuantity,
                        String lastDateSend) {
        this.itemName = itemName;
        this.storeName = storeName;
        this.storeNumber = storeNumber;
        this.storeTotalQuantity = storeTotalQuantity;
        this.lastDateSend = lastDateSend;
    }

    public int getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getLastDateSend() {
        return lastDateSend;
    }

    public void setLastDateSend(String lastDateSend) {
        this.lastDateSend = lastDateSend;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getStoreNumber() {
        return storeNumber;
    }

    public void setStoreNumber(int storeNumber) {
        this.storeNumber = storeNumber;
    }

    public String getStoreTotalQuantity() {
        return storeTotalQuantity;
    }

    public void setStoreTotalQuantity(String storeTotalQuantity) {
        this.storeTotalQuantity = storeTotalQuantity;
    }
}
