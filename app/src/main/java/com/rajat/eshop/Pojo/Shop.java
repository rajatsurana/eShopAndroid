package com.rajat.eshop.Pojo;

/**
 * Created by Rishab on 04-04-2016.
 */
public class Shop {
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

    public Shop(String ShopId, String  shopName) {

        this.shopId = ShopId;
        this.shopName = shopName;
    }
}
