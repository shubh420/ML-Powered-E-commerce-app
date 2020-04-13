package io.shubh.e_commver1.Models;

import com.google.firebase.firestore.PropertyName;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {

    //below  field is not supposed to have the same in the BagItem document in firestore
    //this below object will be added manually
    //its purpose is for the time when on the obejct is clicked on am=nd then we have to show the itemdetail page
    ItemsForSale itemObject;
    ArrayList<BagItem> bagItems ;
    AdressItem adressItem;

    long timeOfCreationOfOrder;
    long timeOfPaymentSuccessOfItem;
    long timeOfDeliveryOfItem;

    int orderId ;
    String userId;



    public Order() {

    }

    public ItemsForSale getItemObject() {
        return itemObject;
    }

    public void setItemObject(ItemsForSale itemObject) {
        this.itemObject = itemObject;
    }

    public ArrayList<BagItem> getBagItems() {
        return bagItems;
    }

    public void setBagItems(ArrayList<BagItem> bagItems) {
        this.bagItems = bagItems;
    }

    public long getTimeOfCreationOfOrder() {
        return timeOfCreationOfOrder;
    }

    public void setTimeOfCreationOfOrder(long timeOfCreationOfOrder) {
        this.timeOfCreationOfOrder = timeOfCreationOfOrder;
    }

    public long getTimeOfPaymentSuccessOfItem() {
        return timeOfPaymentSuccessOfItem;
    }

    public void setTimeOfPaymentSuccessOfItem(long timeOfPaymentSuccessOfItem) {
        this.timeOfPaymentSuccessOfItem = timeOfPaymentSuccessOfItem;
    }

    public long getTimeOfDeliveryOfItem() {
        return timeOfDeliveryOfItem;
    }

    public void setTimeOfDeliveryOfItem(long timeOfDeliveryOfItem) {
        this.timeOfDeliveryOfItem = timeOfDeliveryOfItem;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public AdressItem getAdressItem() {
        return adressItem;
    }

    public void setAdressItem(AdressItem adressItem) {
        this.adressItem = adressItem;
    }

/*
    //non property field
    public ItemsForSale getItemObject() {
        return itemObject;
    }

    public void setItemObject(ItemsForSale itemObject) {
        this.itemObject = itemObject;
    }


    @PropertyName("item amount")
    public String getItemAmount() {
        return itemAmount;
    }

    @PropertyName("item amount")
    public void setItemAmount(String itemAmount) {
        this.itemAmount = itemAmount;
    }

    @PropertyName("time of upload")
    public long getTime_of_upload() {
        return time_of_upload;
    }

    @PropertyName("time of upload")
    public void setTime_of_upload(long time_of_upload) {
        this.time_of_upload = time_of_upload;
    }

    @PropertyName("item id")
    public String getItemId() {
        return itemId;
    }

    @PropertyName("item id")
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @PropertyName("user id")
    public String getUserId() {
        return userId;
    }

    @PropertyName("user id")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @PropertyName("selected variety index in list")
    public String getSelectedVarietyIndexInList() {
        return selectedVarietyIndexInList;
    }

    @PropertyName("selected variety index in list")
    public void setSelectedVarietyIndexInList(String selectedVarietyIndexInList) {
        this.selectedVarietyIndexInList = selectedVarietyIndexInList;
    }


    public boolean isTheOriginalItemDeleted() {
        return isTheOriginalItemDeleted;
    }

    public void setTheOriginalItemDeleted(boolean theOriginalItemDeleted) {
        isTheOriginalItemDeleted = theOriginalItemDeleted;
    }
*/


}
