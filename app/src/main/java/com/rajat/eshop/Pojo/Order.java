package com.rajat.eshop.Pojo;

import java.util.ArrayList;

/**
 * Created by Rajat on 07-03-2016.
 */
public class Order {

    private String shopkeeperId = "";
    private String currentState = "";
    private String customerId = "";
    private String customerEmail ="";
    private String orderId = "";
    private String created_at = "";
    private String updated_at = "";
    private ArrayList<Item> itemObjArr;



    public Order(String shopkeeperId, String currentState, String customerId, String customerEmail, String orderId, String created_at, String updated_at, ArrayList<Item> itemObjArr) {
        this.shopkeeperId = shopkeeperId;
        this.currentState = currentState;
        this.customerId = customerId;
        this.customerEmail = customerEmail;
        this.orderId = orderId;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.itemObjArr = itemObjArr;
    }

    public String getShopkeeperId() {
        return shopkeeperId;
    }

    public void setShopkeeperId(String shopkeeperId) {
        this.shopkeeperId = shopkeeperId;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public ArrayList<Item> getItemObjArr() {
        return itemObjArr;
    }

    public void setItemObjArr(ArrayList<Item> itemObjArr) {
        this.itemObjArr = itemObjArr;
    }
}
