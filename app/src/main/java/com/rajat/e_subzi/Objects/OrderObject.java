package com.rajat.e_subzi.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Rajat on 07-03-2016.
 */
public class OrderObject {

    String shopkeeperId = "";
    String currentState = "";
    String customerId = "";
    String customerEmail ="";
    String orderId = "";
    String created_at = "";
    String updated_at = "";
    ArrayList<ItemObject> itemObjArr;
    //String productId = "";
    //int quantity = 0;


    public OrderObject(String shopkeeperId, String currentState, String customerId, String customerEmail, String orderId, String created_at, String updated_at, ArrayList<ItemObject> itemObjArr) {
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

    public ArrayList<ItemObject> getItemObjArr() {
        return itemObjArr;
    }

    public void setItemObjArr(ArrayList<ItemObject> itemObjArr) {
        this.itemObjArr = itemObjArr;
    }
}
