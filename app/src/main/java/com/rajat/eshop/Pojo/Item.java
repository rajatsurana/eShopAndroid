package com.rajat.eshop.Pojo;

/**
 * Created by Rajat on 06-03-2016.
 */
public class Item {
    private String itemId;
    private Product product;
    private int quantity;

    public Item(String itemId, Product product, int quantity) {
        this.itemId = itemId;
        this.product = product;
        this.quantity = quantity;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
