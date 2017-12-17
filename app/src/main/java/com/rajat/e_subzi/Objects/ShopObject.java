package com.rajat.e_subzi.Objects;

/**
 * Created by Rishab on 04-04-2016.
 */
public class ShopObject {
    String shopId;
    String  shopName;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String ShopId) {
        this.shopId = ShopId;
    }

    public String  getShopName() {
        return shopName;
    }

    public void setShopName(String  shopName) {
        this.shopName = shopName;
    }

    public ShopObject(String ShopId, String  shopName) {

        this.shopId = ShopId;
        this.shopName = shopName;
    }
}
