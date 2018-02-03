package com.rajat.eshop.Pojo;

/**
 * Created by Rajat on 05-03-2016.
 */
public class Product {
    private String created_at= "";
    private String updated_at= "";
    private String userId= "";
    private int discount=0;
    private String description= "";
    private int quantity= 0;
    private int price= 0;
    private String productId= "";
    private String userEmail="";
    private String photoUrl="";

    public Product(String created_at, String updated_at, String userId, int discount, String description, int quantity, int price, String productId, String userEmail, String photoUrl) {
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.userId = userId;
        this.discount = discount;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.productId = productId;
        this.userEmail = userEmail;
        this.photoUrl = photoUrl;
    }

//    public Product(String created_at, String updated_at, String userId, int discount, String description, int quantity, int price, String productId, String userEmail) {
//        this.created_at = created_at;
//        this.updated_at = updated_at;
//        this.userId = userId;
//        this.discount = discount;
//        this.description = description;
//        this.quantity = quantity;
//        this.price = price;
//        this.productId = productId;
//        this.userEmail = userEmail;
//    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
