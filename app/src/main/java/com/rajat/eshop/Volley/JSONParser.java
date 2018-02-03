package com.rajat.eshop.Volley;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.rajat.eshop.Discounts;
import com.rajat.eshop.Pojo.Item;
import com.rajat.eshop.Pojo.Order;
import com.rajat.eshop.Pojo.Product;
import com.rajat.eshop.Pojo.Shop;
import com.rajat.eshop.OffersActivity;
import com.rajat.eshop.OrdersActivity;
import com.rajat.eshop.PermissionForm;
import com.rajat.eshop.ProductsActivity;
import com.rajat.eshop.ShopsActivity;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Rajat on 19-02-2016.
 */
public class JSONParser {
    public static void SignUpApiJsonParser(String JsonStringResult, Context con) {
        try {
            //boolean status;
            String email = "";
            String userId = "";
            String error = "";
            String token = "";
            String type = "";
            //create json object from response string
            JSONObject resultJson = new JSONObject(JsonStringResult);
            if(resultJson.has("message")){
                Toast.makeText(con, "Invalid "+resultJson.getString("message"),
                        Toast.LENGTH_LONG).show();
                return;
            }
            if (resultJson.has("token")) {
                SharedPreferences pref = con.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                token = resultJson.getString("token");
                editor.putString("token", token);


                if (resultJson.has("email")) {
                    email = resultJson.getString("email");
                    editor.putString("email", email);
                }
                if (resultJson.has("userId")) {
                    userId = resultJson.getString("userId");
                    editor.putString("userId", userId);
                }
                if (resultJson.has("type")) {
                    type = resultJson.getString("type");
                    editor.putString("type", type);
                }
                editor.commit();
                if(type.equals("Shopkeeper")){
                    VolleyClick.findProductsClick(userId,con);
                }
                else{
                    VolleyClick.findDiscountsClick(userId,con);
                }
            } else if (resultJson.has("error")) {
                error = resultJson.getString("error");
                Toast.makeText(con,error,Toast.LENGTH_SHORT).show();
            }
            Log.i("rajat", email + " " + userId + " " + token + " " + type + " " + error);
//            Tools.showAlertDialog(email + " " + userId + " " + token + " " + type + " " + error, con);

        } catch (Exception e) {
            Log.i("rajat", "Exception: LoginActivity: " + e.getLocalizedMessage());
        }
    }

    public static void LoginApiJsonParser(String JsonStringResult, Context con) {
        try {
            //boolean status;
            String email = "";
            String userId = "";
            String error = "";
            String token = "";
            String type = "";
            //create json object from response string
            JSONObject resultJson = new JSONObject(JsonStringResult);
            if(resultJson.has("message")){
                Toast.makeText(con, "Invalid "+resultJson.getString("message"),
                        Toast.LENGTH_LONG).show();
                return;
            }
            if (resultJson.has("token")) {
                SharedPreferences pref = con.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                token = resultJson.getString("token");
                editor.putString("token", token);


                if (resultJson.has("email")) {
                    email = resultJson.getString("email");
                    editor.putString("email", email);
                }
                if (resultJson.has("userId")) {
                    userId = resultJson.getString("userId");
                    editor.putString("userId", userId);
                }
                if (resultJson.has("type")) {
                    type = resultJson.getString("type");
                    editor.putString("type", type);
                }
                editor.commit();
                if(type.equals("Shopkeeper")){
                    VolleyClick.findProductsClick(userId,con);
                }
                else{

                    VolleyClick.findDiscountsClick(userId,con);
                }
            } else if (resultJson.has("error")) {
                error = resultJson.getString("error");
                Toast.makeText(con, "Invalid "+resultJson.getString("error"),
                        Toast.LENGTH_LONG).show();
            }




            Log.i("rajat", email + " " + userId + " " + token + " " + type + " " + error);
//            Tools.showAlertDialog(email + " " + userId + " " + token + " " + type + " " + error, con);
//            if(type.equals("1")){
//                VolleyClick.findProductsClick(userId,con);
//            }
//            else{
//                VolleyClick.findDiscountsClick(userId,con);
//            }


        } catch (Exception e) {
            Log.i("rajat", "Exception: LoginActivity: " + e.getLocalizedMessage());
        }
    }

    //DeleteProductApiJsonParser
    public static void DeleteProductApiJsonParser(String JsonStringResult,final String userId,final Context con) {
        try {


            String message = "";
            //create json object from response string
            JSONObject resultJson = new JSONObject(JsonStringResult);
            if (resultJson.has("message")) {

                message = resultJson.getString("message");
                if(message.equals("product removed")){
                    Log.i("rajat",  message);
                    VolleyClick.findProductsClick(userId, con);
                    //VolleyClick.fin
                }else if(message.equals("error")){
                    Toast.makeText(con, message +" occured", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(con, message, Toast.LENGTH_SHORT).show();
                }
            }
            Log.i("rajat",  message);
//            Tools.showAlertDialog(discount + " " + quantity + " " + price + " " + userId + " " + description + " " + productId + " " + message, con);

        } catch (Exception e) {
            e.printStackTrace();
            Log.i("rajat", "Exception: LoginActivity: " + e.getLocalizedMessage());
        }
    }
    public static void CreateProductApiJsonParser(String JsonStringResult,final Context con,final File file) {
        try {

            JSONObject product;

            String created_at = "";
            String updated_at = "";
            String userId = "";
            String userEmail="";
            int discount = 0;
            String description = "";
            int quantity = 0;
            int price = 0;
            String productId = "";
            String message = "";
            //create json object from response string
            JSONObject resultJson = new JSONObject(JsonStringResult);
            if (resultJson.has("newProduct")) {
                product = resultJson.getJSONObject("newProduct");
                discount = product.getInt("discount");
                quantity = product.getInt("quantity");
                price = product.getInt("price");
                userId = product.getString("userId");
                if(product.has("userEmail")){
                    userEmail = product.getString("userEmail");
                }
                description = product.getString("description");
                productId = product.getString("_id");
                created_at = product.getString("created_at");
                updated_at = product.getString("updated_at");
                message = resultJson.getString("message");
            }
            Log.i("rajat", discount + " " + quantity + " " + price + " " + userId + " " + description + " " + productId + " " + message);
//            Tools.showAlertDialog(discount + " " + quantity + " " + price + " " + userId + " " + description + " " + productId + " " + message, con);
            //uploadFile("ImageUpload", "http://192.168.43.200:3000/api/profile", file, "multipart", pprams, mlistener, err, listener, con);

            uploadImage(con,file,productId,userId);

//            byte[] multipartBody = getFileDataFromDrawable(con, R.mipmap.ic_launcher);
//            FileOutputStream fos = new FileOutputStream(file.getPath());
//            fos.write(multipartBody);
//            fos.close();
//            File f= new File(file.getPath());
//            new UploadImage(f,con).execute();

//           VolleyClick.findProductsClick(userId, con);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("rajat", "Exception: LoginActivity: " + e.getLocalizedMessage());
        }
    }

    public static void FindProductsApiJsonParser(String JsonStringResult, Context con) {
        try {

            JSONArray products;
            JSONObject product;
            String created_at = "";
            String updated_at = "";
            String userId = "";
            String userEmail = "";
            int discount = 0;
            String description = "";
            int quantity = 0;
            int price = 0;
            String productId = "";
            String message = "";
            String photoUrl="";
//            ArrayList<String> photoUrl= new ArrayList<String>();
            //create json object from response string
            ArrayList<Product> productObjList = new ArrayList<Product>();
            JSONObject resultJson = new JSONObject(JsonStringResult);
            if (resultJson.has("products")) {
                products = resultJson.getJSONArray("products");
                for (int i = 0; i < products.length(); i++) {
                    product = products.getJSONObject(i);
                    discount = product.getInt("discount");
                    quantity = product.getInt("quantity");
                    if(product.has("price")){
                    price = product.getInt("price");}
                    if(product.has("userEmail")){
                        userEmail = product.getString("userEmail");
                    }
                    if(product.has("photoUrl")){
                        //photoUrl.add(product.getString("photoUrl"));
                        photoUrl=product.getString("photoUrl");
                    }
                    userId = product.getString("userId");
                    description = product.getString("description");
                    productId = product.getString("_id");
                    created_at = product.getString("created_at");
                    updated_at = product.getString("updated_at");
                    productObjList.add(new Product(created_at, updated_at, userId, discount, description, quantity, price, productId,userEmail,photoUrl));
                }
                // message=resultJson.getString("message");
            }
            Log.i("rajat", discount + " " + quantity + " " + price + " " + userId + " " + description + " " + productId + " size: " + productObjList.size() + " " + message);
////            Tools.showAlertDialog(discount + " " + quantity + " " + price + " " + userId + " " + description + " " + productId + " " + message, con);
            Intent intent=new Intent(con,ProductsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("data", (String) new Gson().toJson(productObjList));
            //intent.putExtra("photoUrl",(String) new Gson().toJson(photoUrl));
            con.startActivity(intent);
        } catch (Exception e) {
            Log.i("rajat", "Exception: LoginActivity: " + e.getLocalizedMessage());
        }
    }

    public static void FindDiscountsApiJsonParser(String JsonStringResult, Context con) {
        ArrayList<Product> productObjList;
        try {
            HashMap<String,ArrayList<Product>> shops=new HashMap<String ,ArrayList<Product>>();
            JSONArray products=new JSONArray();
            JSONObject product;
            String created_at = "";
            String updated_at = "";
            String userId = "";
            String userEmail = "";
            int discount = 0;
            String description = "";
            int quantity = 0;
            int price = 0;
            String productId = "";
            String message = "";
            String photoUrl="";
//            ArrayList<String> photoUrls = new ArrayList<String>();
//            HashMap<String,ArrayList<String>> photos=new HashMap<String ,ArrayList<String>>();
            Boolean deliveryAvailble=false;
            ArrayList<Boolean>delivery = new ArrayList<Boolean>();
            HashMap<String,ArrayList<Boolean>> deliveryAvail=new HashMap<String ,ArrayList<Boolean>>();
            //create json object from response string
            productObjList = new ArrayList<Product>();
            //JSONObject resultJson = new JSONObject(JsonStringResult);
            products= new JSONArray(JsonStringResult);
            SharedPreferences pref = con.getSharedPreferences("MyPrefs", con.MODE_PRIVATE);
            if (products.length()!=0) {
                //resultJson.toJSONArray(products);
                 for (int i = 0; i < products.length(); i++) {
                    product = products.getJSONObject(i);
                     price =product.getInt("price");
                    if(product.has("discount"))
                    discount = product.getInt("discount");
                    quantity = product.getInt("quantity");
                    if(product.has("userId"))
                    userId = product.getString("userId");
                     if(product.has("userEmail"))
                         userEmail = product.getString("userEmail");
                    description = product.getString("description");
                    productId = product.getString("_id");
                    if(product.has("created_at"))
                    created_at = product.getString("created_at");
                    if(product.has("updated_at"))
                    updated_at = product.getString("updated_at");
                    if(product.has("photoUrl")){
                        photoUrl=product.getString("photoUrl");
//                        photoUrls.add(product.getString("photoUrl"));
                    }
                     if(product.has("deliverable")){
                         deliveryAvailble=product.getBoolean("deliverable");
                         delivery.add(product.getBoolean("deliverable"));
                     }

                    if(shops.containsKey(userEmail)){
                        shops.get(userEmail).add(new Product(created_at, updated_at, userId, discount, description, quantity, price, productId, userEmail,photoUrl));
//                        photos.get(userEmail).add(photoUrl);
                        deliveryAvail.get(userEmail).add(deliveryAvailble);
                    }
                     else {
                        shops.put(userEmail,new ArrayList<Product>());
                        shops.get(userEmail).add(new Product(created_at, updated_at, userId, discount, description, quantity, price, productId, userEmail,photoUrl));
//                        photos.put(userEmail, new ArrayList<String>());
//                        photos.get(userEmail).add(photoUrl);
                        deliveryAvail.put(userEmail, new ArrayList<Boolean>());
                        deliveryAvail.get(userEmail).add(deliveryAvailble);
                    }


                }
                // message=resultJson.getString("message");
            }
            Log.i("rajat", discount + " " + quantity + " " + price + " " + userId + " " + description + " " + productId + " size: " + productObjList.size() + " " + message);
//            Tools.showAlertDialog(discount + " " + quantity + " " + price + " " + userId + " " + description + " " + productId + " " + message, con);
            Log.d("check", new Gson().toJson(shops));
            Intent intent=new Intent(con,ShopsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Log.d("check", new Gson().toJson(shops));
            intent.putExtra("data", new Gson().toJson(shops));
//            Log.i("rajat", new Gson().toJson(photos) + "sixe" + photoUrls.size());
//            intent.putExtra("photoUrl", new Gson().toJson(photos));
            Log.i("rajat", new Gson().toJson(deliveryAvail) + "deliv:" + delivery.size());
            intent.putExtra("deliverable",new Gson().toJson(deliveryAvail));
            con.startActivity(intent);
        } catch (Exception e) {
            Log.i("rajat", "Exception: LoginActivity: " + e.getLocalizedMessage());
            productObjList=new ArrayList<Product>();
            Intent intent=new Intent(con,Discounts.class);
            intent.putExtra("data", (String) new Gson().toJson(productObjList));
            con.startActivity(intent);
        }
    }
    //post_msgApiJsonParser
    public static void UpdateProductPriceApiJsonParser(String JsonStringResult, Context con) {
        try {

            JSONObject product;

            String created_at = "";
            String updated_at = "";
            String userId = "";
            int discount = 0;
            String description = "";
            int quantity = 0;
            int price = 0;
            String productId = "";
            String message = "";
            //create json object from response string
            JSONObject resultJson = new JSONObject(JsonStringResult);
            if (resultJson.has("newProduct")) {
                product = resultJson.getJSONObject("newProduct");
                discount = product.getInt("discount");
                quantity = product.getInt("quantity");
                price = product.getInt("price");
                userId = product.getString("userId");
                description = product.getString("description");
                productId = product.getString("_id");
                created_at = product.getString("created_at");
                updated_at = product.getString("updated_at");
                message = resultJson.getString("message");
            }
            Log.i("rajat", discount + " " + quantity + " " + price + " " + userId + " " + description + " " + productId + " " + message);
//            Tools.showAlertDialog(discount + " " + quantity + " " + price + " " + userId + " " + description + " " + productId + " " + message, con);
        } catch (Exception e) {
            Log.i("rajat", "Exception: LoginActivity: " + e.getLocalizedMessage());
        }
    }
    public static void UpdateProductNameApiJsonParser(String JsonStringResult, Context con) {
        try {

            JSONObject product;

            String created_at = "";
            String updated_at = "";
            String userId = "";
            int discount = 0;
            String description = "";
            int quantity = 0;
            int price = 0;
            String productId = "";
            String message = "";
            //create json object from response string
            JSONObject resultJson = new JSONObject(JsonStringResult);
            if (resultJson.has("newProduct")) {
                product = resultJson.getJSONObject("newProduct");
                discount = product.getInt("discount");
                quantity = product.getInt("quantity");
                price = product.getInt("price");
                userId = product.getString("userId");
                description = product.getString("description");
                productId = product.getString("_id");
                created_at = product.getString("created_at");
                updated_at = product.getString("updated_at");
                message = resultJson.getString("message");
            }
            Log.i("rajat", discount + " " + quantity + " " + price + " " + userId + " " + description + " " + productId + " " + message);
//            Tools.showAlertDialog(discount + " " + quantity + " " + price + " " + userId + " " + description + " " + productId + " " + message, con);
        } catch (Exception e) {
            Log.i("rajat", "Exception: LoginActivity: " + e.getLocalizedMessage());
        }
    }
    public static void UpdateProductQuantityApiJsonParser(String JsonStringResult, Context con) {
        try {

            JSONObject product;

            String created_at = "";
            String updated_at = "";
            String userId = "";
            int discount = 0;
            String description = "";
            int quantity = 0;
            int price = 0;
            String productId = "";
            String message = "";
            //create json object from response string
            JSONObject resultJson = new JSONObject(JsonStringResult);
            if (resultJson.has("newProduct")) {
                product = resultJson.getJSONObject("newProduct");
                discount = product.getInt("discount");
                quantity = product.getInt("quantity");
                price = product.getInt("price");
                userId = product.getString("userId");
                description = product.getString("description");
                productId = product.getString("_id");
                created_at = product.getString("created_at");
                updated_at = product.getString("updated_at");
                message = resultJson.getString("message");
            }
            Log.i("rajat", discount + " " + quantity + " " + price + " " + userId + " " + description + " " + productId + " " + message);
//            Tools.showAlertDialog(discount + " " + quantity + " " + price + " " + userId + " " + description + " " + productId + " " + message, con);
        } catch (Exception e) {
            Log.i("rajat", "Exception: LoginActivity: " + e.getLocalizedMessage());
        }
    }
    public static void ChangeProductDiscountApiJsonParser(String JsonStringResult, Context con) {
        try {

            JSONObject product;

            String created_at = "";
            String updated_at = "";
            String userId = "";
            int discount = 0;
            String description = "";
            int quantity = 0;
            int price = 0;
            String productId = "";
            String message = "";
            //create json object from response string
            JSONObject resultJson = new JSONObject(JsonStringResult);
            if (resultJson.has("newProduct")) {
                product = resultJson.getJSONObject("newProduct");
                discount = product.getInt("discount");
                quantity = product.getInt("quantity");
                price = product.getInt("price");
                userId = product.getString("userId");
                description = product.getString("description");
                productId = product.getString("_id");
                created_at = product.getString("created_at");
                updated_at = product.getString("updated_at");
                message = resultJson.getString("message");
            }
            Log.i("rajat", discount + " " + quantity + " " + price + " " + userId + " " + description + " " + productId + " " + message);
//            Tools.showAlertDialog(discount + " " + quantity + " " + price + " " + userId + " " + description + " " + productId + " " + message, con);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("rajat", "Exception: LoginActivity: " + e.getLocalizedMessage());
        }
    }

    public static void PlaceOrderApiJsonParser(JSONObject JsonStringResult, Context con) {
        try {

            JSONObject order;
            String orderId="";
            String customerEmail="";
            String created_at = "";
            String updated_at = "";
            String shopkeeperId = "";
            String currentState = "";
            String customerId = "";
            JSONArray items = new JSONArray();
            JSONObject item;
            String itemId="";
            JSONObject productObj=new JSONObject();
            Product product;
            String pro_created_at = "";
            String pro_updated_at = "";
            String userId = "";
            String userEmail="";
            int discount = 0;
            String description = "";
            int quantity = 0;
            int price = 0;
            String productId = "";
            //String productId = "";
            int orderQuantity = 0;
            String message = "";
            Item itemObj;
            String photoUrl="";
            ArrayList<Item> itemObjList = new ArrayList<Item>();
            //create json object from response string
            JSONObject resultJson = JsonStringResult;
            if (resultJson.has("newOrder")) {
                order = resultJson.getJSONObject("newOrder");
                if(order.has("customerEmail"))
                customerEmail=order.getString("customerEmail");
                orderId=order.getString("_id");
                if(order.has("shopKeeperId"))
                shopkeeperId = order.getString("shopKeeperId");
                customerId = order.getString("customerId");
                created_at = order.getString("created_at");
                updated_at = order.getString("updated_at");
                //Log.i("rajat",resultJson.get("items").getClass()+" "+resultJson.get(""));
                items = order.getJSONArray("items");
                for (int i = 0; i < items.length(); i++) {
                    item = items.getJSONObject(i);
                    if(item.has("_id"))
                    itemId = item.getString("_id");
                    orderQuantity = item.getInt("orderQuantity");
                    productObj = item.getJSONObject("product");
                    discount = productObj.getInt("discount");
                    quantity = productObj.getInt("quantity");
                    price = productObj.getInt("price");
                    userId = productObj.getString("userId");
                    if(productObj.has("userEmail")){
                        userEmail = productObj.getString("userEmail");
                    }
                    if(productObj.has("photoUrl")){
                        photoUrl = productObj.getString("photoUrl");
                    }
                    description = productObj.getString("description");
                    productId = productObj.getString("_id");
                    pro_created_at = productObj.getString("created_at");
                    pro_updated_at = productObj.getString("updated_at");
                    product =new Product(pro_created_at,pro_updated_at,userId,discount,description,quantity,price,productId,userEmail,photoUrl);
                    itemObj = new Item(itemId, product, orderQuantity);
                    itemObjList.add(itemObj);
                }
                message = resultJson.getString("message");
            }
            Log.i("rajat",orderId+" "+ shopkeeperId + " " + customerId + " " + itemObjList.size() + " " + productId + " " + message);
//            Tools.showAlertDialog(shopkeeperId + " " + customerId + " " + itemObjList.size() + " " + productId + " " + message, con);
            SharedPreferences pref = con.getSharedPreferences("MyPrefs", con.MODE_PRIVATE);
            VolleyClick.findOrdersClick(pref.getString("userId", " "), pref.getString("type", ""), con);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("rajat", "Exception: LoginActivity: " + e.getLocalizedMessage());
        }
    }

    public static void ChangeOrderStateApiJsonParser(String JsonStringResult, Context con) {
        try {

            JSONObject order;
            String orderId="";
            String created_at = "";
            String updated_at = "";
            String shopkeeperId = "";
            String currentState = "";
            String customerId = "";
            String customerEmail="";
            JSONArray items = new JSONArray();
            JSONObject item;
            String itemId="";
            JSONObject productObj=new JSONObject();
            Product product;
            String pro_created_at = "";
            String pro_updated_at = "";
            String userId = "";
            String userEmail="";
            int discount = 0;
            String description = "";
            int quantity = 0;
            int price = 0;
            String productId = "";
            //String productId = "";
            int orderQuantity = 0;
            String message = "";
            Item itemObj;
            String photoUrl="";
            ArrayList<Item> itemObjList = new ArrayList<Item>();
            //create json object from response string
            JSONObject resultJson = new JSONObject(JsonStringResult);
            if (resultJson.has("modifiedOrder")) {
                order = resultJson.getJSONObject("modifiedOrder");
                orderId=order.getString("_id");
                shopkeeperId = order.getString("shopKeeperId");
                if(order.has("customerId"))
                customerId = order.getString("customerId");
                customerEmail = order.getString("customerEmail");
                created_at = order.getString("created_at");
                updated_at = order.getString("updated_at");
                //Log.i("rajat",resultJson.get("items").getClass()+" "+resultJson.get(""));
                items = order.getJSONArray("items");
                for (int i = 0; i < items.length(); i++) {
                    item = items.getJSONObject(i);
                    if(item.has("_id"))
                    itemId = item.getString("_id");
                    orderQuantity = item.getInt("orderQuantity");
                    productObj = item.getJSONObject("product");
                    discount = productObj.getInt("discount");
                    quantity = productObj.getInt("quantity");
                    price = productObj.getInt("price");
                    userId = productObj.getString("userId");
                    if(productObj.has("userEmail")){
                        userEmail = productObj.getString("userEmail");
                    }
                    if(productObj.has("photoUrl")){
                        photoUrl = productObj.getString("photoUrl");
                    }
                    description = productObj.getString("description");
                    productId = productObj.getString("_id");
                    pro_created_at = productObj.getString("created_at");
                    pro_updated_at = productObj.getString("updated_at");
                    product =new Product(pro_created_at,pro_updated_at,userId,discount,description,quantity,price,productId,userEmail,photoUrl);
                    itemObj = new Item(itemId, product, orderQuantity);
                    //itemObj = new Item(productId, quantity);
                    itemObjList.add(itemObj);
                }
                message = resultJson.getString("message");
            }
            Log.i("rajat", orderId + " " + shopkeeperId + " " + customerId + " " + itemObjList.size() + " " + productId + " " + message);
//            Tools.showAlertDialog(shopkeeperId + " " + customerId + " " + itemObjList.size() + " " + productId + " " + message, con);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("rajat", "Exception: LoginActivity: " + e.getLocalizedMessage());
        }
    }

    public static void FindOrdersApiJsonParser(String JsonStringResult, Context con) {
        try {
            JSONArray orders=new JSONArray();
            Order orderObject;
            ArrayList<Order> orderObjList= new ArrayList<Order>();
            JSONObject order;
            String orderId="";
            String customerEmail="";
            String created_at = "";
            String updated_at = "";
            String shopkeeperId = "";
            String currentState = "";
            String customerId = "";
            JSONArray items = new JSONArray();
            JSONObject item;
            String itemId="";
            JSONObject productObj=new JSONObject();
            Product product;
            String pro_created_at = "";
            String pro_updated_at = "";
            String userId = "";
            String userEmail="";
            int discount = 0;
            String description = "";
            int quantity = 0;
            int price = 0;
            String productId = "";
            //String productId = "";
            int orderQuantity = 0;
            String message = "";
            String phoneNumber= "";
            String address="";
            JSONArray numbers=new JSONArray();
            JSONArray addresses=new JSONArray();
            Item itemObj;
            String photoUrl="";
            //
            ArrayList<Item> itemObjList = new ArrayList<Item>();
            ArrayList<String> Addressess= new ArrayList<String>();
            ArrayList<String> Numbers= new ArrayList<String>();
            //create json object from response string
            JSONObject resultJson = new JSONObject(JsonStringResult);
            if (resultJson.has("Orders")) {
                orders =resultJson.getJSONArray("Orders");
                for(int x=0;x<orders.length();x++){
                    order = orders.getJSONObject(x);
                    orderId= order.getString("_id");
                    if(order.has("customerEmail"))
                    customerEmail=order.getString("customerEmail");
                    shopkeeperId = order.getString("shopKeeperId");
                    if(order.has("customerId"))
                    customerId = order.getString("customerId");
                    created_at = order.getString("created_at");
                    updated_at = order.getString("updated_at");
                    currentState = order.getString("currentState");
                    //Log.i("rajat",resultJson.get("items").getClass()+" "+resultJson.get(""));
                    items = order.getJSONArray("items");
                    itemObjList = new ArrayList<Item>();
                    for (int i = 0; i < items.length(); i++) {

                        item = items.getJSONObject(i);
                        if(item.has("_id"))
                        itemId = item.getString("_id");
                        if(item.has("orderQuantity"))
                        orderQuantity = item.getInt("orderQuantity");
                        productObj = item.getJSONObject("product");
                        discount = productObj.getInt("discount");
                        quantity = productObj.getInt("quantity");
                        price = productObj.getInt("price");
                        userId = productObj.getString("userId");
                        if(productObj.has("userEmail")){
                            userEmail = productObj.getString("userEmail");
                        }
                        if(productObj.has("photoUrl")){
                            photoUrl = productObj.getString("photoUrl");
                        }
                        description = productObj.getString("description");
                        productId = productObj.getString("_id");
                        pro_created_at = productObj.getString("created_at");
                        pro_updated_at = productObj.getString("updated_at");
                        product =new Product(pro_created_at,pro_updated_at,userId,discount,description,quantity,price,productId,userEmail,photoUrl);
                        itemObj = new Item(itemId, product, orderQuantity);
                        itemObjList.add(itemObj);

                    }
                    orderObject=new Order(shopkeeperId,currentState,customerId,customerEmail,orderId,created_at,updated_at,itemObjList);
                    orderObjList.add(orderObject);
                }

                message = resultJson.getString("message");
            }
            if(resultJson.has("UserNumber")){
                numbers=resultJson.getJSONArray("UserNumber");
                for (int i=0; i<numbers.length();i++){
                    phoneNumber = numbers.getString(i);
                    Numbers.add(phoneNumber);
                    Log.i("rajat", phoneNumber );
                }
                //phoneNumber = resultJson.getString("UserNumber");
            }
            if(resultJson.has("UserAddress")){
                addresses = resultJson.getJSONArray("UserAddress");
                //String[] addres = new String[resultJson.ge];
                for (int i=0; i<addresses.length();i++){
                    address = addresses.getString(i);
                    Addressess.add(address);
                    Log.i("rajat", address);
                }


               // address = resultJson.getString("UserAddress");
            }
            Log.i("rajat", shopkeeperId + " " + customerId + " " + orderObjList.size() + " " + productId + " " + message);
//            Tools.showAlertDialog(shopkeeperId + " " + customerId + " " + orderObjList.size() + " " + productId + " " + message, con);
            Intent intent=new Intent(con, OrdersActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("data",new Gson().toJson(orderObjList));
           intent.putExtra("userPhone",new Gson().toJson(Numbers));
           intent.putExtra("userAddress",new Gson().toJson(Addressess));
            con.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("rajat", "Exception: LoginActivity: " + e.getLocalizedMessage());
        }
    }
    public static void RegisterApiJsonParser(String JsonStringResult, Context con){
        try {

            JSONObject device;
            String token="";
            String deviceType="";
            String email="";
            String  deviceId="";
            ArrayList<String> subscriptions=new ArrayList<String>();
            JSONArray subscriptionArr = new JSONArray();
            String message = "";
            //create json object from response string
            JSONObject resultJson = new JSONObject(JsonStringResult);
            if (resultJson.has("Device")) {
                device = resultJson.getJSONObject("Device");
                token = device.getString("token");
                email= device.getString("email");
                deviceId = device.getString("_id");
                deviceType = device.getString("deviceType");
                if(device.has("subscribedIDs")){
                    //subscriptions;
                    subscriptionArr = device.getJSONArray("subscribedIDs");
                    for(int i =0 ;i<subscriptionArr.length();i++){
                        subscriptions.add(subscriptionArr.getString(i));
                    }
                }
                SharedPreferences shares= con.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = shares.edit();
                editor.putString("deviceId", deviceId);
                editor.commit();
            }
            if(resultJson.has("message")){
                message = resultJson.getString("message");
            }
            Log.i("rajat", message + token + " " + email + " " + deviceId + " " + deviceType + " ");
            //Tools.showAlertDialog(message + token, con);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("rajat", "Exception: LoginActivity: " + e.getLocalizedMessage());
        }
    }

    public static void SetSubscriptionApiJsonParser(String JsonStringResult, Context con){
        try {

            JSONObject device;
            String token="";
            String deviceType="";
            String email="";
            String  deviceId="";
            ArrayList<String> subscriptions=new ArrayList<String>();
            JSONArray subscriptionArr = new JSONArray();
            String message = "";
            //create json object from response string
            JSONObject resultJson = new JSONObject(JsonStringResult);
            if (resultJson.has("updatedDevice")) {
                device = resultJson.getJSONObject("updatedDevice");
                token = device.getString("token");
                email= device.getString("email");
                deviceId = device.getString("_id");
                deviceType = device.getString("deviceType");
                if(device.has("subscribedIDs")){
                    //subscriptions;
                    subscriptionArr = device.getJSONArray("subscribedIDs");
                    for(int i =0 ;i<subscriptionArr.length();i++){
                        subscriptions.add(subscriptionArr.getString(i));
                    }
                }

            }
            if(resultJson.has("message")){
                message = resultJson.getString("message");
            }
            Log.i("rajat", token + " " + email + " " + deviceId + " " + deviceType + " " + message);
            //Tools.showAlertDialog( message+token , con);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("rajat", "Exception: LoginActivity: " + e.getLocalizedMessage());
        }
    }

    private static final String twoHyphens = "--";
    private static final String lineEnd = "\r\n";
    private static final String boundary = "apiclient-" + System.currentTimeMillis();
    private static final String mimeType = "multipart/form-data;boundary=" + boundary;
    private static byte[] multipartBody;
    private static void uploadImage(final Context context,File file,String productId,final String userId){
        Log.i("rajat","uploading image");
        byte[] fileData1;//=getFileDataFromDrawable(context, R.mipmap.ic_launcher); ;
        String filePath = file.getPath();
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        Bitmap bit2 = Bitmap.createScaledBitmap(bitmap, 400, 400, true);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bit2.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
        Log.i("rajat","uploading image");
        fileData1 = byteArrayOutputStream.toByteArray();
        //ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        try {
//            //FileUtils.readFileToByteArray(file);
//        } catch (IOException e) {
//            fileData1 = getFileDataFromDrawable(context, R.mipmap.ic_launcher);
//            e.printStackTrace();
//        }
        //fileData1 = file.
        //byte[] fileData2 = getFileDataFromDrawable(context, R.mipmap.ic_launcher);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        try {
            // the first file
            buildPart(dos, fileData1, "ic_action_android.png");
            // the second file
            //buildPart(dos, fileData2, "ic_action_book.png");
            // send multipart form data necesssary after file data
            buildTextPart(dos,"productId", productId);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // pass to multipart body
            multipartBody = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String url = VolleyClick.IP+"/api/profile";
        MultipartRequest multipartRequest = new MultipartRequest(url, null, mimeType, multipartBody, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                Toast.makeText(context, "Upload successfully!", Toast.LENGTH_SHORT).show();
                VolleyClick.findProductsClick(userId, context);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Upload failed!\r\n" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        VolleySingleton.getInstance(context).addToRequestQueue(multipartRequest);
    }
    private static void buildPart(DataOutputStream dataOutputStream, byte[] fileData, String fileName) throws IOException {
        dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
        dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"image\"; filename=\""
                + fileName + "\"" + lineEnd);
        dataOutputStream.writeBytes(lineEnd);

        ByteArrayInputStream fileInputStream = new ByteArrayInputStream(fileData);
        int bytesAvailable = fileInputStream.available();

        int maxBufferSize = 1024 * 1024;
        int bufferSize = Math.min(bytesAvailable, maxBufferSize);
        byte[] buffer = new byte[bufferSize];

        // read file and write it into form...
        int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

        while (bytesRead > 0) {
            dataOutputStream.write(buffer, 0, bufferSize);
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        }

        dataOutputStream.writeBytes(lineEnd);
    }

    private static byte[] getFileDataFromDrawable(Context context, int id) {
        Drawable drawable = ContextCompat.getDrawable(context, id);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    private static void buildTextPart(DataOutputStream dataOutputStream, String parameterName, String parameterValue) throws IOException {
        dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
        dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + parameterName + "\"" + lineEnd);
        dataOutputStream.writeBytes("Content-Type: text/plain; charset=UTF-8" + lineEnd);
        dataOutputStream.writeBytes(lineEnd);
        dataOutputStream.writeBytes(parameterValue + lineEnd);
    }



    public static void setSubcriptionsApiJsonParser(JSONObject JsonStringResult, Context con) {
        try {
            //boolean status;
            String email = "";
            String userId = "";
            String error = "";
            String token = "";
            String type = "";
            //create json object from response string
            JSONObject resultJson = JsonStringResult;
            if(resultJson.has("message")){
                Toast.makeText(con, resultJson.getString("message"),
                        Toast.LENGTH_LONG).show();
//                return;
            }


            VolleyClick.findDiscountsClick(userId,con);


        } catch (Exception e) {
            Log.i("rajat", "Exception: LoginActivity: " + e.getLocalizedMessage());
        }
    }

    public static void getSubcriptionsApiJsonParser(String JsonStringResult, Context con) {
        try {
            //boolean status;
            String email = "";
            String userId = "";
            String error = "";
            String token = "";
            String type = "";
            JSONArray userObj= new JSONArray();
            JSONObject userObject = new JSONObject();
            ArrayList<String> subscription_list = new ArrayList<String>();
            JSONArray subscrip_array = new JSONArray();
            ArrayList<Shop> shops = new ArrayList<Shop>();
            //create json object from response string
            JSONObject resultJson = new JSONObject(JsonStringResult);
            if(resultJson.has("message")){
                if(resultJson.getString("message").equals("not found")){
                    Toast.makeText(con, "Invalid "+resultJson.getString("message"),
                            Toast.LENGTH_LONG).show();

                }else if(resultJson.getString("message").equals("found")){
                    if(resultJson.has("Users")){
                        userObj=resultJson.getJSONArray("Users");
                        for(int i=0;i<userObj.length();i++){
                            userObject = userObj.getJSONObject(i);
                            email = userObject.getString("email");
                            userId=userObject.getString("_id");
                            shops.add(new Shop(userId,email));
                        }

                    }
                    if(resultJson.has("SubscribedIds")){
//                        userObj=resultJson.getJSONObject("Users")
                        subscrip_array=resultJson.getJSONArray("SubscribedIds");
                        for(int j=0 ; j<subscrip_array.length();j++){
                            subscription_list.add(subscrip_array.getString(j));
                        }
                    }
                }

                //return;
            }
            //Log.i(userObj.toString()+""+subscrip_array.toString()+"");

            Intent intent=new Intent(con, PermissionForm.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("shops",new Gson().toJson(shops));
            intent.putExtra("subscriptions",new Gson().toJson(subscription_list));
            con.startActivity(intent);

        } catch (Exception e) {
            Log.i("rajat", "Exception: LoginActivity: " + e.getLocalizedMessage());
        }
    }



    public static void FindOffersApiJsonParser(String JsonStringResult, Context con) {
        try {

            String description = "";

            JSONArray offerObjArr= new JSONArray();
            JSONObject offerObject = new JSONObject();
            ArrayList<String> offer_list = new ArrayList<String>();

            JSONObject resultJson = new JSONObject(JsonStringResult);
            if(resultJson.has("Discounts")){
                offerObjArr=resultJson.getJSONArray("Discounts");
                for(int i=0;i<offerObjArr.length();i++){
                    offerObject=offerObjArr.getJSONObject(i);
                    if(offerObject.has("discountDescription")){
                        description=offerObject.getString("discountDescription");
                        offer_list.add(description);
                    }
                }
            }
            Intent intent=new Intent(con, OffersActivity.class);//////////////////
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("offers",new Gson().toJson(offer_list));
            con.startActivity(intent);

        } catch (Exception e) {
            Log.i("rajat", "Exception: LoginActivity: " + e.getLocalizedMessage());
        }
    }
}
