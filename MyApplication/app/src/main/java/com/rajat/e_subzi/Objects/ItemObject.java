package com.rajat.e_subzi.Objects;

/**
 * Created by Rajat on 06-03-2016.
 */
public class ItemObject {
    String itemId;
    ProductObject productObject;
    int quantity;

    public ItemObject(String itemId, ProductObject productObject, int quantity) {
        this.itemId = itemId;
        this.productObject = productObject;
        this.quantity = quantity;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public ProductObject getProductObject() {
        return productObject;
    }

    public void setProductObject(ProductObject productObject) {
        this.productObject = productObject;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
