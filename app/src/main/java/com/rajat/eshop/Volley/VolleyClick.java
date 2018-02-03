package com.rajat.eshop.Volley;
import android.content.Context;
import android.content.Intent;

import com.rajat.eshop.LoginActivity;
import com.rajat.eshop.Tools.CheckNetwork;
import com.rajat.eshop.Tools.Tools;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Rajat on 07-03-2016.
 */
public class VolleyClick {
//128.199.152.41
    static String IP = "http://192.168.43.200:3000";
//    static String IP="http://139.59.30.244:3000";//http://128.199.152.41:3000";
    public static void signUp(String email,String  password,String address,String phoneNum,String type,Context context){
        CheckNetwork chkNet = new CheckNetwork(context);
        String URL = IP+"/api/signup";
        if (chkNet.checkNetwork()) {
            VolleySingleton.getInstance(context).getRequestQueue().getCache().clear();
            CallVolley.signUpCall(URL, context, email, password, address, phoneNum, type, 3);
        } else {
            Tools.showAlertDialog("Internet Unavailable", context);
        }
    }
    public static void loginClick(String email,String  password,Context context){
        CheckNetwork chkNet = new CheckNetwork(context);
        String URL = IP+"/api/login";
        if (chkNet.checkNetwork()) {
            VolleySingleton.getInstance(context).getRequestQueue().getCache().clear();
            CallVolley.loginCall(URL, context, email, password, 3);
        } else {
            Tools.showAlertDialog("Internet Unavailable", context);
        }
    }

    public static void createProductClick(int price ,int quantity, String description, String userId ,String userEmail,int discount,Boolean deliveryAvailable,Context context, File file){
        CheckNetwork chkNet = new CheckNetwork(context);
        String URL = IP+"/api/products/create";
        if (chkNet.checkNetwork()) {
            VolleySingleton.getInstance(context).getRequestQueue().getCache().clear();
            CallVolley.createProductCall(URL, context, price, quantity, description, userId, userEmail, discount,deliveryAvailable, 3, file);
        } else {
            Tools.showAlertDialog("Internet Unavailable", context);
        }
    }
    public static void findProductsClick(String userId,Context context){
        CheckNetwork chkNet = new CheckNetwork(context);
        String URL = IP+"/api/products/find";
        if (chkNet.checkNetwork()) {
            //VolleySingleton.getInstance(context).getRequestQueue().getCache().clear();
            CallVolley.findProductsCall(URL, context, userId, 3);
        } else {
            Tools.showAlertDialog("Internet Unavailable", context);
        }
    }
    public static void findDiscountsClick(String userId,Context context){
       CheckNetwork chkNet = new CheckNetwork(context);
        String URL = IP+"/api/products/get";
        if (chkNet.checkNetwork()) {
            VolleySingleton.getInstance(context).getRequestQueue().getCache().clear();
            CallVolley.findDiscountsCall(URL, context, userId, 3);
        } else {
            Tools.showAlertDialog("Internet Unavailable", context);
        }
    }
    public static void findOffersClick(Context context){
        CheckNetwork chkNet = new CheckNetwork(context);
        String URL = IP+"/api/discounts/get";
        if (chkNet.checkNetwork()) {
            VolleySingleton.getInstance(context).getRequestQueue().getCache().clear();
            CallVolley.findOffersCall(URL, context, 3);
        } else {
            Tools.showAlertDialog("Internet Unavailable", context);
        }
    }
    public static void deleteProductClick(String productId,String userId,Context context){
        CheckNetwork chkNet = new CheckNetwork(context);
        String URL = IP+"/api/products/delete";
        if (chkNet.checkNetwork()) {
        VolleySingleton.getInstance(context).getRequestQueue().getCache().clear();
        CallVolley.deleteProductCall(URL, context, productId, userId, 3);
        } else {
            Tools.showAlertDialog("Internet Unavailable", context);
        }
    }
    public static void registerDeviceClick(String regId,String email,String type,Context con){
        CheckNetwork chkNet = new CheckNetwork(con);
        String URL = IP+"/api/register";
        if (chkNet.checkNetwork()) {
            VolleySingleton.getInstance(con).getRequestQueue().getCache().clear();
            CallVolley.registerDeviceCall(URL, con, regId, email, type, 3);
        } else {
            Tools.showAlertDialog("Internet UnAvailable", con);
        }
    }
    public static void updatePriceClick(String productId,int price,Context context){
        CheckNetwork chkNet = new CheckNetwork(context);
        String URL = IP+"/api/updatePrice";
      if (chkNet.checkNetwork()) {
            VolleySingleton.getInstance(context).getRequestQueue().getCache().clear();
            CallVolley.updateProductPriceCall(URL, context, productId, price, 3);
        } else {
            Tools.showAlertDialog("Internet Unavailable", context);
        }
    }
    public static void updateQuantityClick(String productId,int quantity,Context context){
        CheckNetwork chkNet = new CheckNetwork(context);
        String URL = IP+"/api/updateQuantity";
       if (chkNet.checkNetwork()) {
        VolleySingleton.getInstance(context).getRequestQueue().getCache().clear();
        CallVolley.updateProductQuantityCall(URL, context, productId, quantity, 3);
        } else {
            Tools.showAlertDialog("Internet Unavailable", context);
        }
    }
    public static void updateNameClick(String productId,String name,Context context){
        CheckNetwork chkNet = new CheckNetwork(context);
        String URL = IP+"/api/updateName";
       if (chkNet.checkNetwork()) {
        VolleySingleton.getInstance(context).getRequestQueue().getCache().clear();
        CallVolley.updateProductNameCall(URL, context, productId, name, 3);
        } else {
            Tools.showAlertDialog("Internet Unavailable", context);
        }
    }
    public static void changeDiscountClick(String productId,int discount,Context context){
        CheckNetwork chkNet = new CheckNetwork(context);
        String URL = IP+"/api/changeDiscount";
        if (chkNet.checkNetwork()) {
            VolleySingleton.getInstance(context).getRequestQueue().getCache().clear();
            CallVolley.changeProductDiscountCall(URL, context, productId, discount, 3);
        } else {
            Tools.showAlertDialog("Internet Unavailable", context);
        }
    }
    public static void createDiscountClick(String shopId,String discountDesc,Context context){
        CheckNetwork chkNet = new CheckNetwork(context);
        String URL = IP+"/api/discounts/create";
        if (chkNet.checkNetwork()) {
        VolleySingleton.getInstance(context).getRequestQueue().getCache().clear();
        CallVolley.createDiscountCall(URL, context, shopId, discountDesc, 3);
        } else {
            Tools.showAlertDialog("Internet Unavailable", context);
        }
    }
    //placeOrderCall
    public static void placeOrderClick(String customerId,String customerEmail,String shopkeeperId,ArrayList<String> productIds,ArrayList<Float> quantities,Context context){
        CheckNetwork chkNet = new CheckNetwork(context);
        String URL = IP+"/api/placeOrder";
        if (chkNet.checkNetwork()) {
            VolleySingleton.getInstance(context).getRequestQueue().getCache().clear();
            CallVolley.placeOrderCall(URL, context, customerId, customerEmail, shopkeeperId, productIds, quantities, 3);
        } else {
            Tools.showAlertDialog("Internet Unavailable", context);
        }
    }
    public static void changeOrderStateClick(String orderId,String orderState,Context context){
        CheckNetwork chkNet = new CheckNetwork(context);
        String URL = IP+"/api/changeOrderState";
        if (chkNet.checkNetwork()) {
            VolleySingleton.getInstance(context).getRequestQueue().getCache().clear();
            CallVolley.changeOrderStateCall(URL, context, orderId, orderState, 3);
        } else {
            Tools.showAlertDialog("Internet Unavailable", context);
        }
    }
    public static void changePreferencesClick(String userId,Context context,ArrayList<String> shops){
        CheckNetwork chkNet = new CheckNetwork(context);
        String URL = IP+"/api/change_order_state";
       if (chkNet.checkNetwork()) {
        VolleySingleton.getInstance(context).getRequestQueue().getCache().clear();
        CallVolley.changePreferencesCall(URL, context, userId, shops, 3);
        } else {
            Tools.showAlertDialog("Internet Unavailable", context);
        }
    }
    public static void setSubscriptionsClick(String userId,ArrayList<String> shopIds,Context context){
        CheckNetwork chkNet = new CheckNetwork (context);
        String URL = IP+"/api/setSubscriptions";
        if (chkNet.checkNetwork()) {
        VolleySingleton.getInstance(context).getRequestQueue().getCache().clear();
        CallVolley.setSubscriptionsCall(URL, context, userId, shopIds, 3);
        } else {
            Tools.showAlertDialog("Internet Unavailable", context);
        }
    }

    public static void getSubscriptionClick(String deviceId,Context context){
       CheckNetwork chkNet = new CheckNetwork(context);
        String URL = IP+"/api/getSubscriptions";
        if (chkNet.checkNetwork()) {
        VolleySingleton.getInstance(context).getRequestQueue().getCache().clear();
        CallVolley.getSubscriptionsCall(URL, context, deviceId, 3);
        } else {
            Tools.showAlertDialog("Internet Unavailable", context);
        }
    }
    public static void findOrdersClick(String userId,String usertype,Context context){
        CheckNetwork chkNet = new CheckNetwork(context);
        String URL = IP+"/api/findOrdersNotDelivered";
       if (chkNet.checkNetwork()) {
            VolleySingleton.getInstance(context).getRequestQueue().getCache().clear();
            CallVolley.findOrdersCall(URL, context, userId, usertype, 3);
        } else {
            Tools.showAlertDialog("Internet Unavailable", context);
        }
    }
    public static void logoutClick(String deviceId,Context context){
        CheckNetwork chkNet = new CheckNetwork(context);
        //String URL = IP+"/api/unregister";
        if (chkNet.checkNetwork()) {
        VolleySingleton.getInstance(context).getRequestQueue().getCache().clear();
//        CallVolley.logoutCall(URL, context, deviceId, 3);
            context.getSharedPreferences("MyPrefs", 0).edit().clear().commit();
            Intent intent = new Intent(context, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        } else {
            Tools.showAlertDialog("Internet Unavailable", context);
        }
    }

}
