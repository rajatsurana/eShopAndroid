package com.rajat.eshop.Volley;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import com.rajat.eshop.AddProductsActivity;
import com.rajat.eshop.LoginActivity;
import com.rajat.eshop.PermissionForm;
import com.rajat.eshop.Tools.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CallVolley {
        //dialog box appears showing progress
        private static ProgressDialog pDialog;
        //TimeOut and maximum number of tries
        private static void setCustomRetryPolicy(StringRequest jsonObjReq) {
                Log.i("rajat", "setCustomRetryPolicy");
                jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }
        private static void setCustomRetryPolicy(JsonObjectRequest jsonObjReq) {
                Log.i("rajat", "setCustomRetryPolicy");
                jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }
        public static void signUpCall(String url, final Context context, final String email,final String password,final String address, final String phoneNum,final String type,final int Number)
        {
                pDialog=  Tools.showProgressBar(context);

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                {
                        // if a reponse is recieved after sending request
                        @Override
                        public void onResponse(String response)
                        {
                                try
                                {
                                        Log.i("rajat", " onResponseActive " + response);
                                        pDialog.dismiss();
                                        JSONParser.SignUpApiJsonParser(response, context);


                                }
                                catch (Exception localException)
                                {
                                        Log.i("rajat"," onResponseException "+localException.getMessage());
                                        localException.printStackTrace();
                                }
                        }
                }
                        , new Response.ErrorListener()
                {
                        //if error occurs
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                                Log.i("rajat", "onErrorResponse" + error.toString());
                                error.printStackTrace();
                                pDialog.dismiss();
                        }
                }){
                        @Override
                        public String getBodyContentType() {
                                return "application/x-www-form-urlencoded; charset=UTF-8";
                        }
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("email",email);
                                params.put("password",password);
                                params.put("userType",type);
                                params.put("address",address);
                                params.put("phoneNumber",phoneNum);
                                return params;
                        }
                };

                //how many times to try and for how much duration
                setCustomRetryPolicy(request);
                //get instance of volleysingleton and add reuest to the queue
                VolleySingleton.getInstance(context).addToRequestQueue(request);
        }
        public static void loginCall(String url, final Context context, final String email,final String password,final int Number)
        {
                pDialog=  Tools.showProgressBar(context);

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                {
                        // if a reponse is recieved after sending request
                        @Override
                        public void onResponse(String response)
                        {
                                try
                                {
                                        pDialog.dismiss();
                                        Log.i("rajat", " onResponseActive " + response);
                                        JSONParser.LoginApiJsonParser(response, context);


                                }
                                catch (Exception localException)
                                {
                                        Log.i("rajat"," onResponseException "+localException.getMessage());
                                        localException.printStackTrace();
                                }
                        }
                }
                        , new Response.ErrorListener()
                {
                        //if error occurs
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                                pDialog.dismiss();
                                Log.i("rajat", "onErrorResponse" + error.toString());
                                if(error.toString().equals("com.android.volley.AuthFailureError")){
                                        Toast.makeText(context,"Authentication failed",Toast.LENGTH_SHORT).show();
                                }else{
                                        Toast.makeText(context,"Connection Error",Toast.LENGTH_SHORT).show();
                                }


                        }
                }){
                        @Override
                        public String getBodyContentType() {
                                return "application/x-www-form-urlencoded; charset=UTF-8";
                        }
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("email",email);
                                params.put("password",password);
                                return params;
                        }
                };

                //how many times to try and for how much duration
                setCustomRetryPolicy(request);
                //get instance of volleysingleton and add reuest to the queue
                VolleySingleton.getInstance(context).addToRequestQueue(request);
        }

        public static void logoutCall(String url, final Context context, final String deviceId,final int Number)
        {
                pDialog=  Tools.showProgressBar(context);

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                {
                        // if a reponse is recieved after sending request
                        @Override
                        public void onResponse(String response)
                        {
                                try
                                {
                                        pDialog.dismiss();
                                        Log.i("rajat", " onResponseActive " + response);
                                        //JSONParser.LogoutApiJsonParser(response, context);
                                        context.getSharedPreferences("MyPrefs", 0).edit().clear().commit();
                                        Intent intent = new Intent(context, LoginActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        context.startActivity(intent);

                                }
                                catch (Exception localException)
                                {
                                        Log.i("rajat"," onResponseException "+localException.getMessage());
                                        localException.printStackTrace();
                                }
                        }
                }
                        , new Response.ErrorListener()
                {
                        //if error occurs
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                                Log.i("rajat", "onErrorResponse" + error.toString());
                                pDialog.dismiss();
                        }
                }){
                        @Override
                        public String getBodyContentType() {
                                return "application/x-www-form-urlencoded; charset=UTF-8";
                        }
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("id",deviceId);
                                //params.put("userType",userType);
                                return params;
                        }
                };

                //how many times to try and for how much duration
                setCustomRetryPolicy(request);
                //get instance of volleysingleton and add reuest to the queue
                VolleySingleton.getInstance(context).addToRequestQueue(request);
        }
        public static void createProductCall(String url, final Context context,final int price ,final int quantity,final String description,final String userId ,final String userEmail,final int discount,final Boolean deliveryAvailable,final int Number,final File file)
        {

                pDialog=  Tools.showProgressBar(context);

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                {
                        // if a reponse is recieved after sending request
                        @Override
                        public void onResponse(String response)
                        {
                                try
                                {
                                        pDialog.dismiss();
                                        Log.i("rajat", " onResponseActive " + response);
                                        JSONParser.CreateProductApiJsonParser(response, context, file);
//                                        JSONParser.FindProductsApiJsonParser(response, context);

                                }
                                catch (Exception localException)
                                {
                                        Log.i("rajat"," onResponseException "+localException.getMessage());
                                        localException.printStackTrace();
                                }
                        }
                }
                        , new Response.ErrorListener()
                {
                        //if error occurs
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                                Log.i("rajat", "onErrorResponse" + error.toString());
                                pDialog.dismiss();
                        }
                }){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                                //Log.i("size in getHeader: ",myHeaders.size()+"");
                                Map<String, String> mHeaders=new HashMap<String,String>();//myHeaders;
                                mHeaders.put("x-access-token", AddProductsActivity.pref.getString("token", ""));//MainActivity.sharedpreferences.getString("Set-Cookie",""));
                                return mHeaders;
                        }
                        @Override
                        public String getBodyContentType() {
                                return "application/x-www-form-urlencoded; charset=UTF-8";
                        }
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("price",price+"");
                                params.put("quantity",quantity+"");
                                params.put("description",description);
                                params.put("userId",userId);
                                params.put("userEmail",userEmail);
                                params.put("discount",""+discount);
                                params.put("deliverable",deliveryAvailable+"");

                                return params;
                        }
                };

                //how many times to try and for how much duration
                setCustomRetryPolicy(request);
                //get instance of volleysingleton and add reuest to the queue
                VolleySingleton.getInstance(context).addToRequestQueue(request);
        }
        public static void createDiscountCall(String url, final Context context,final String shopId,final String discountDesc,final int Number)
        {
                pDialog=  Tools.showProgressBar(context);

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                {
                        // if a reponse is recieved after sending request
                        @Override
                        public void onResponse(String response)
                        {
                                try
                                {
                                        pDialog.dismiss();
                                        Log.i("rajat", " onResponseActive " + response);
                                        //JSONParser.CreateDiscountApiJsonParser(response, context);


                                }
                                catch (Exception localException)
                                {
                                        Log.i("rajat"," onResponseException "+localException.getMessage());
                                        localException.printStackTrace();
                                }
                        }
                }
                        , new Response.ErrorListener()
                {
                        //if error occurs
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                                Log.i("rajat", "onErrorResponse" + error.toString());
                                pDialog.dismiss();
                        }
                }){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                                //Log.i("size in getHeader: ",myHeaders.size()+"");
                                Map<String, String> mHeaders=new HashMap<String,String>();//myHeaders;
                                mHeaders.put("x-access-token", context.getSharedPreferences("MyPrefs",Context.MODE_PRIVATE).getString("token", ""));//MainActivity.sharedpreferences.getString("Set-Cookie",""));
                                return mHeaders;
                        }
                        @Override
                        public String getBodyContentType() {
                                return "application/x-www-form-urlencoded; charset=UTF-8";
                        }
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("shopKeeperId", shopId);
                                params.put("discountDescription", discountDesc);
                                return params;
                        }
                };

                //how many times to try and for how much duration
                setCustomRetryPolicy(request);
                //get instance of volleysingleton and add reuest to the queue
                VolleySingleton.getInstance(context).addToRequestQueue(request);
        }
        public static void findProductsCall(String url, final Context context,final String userId,final int Number)
        {
                pDialog=  Tools.showProgressBar(context);

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                {
                        // if a reponse is recieved after sending request
                        @Override
                        public void onResponse(String response)
                        {
                                try
                                {
                                        Log.i("rajat", " onResponseActive " + response);
                                        pDialog.dismiss();
                                        JSONParser.FindProductsApiJsonParser(response, context);


                                }
                                catch (Exception localException)
                                {
                                        Log.i("rajat"," onResponseException "+localException.getMessage());
                                        localException.printStackTrace();
                                }
                        }
                }
                        , new Response.ErrorListener()
                {
                        //if error occurs
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                                Log.i("rajat", "onErrorResponse" + error.toString());
                                error.printStackTrace();
                                pDialog.dismiss();
                        }
                }){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                                //Log.i("size in getHeader: ",myHeaders.size()+"");
                                Map<String, String> mHeaders=new HashMap<String,String>();//myHeaders;
                                mHeaders.put("x-access-token", context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("token", ""));//MainActivity.sharedpreferences.getString("Set-Cookie",""));
                                return mHeaders;
                        }
                        @Override
                        public String getBodyContentType() {
                                return "application/x-www-form-urlencoded; charset=UTF-8";
                        }
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();

                                params.put("userId",userId);
                                return params;
                        }
                };

                //how many times to try and for how much duration
                setCustomRetryPolicy(request);
                //get instance of volleysingleton and add reuest to the queue
                VolleySingleton.getInstance(context).addToRequestQueue(request);
        }
        public static void deleteProductCall(String url, final Context context,final String productId,final String userId,final int Number)
        {
                pDialog=  Tools.showProgressBar(context);

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                {
                        // if a reponse is recieved after sending request
                        @Override
                        public void onResponse(String response)
                        {
                                try
                                {
                                        Log.i("rajat", " onResponseActive " + response);
                                        pDialog.dismiss();

                                        JSONParser.DeleteProductApiJsonParser(response, userId, context);


                                }
                                catch (Exception localException)
                                {
                                        Log.i("rajat"," onResponseException "+localException.getMessage());
                                        localException.printStackTrace();
                                }
                        }
                }
                        , new Response.ErrorListener()
                {
                        //if error occurs
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                                Log.i("rajat", "onErrorResponse" + error.toString());
                                error.printStackTrace();
                                pDialog.dismiss();
                        }
                }){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                                //Log.i("size in getHeader: ",myHeaders.size()+"");
                                Map<String, String> mHeaders=new HashMap<String,String>();//myHeaders;
                                mHeaders.put("x-access-token", context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("token", ""));//MainActivity.sharedpreferences.getString("Set-Cookie",""));
                                return mHeaders;
                        }
                        @Override
                        public String getBodyContentType() {
                                return "application/x-www-form-urlencoded; charset=UTF-8";
                        }
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();

                                params.put("id",productId);
                                return params;
                        }
                };

                //how many times to try and for how much duration
                setCustomRetryPolicy(request);
                //get instance of volleysingleton and add reuest to the queue
                VolleySingleton.getInstance(context).addToRequestQueue(request);
        }
        public static void findOffersCall(String url, final Context context,final int Number)
        {
                pDialog=  Tools.showProgressBar(context);

                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
                {
                        // if a reponse is recieved after sending request
                        @Override
                        public void onResponse(String response)
                        {
                                try
                                {
                                        Log.i("rajat", " onResponseActive " + response);
                                        pDialog.dismiss();

                                        JSONParser.FindOffersApiJsonParser(response, context);


                                }
                                catch (Exception localException)
                                {
                                        Log.i("rajat"," onResponseException "+localException.getMessage());
                                        localException.printStackTrace();
                                }
                        }
                }
                        , new Response.ErrorListener()
                {
                        //if error occurs
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                                Log.i("rajat", "onErrorResponse" + error.toString());
                                error.printStackTrace();
                                pDialog.dismiss();
                        }
                }){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                                //Log.i("size in getHeader: ",myHeaders.size()+"");
                                Map<String, String> mHeaders=new HashMap<String,String>();//myHeaders;
                                mHeaders.put("x-access-token", context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("token", ""));//MainActivity.sharedpreferences.getString("Set-Cookie",""));
                                return mHeaders;
                        }

                };

                //how many times to try and for how much duration
                setCustomRetryPolicy(request);
                //get instance of volleysingleton and add reuest to the queue
                VolleySingleton.getInstance(context).addToRequestQueue(request);
        }
        public static void findDiscountsCall(String url, final Context context,final String userId,final int Number)
        {
                pDialog=  Tools.showProgressBar(context);

                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
                {
                        // if a reponse is recieved after sending request
                        @Override
                        public void onResponse(String response)
                        {
                                try
                                {
                                        Log.i("rajat_  ", " onResponseActive " + response);
                                        pDialog.dismiss();
                                        JSONParser.FindDiscountsApiJsonParser(response, context);


                                }
                                catch (Exception localException)
                                {
                                        Log.i("rajat"," onResponseException "+localException.getMessage());
                                        localException.printStackTrace();
                                }
                        }
                }
                        , new Response.ErrorListener()
                {
                        //if error occurs
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                                Log.i("rajat", "onErrorResponse" + error.toString());
                                pDialog.dismiss();
                        }
                }){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                                //Log.i("size in getHeader: ",myHeaders.size()+"");
                                Map<String, String> mHeaders=new HashMap<String,String>();//myHeaders;
                                mHeaders.put("x-access-token", context.getApplicationContext().getSharedPreferences("MyPrefs", context.MODE_PRIVATE).getString("token", ""));//MainActivity.sharedpreferences.getString("Set-Cookie",""));
                                return mHeaders;
                        }

                };

                //how many times to try and for how much duration
                setCustomRetryPolicy(request);
                //get instance of volleysingleton and add reuest to the queue
                VolleySingleton.getInstance(context).addToRequestQueue(request);
        }
        public static void registerDeviceCall(String url, final Context context,final String regId,final String email,final String type,final int Number)
        {
                //pDialog=  Tools.showProgressBar(context);

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                {
                        // if a reponse is recieved after sending request
                        @Override
                        public void onResponse(String response)
                        {
                                try
                                {
                                        Log.i("rajat", " onResponseActive " + response);
                                        JSONParser.RegisterApiJsonParser(response, context);

                                        //pDialog.dismiss();
                                }
                                catch (Exception localException)
                                {
                                        Log.i("rajat"," onResponseException "+localException.getMessage());
                                        localException.printStackTrace();
                                }
                        }
                }
                        , new Response.ErrorListener()
                {
                        //if error occurs
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                                Log.i("rajat", "onErrorResponse" + error.toString());
                                error.printStackTrace();
                                //pDialog.dismiss();
                        }
                }){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                                //Log.i("size in getHeader: ",myHeaders.size()+"");
                                Map<String, String> mHeaders=new HashMap<String,String>();//myHeaders;
                                mHeaders.put("x-access-token", context.getApplicationContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("token", ""));//MainActivity.sharedpreferences.getString("Set-Cookie",""));
                                return mHeaders;
                        }
                        @Override
                        public String getBodyContentType() {
                                return "application/x-www-form-urlencoded; charset=UTF-8";
                        }
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();

                                params.put("regId",regId);
                                params.put("type",type);
                                params.put("email",email);
                                return params;
                        }
                };

                //how many times to try and for how much duration
                setCustomRetryPolicy(request);
                //get instance of volleysingleton and add reuest to the queue
                VolleySingleton.getInstance(context).addToRequestQueue(request);
        }
        public static void updateProductPriceCall(String url, final Context context,final String productId,final int price,final int Number)
        {
                pDialog=  Tools.showProgressBar(context);

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                {
                        // if a reponse is recieved after sending request
                        @Override
                        public void onResponse(String response)
                        {
                                try
                                {
                                        pDialog.dismiss();
                                        Log.i("rajat", " onResponseActive " + response);
                                        JSONParser.UpdateProductPriceApiJsonParser(response, context);


                                }
                                catch (Exception localException)
                                {
                                        Log.i("rajat"," onResponseException "+localException.getMessage());
                                        localException.printStackTrace();
                                }
                        }
                }
                        , new Response.ErrorListener()
                {
                        //if error occurs
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                                Log.i("rajat", "onErrorResponse" + error.toString());
                                pDialog.dismiss();
                        }
                }){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                                //Log.i("size in getHeader: ",myHeaders.size()+"");
                                Map<String, String> mHeaders=new HashMap<String,String>();//myHeaders;
                                mHeaders.put("x-access-token", context.getSharedPreferences("MyPrefs", context.MODE_PRIVATE).getString("token", ""));//MainActivity.sharedpreferences.getString("Set-Cookie",""));
                                return mHeaders;
                        }
                        @Override
                        public String getBodyContentType() {
                                return "application/x-www-form-urlencoded; charset=UTF-8";
                        }
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("id",productId);
                                params.put("price",price+"");
                                return params;
                        }
                };

                //how many times to try and for how much duration
                setCustomRetryPolicy(request);
                //get instance of volleysingleton and add reuest to the queue
                VolleySingleton.getInstance(context).addToRequestQueue(request);
        }
        public static void updateProductQuantityCall(String url, final Context context,final String productId,final int quantity,final int Number)
        {
                pDialog=  Tools.showProgressBar(context);

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                {
                        // if a reponse is recieved after sending request
                        @Override
                        public void onResponse(String response)
                        {
                                try
                                {
                                        pDialog.dismiss();
                                        Log.i("rajat", " onResponseActive " + response);
                                        JSONParser.UpdateProductQuantityApiJsonParser(response, context);


                                }
                                catch (Exception localException)
                                {
                                        Log.i("rajat"," onResponseException "+localException.getMessage());
                                        localException.printStackTrace();
                                }
                        }
                }
                        , new Response.ErrorListener()
                {
                        //if error occurs
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                                Log.i("rajat", "onErrorResponse" + error.toString());
                                pDialog.dismiss();
                        }
                }){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                                //Log.i("size in getHeader: ",myHeaders.size()+"");
                                Map<String, String> mHeaders=new HashMap<String,String>();//myHeaders;
                                mHeaders.put("x-access-token", context.getSharedPreferences("MyPrefs", context.MODE_PRIVATE).getString("token", ""));//MainActivity.sharedpreferences.getString("Set-Cookie",""));
                                return mHeaders;
                        }
                        @Override
                        public String getBodyContentType() {
                                return "application/x-www-form-urlencoded; charset=UTF-8";
                        }
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("id",productId);
                                params.put("quantity",quantity+"");
                                return params;
                        }
                };

                //how many times to try and for how much duration
                setCustomRetryPolicy(request);
                //get instance of volleysingleton and add reuest to the queue
                VolleySingleton.getInstance(context).addToRequestQueue(request);
        }
        public static void updateProductNameCall(String url, final Context context,final String productId,final String description,final int Number)
        {
                pDialog=  Tools.showProgressBar(context);

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                {
                        // if a reponse is recieved after sending request
                        @Override
                        public void onResponse(String response)
                        {
                                try
                                {
                                        pDialog.dismiss();
                                        Log.i("rajat", " onResponseActive " + response);
                                        JSONParser.UpdateProductNameApiJsonParser(response, context);


                                }
                                catch (Exception localException)
                                {
                                        Log.i("rajat"," onResponseException "+localException.getMessage());
                                        localException.printStackTrace();
                                }
                        }
                }
                        , new Response.ErrorListener()
                {
                        //if error occurs
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                                Log.i("rajat", "onErrorResponse" + error.toString());
                                pDialog.dismiss();
                        }
                }){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                                //Log.i("size in getHeader: ",myHeaders.size()+"");
                                Map<String, String> mHeaders=new HashMap<String,String>();//myHeaders;
                                mHeaders.put("x-access-token", context.getSharedPreferences("MyPrefs", context.MODE_PRIVATE).getString("token", ""));//MainActivity.sharedpreferences.getString("Set-Cookie",""));
                                return mHeaders;
                        }
                        @Override
                        public String getBodyContentType() {
                                return "application/x-www-form-urlencoded; charset=UTF-8";
                        }
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("id",productId);
                                params.put("description",description);
                                return params;
                        }
                };

                //how many times to try and for how much duration
                setCustomRetryPolicy(request);
                //get instance of volleysingleton and add reuest to the queue
                VolleySingleton.getInstance(context).addToRequestQueue(request);
        }
        public static void changeProductDiscountCall(String url, final Context context,final String productId,final int discount,final int Number)
        {
                pDialog=  Tools.showProgressBar(context);

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                {
                        // if a reponse is recieved after sending request
                        @Override
                        public void onResponse(String response)
                        {
                                try
                                {
                                        pDialog.dismiss();
                                        Log.i("rajat", " onResponseActive " + response);
                                        JSONParser.ChangeProductDiscountApiJsonParser(response, context);


                                }
                                catch (Exception localException)
                                {
                                        Log.i("rajat"," onResponseException "+localException.getMessage());
                                        localException.printStackTrace();
                                }
                        }
                }
                        , new Response.ErrorListener()
                {
                        //if error occurs
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                                Log.i("rajat", "onErrorResponse" + error.toString());
                                pDialog.dismiss();
                        }
                }){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                                //Log.i("size in getHeader: ",myHeaders.size()+"");
                                Map<String, String> mHeaders=new HashMap<String,String>();//myHeaders;
                                mHeaders.put("x-access-token", context.getSharedPreferences("MyPrefs",context.MODE_PRIVATE).getString("token", ""));//MainActivity.sharedpreferences.getString("Set-Cookie",""));
                                return mHeaders;
                        }
                        @Override
                        public String getBodyContentType() {
                                return "application/x-www-form-urlencoded; charset=UTF-8";
                        }
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("id", productId);
                                params.put("discount", discount + "");
                                return params;
                        }
                };

                //how many times to try and for how much duration
                setCustomRetryPolicy(request);
                //get instance of volleysingleton and add reuest to the queue
                VolleySingleton.getInstance(context).addToRequestQueue(request);
        }

        public static void placeOrderCall(String url, final Context context,final String customerId,final String customerEmail,final String shopkeeperId,final ArrayList<String> productIds,final ArrayList<Float> quantities,final int Number)
        {
                pDialog=  Tools.showProgressBar(context);

                JSONObject post= new JSONObject();
                try{
                        JSONObject postItem = new JSONObject();
                        for(int x=0;x<productIds.size();x++){
                                postItem.put(productIds.get(x), quantities.get(x));
                        }
                        post.put("customerId", customerId);
                        post.put("customerEmail",customerEmail);
                        post.put("shopKeeperId", shopkeeperId);
                        post.put("items",postItem);

                }catch (JSONException je){
                        je.printStackTrace();
                }
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, post,
                        new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                        Log.i("rajat", " onResponseActive " + response);

                                        pDialog.dismiss();
                                        JSONParser.PlaceOrderApiJsonParser(response, context);

                                }
                        }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                                pDialog.dismiss();
                        }
                }){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                                //Log.i("size in getHeader: ",myHeaders.size()+"");
                                Map<String, String> mHeaders=new HashMap<String,String>();//myHeaders;
                                mHeaders.put("x-access-token", context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("token", ""));//MainActivity.sharedpreferences.getString("Set-Cookie",""));
                                return mHeaders;
                        }
                };

                //how many times to try and for how much duration
                setCustomRetryPolicy(request);
                //get instance of volleysingleton and add reuest to the queue
                VolleySingleton.getInstance(context).addToRequestQueue(request);
        }

        public static void changeOrderStateCall(String url, final Context context,final String orderId,final String order_state,final int Number)
        {
                pDialog=  Tools.showProgressBar(context);

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                {
                        // if a reponse is recieved after sending request
                        @Override
                        public void onResponse(String response)
                        {
                                try
                                {
                                        Log.i("rajat", " onResponseActive " + response);
                                        pDialog.dismiss();
                                        JSONParser.ChangeOrderStateApiJsonParser(response, context);


                                }
                                catch (Exception localException)
                                {
                                        Log.i("rajat"," onResponseException "+localException.getMessage());
                                        localException.printStackTrace();
                                }
                        }
                }
                        , new Response.ErrorListener()
                {
                        //if error occurs
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                                Log.i("rajat", "onErrorResponse" + error.toString());
                                pDialog.dismiss();
                        }
                }){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                                //Log.i("size in getHeader: ",myHeaders.size()+"");
                                Map<String, String> mHeaders=new HashMap<String,String>();//myHeaders;
                                mHeaders.put("x-access-token", context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("token", ""));//MainActivity.sharedpreferences.getString("Set-Cookie",""));
                                return mHeaders;
                        }
                        @Override
                        public String getBodyContentType() {
                                return "application/x-www-form-urlencoded; charset=UTF-8";
                        }
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();

                                params.put("orderId",orderId);
                                params.put("order_state",order_state);

                                return params;
                        }
                };

                //how many times to try and for how much duration
                setCustomRetryPolicy(request);
                //get instance of volleysingleton and add reuest to the queue
                VolleySingleton.getInstance(context).addToRequestQueue(request);
        }

        public static void findOrdersCall(String url, final Context context,final String userId,final String usertype,final int Number)
        {
                pDialog=  Tools.showProgressBar(context);

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                {
                        // if a reponse is recieved after sending request
                        @Override
                        public void onResponse(String response)
                        {
                                try
                                {
                                        Log.i("rajat", " onResponseActive " + response);
                                        pDialog.dismiss();
                                        JSONParser.FindOrdersApiJsonParser(response, context);


                                }
                                catch (Exception localException)
                                {
                                        Log.i("rajat"," onResponseException "+localException.getMessage());
                                        localException.printStackTrace();
                                }
                        }
                }
                        , new Response.ErrorListener()
                {
                        //if error occurs
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                                Log.i("rajat", "onErrorResponse" + error.toString());

                                pDialog.dismiss();
                        }
                }){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                                //Log.i("size in getHeader: ",myHeaders.size()+"");
                                Map<String, String> mHeaders=new HashMap<String,String>();//myHeaders;
                                mHeaders.put("x-access-token", context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("token", ""));//MainActivity.sharedpreferences.getString("Set-Cookie",""));
                                return mHeaders;
                        }
                        @Override
                        public String getBodyContentType() {
                                return "application/x-www-form-urlencoded; charset=UTF-8";
                        }
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();

                                params.put("userId",userId);
                                params.put("usertype",usertype);

                                return params;
                        }
                };

                //how many times to try and for how much duration
                setCustomRetryPolicy(request);
                //get instance of volleysingleton and add reuest to the queue
                VolleySingleton.getInstance(context).addToRequestQueue(request);
        }
        //
        public static String encrypt(String password)
        {
                String generatedPassword = null;
                try {
                        MessageDigest md = MessageDigest.getInstance("MD5");
                        md.update(password.getBytes());
                        byte[] bytes = md.digest();
                        StringBuilder sb = new StringBuilder();
                        for(int i=0; i< bytes.length ;i++)
                        {
                                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                        }
                        generatedPassword = sb.toString();
                }
                catch (NoSuchAlgorithmException e)
                {
                        e.printStackTrace();
                }
                Log.i("rajat", "Encrypted password= " + generatedPassword);
                return generatedPassword;
        }

        public static void changePreferencesCall(String url, final Context context,final String userId, final ArrayList<String> shops,final int Number)
        {
                pDialog=  Tools.showProgressBar(context);

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                {
                        // if a reponse is recieved after sending request
                        @Override
                        public void onResponse(String response)
                        {
                                try
                                {
                                        Log.i("rajat", " onResponseActive " + response);
//                                        JSONParser.ChangePreferencesApiJsonParser(response, context);

                                        pDialog.dismiss();
                                }
                                catch (Exception localException)
                                {
                                        Log.i("rajat", " onResponseException " + localException.getMessage());
                                        localException.printStackTrace();
                                }
                        }
                }
                        , new Response.ErrorListener()
                {
                        //if error occurs
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                                Log.i("rajat", "onErrorResponse" + error.toString());
                                pDialog.dismiss();
                        }
                }){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                                //Log.i("size in getHeader: ",myHeaders.size()+"");
                                Map<String, String> mHeaders=new HashMap<String,String>();//myHeaders;
                                mHeaders.put("x-access-token", context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("token", ""));//MainActivity.sharedpreferences.getString("Set-Cookie",""));
                                return mHeaders;
                        }
                        @Override
                        public String getBodyContentType() {
                                return "application/x-www-form-urlencoded; charset=UTF-8";
                        }
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("userId",userId);
                                params.put("shops",shops.toString());
                                return params;
                        }
                };

                //how many times to try and for how much duration
                setCustomRetryPolicy(request);
                //get instance of volleysingleton and add reuest to the queue
                VolleySingleton.getInstance(context).addToRequestQueue(request);
        }

        public static void getSubscriptionsCall(String url,final Context context, final String deviceId, int num)
        {
                pDialog=  Tools.showProgressBar(context);

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                {
                        // if a reponse is recieved after sending request
                        @Override
                        public void onResponse(String response)
                        {
                                try
                                {
                                        Log.i("rajat", " onResponseActive " + response);
                                        pDialog.dismiss();

                                        JSONParser.getSubcriptionsApiJsonParser(response, context);

                                }
                                catch (Exception localException)
                                {
                                        Log.i("rajat"," onResponseException "+localException.getMessage());
                                        localException.printStackTrace();
                                        Intent intent=new Intent(context, PermissionForm.class);
                                        context.startActivity(intent);
                                }
                        }
                }
                        , new Response.ErrorListener()
                {
                        //if error occurs
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                                Log.i("rajat", "onErrorResponse" + error.toString());

                                pDialog.dismiss();
                        }
                }){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                                //Log.i("size in getHeader: ",myHeaders.size()+"");
                                Map<String, String> mHeaders=new HashMap<String,String>();//myHeaders;
                                mHeaders.put("x-access-token", context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("token", ""));//MainActivity.sharedpreferences.getString("Set-Cookie",""));
                                return mHeaders;
                        }
                        @Override
                        public String getBodyContentType() {
                                return "application/x-www-form-urlencoded; charset=UTF-8";
                        }
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();

                                params.put("deviceId",deviceId);


                                return params;
                        }
                };

                //how many times to try and for how much duration
                setCustomRetryPolicy(request);
                //get instance of volleysingleton and add reuest to the queue
                VolleySingleton.getInstance(context).addToRequestQueue(request);
        }

        public static void setSubscriptionsCall(String url, final Context context,final String userId,final ArrayList<String> shopIds,final int Number)
        {
                pDialog=  Tools.showProgressBar(context);
                JSONObject send = new JSONObject();
                JSONArray shops = new JSONArray();
                try{
                        for(int i=0; i<shopIds.size();i++){
                                shops.put(shopIds.get(i));
                        }
                        send.put("id", userId);
                        send.put("subscribedIDs", shops);
                }catch (JSONException je ){

                }
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,send, new Response.Listener<JSONObject>()
                {
                        // if a reponse is recieved after sending request
                        @Override
                        public void onResponse(JSONObject response)
                        {
                                try
                                {
                                        Log.i("rajat", " onResponseActive " + response);
//                                        JSONParser.FindPreferencesApiJsonParser(response, context);
                                        JSONParser.setSubcriptionsApiJsonParser(response, context);
                                        pDialog.dismiss();
                                }
                                catch (Exception localException)
                                {
                                        Log.i("rajat"," onResponseException "+localException.getMessage());
                                        localException.printStackTrace();
                                }
                        }
                }
                        , new Response.ErrorListener()
                {
                        //if error occurs
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                                Log.i("rajat", "onErrorResponse" + error.toString());
                                pDialog.dismiss();
                        }
                }){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                                //Log.i("size in getHeader: ",myHeaders.size()+"");
                                Map<String, String> mHeaders=new HashMap<String,String>();//myHeaders;
                                mHeaders.put("x-access-token", context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("token", ""));//MainActivity.sharedpreferences.getString("Set-Cookie",""));
                                return mHeaders;
                        }
                };

                //how many times to try and for how much duration
                setCustomRetryPolicy(request);
                //get instance of volleysingleton and add reuest to the queue
                VolleySingleton.getInstance(context).addToRequestQueue(request);
        }

        public  static void getBitmapFromUrl(String url,final Context con,final ImageView iv){


                ImageRequest request = new ImageRequest(url,
                        new Response.Listener<Bitmap>() {

                                @Override
                                public void onResponse(Bitmap bitmap) {
                                        //mImageView.setImageBitmap(bitmap);
                                        //iv.setImageBitmap(bitmap);
                                        //byte[] img;

                                        if(bitmap!=null){
                                                Bitmap bit2 = Bitmap.createScaledBitmap(bitmap, 400, 400, true);
                                                //ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                                //bit2.compress(Bitmap.CompressFormat.PNG, 100, bos);
                                                //img = bos.toByteArray();
                                                iv.setImageBitmap(bit2);

                                        }else{
                                                Toast.makeText(con, "Image not found", Toast.LENGTH_SHORT).show();
                                        }

                                }
                        }, 0, 0, null,
                        new Response.ErrorListener() {
                                public void onErrorResponse(VolleyError error) {
                                        //Toast.makeText(con,"",Toast.LENGTH_SHORT).show();

                                        //Log.i("rajat", error.getLocalizedMessage());
                                        //mImageView.setImageResource(R.drawable.image_load_error);
                                }
                        }){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> mHeaders=new HashMap<String,String>();//myHeaders;
                                mHeaders.put("x-access-token", con.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("token", ""));
                                return mHeaders;
                        }
                };
                // Access the RequestQueue through your singleton class.
                VolleySingleton.getInstance(con).addToRequestQueue(request);


        }

        public  static void getBitmapFromUrlBack(String url,final Context con,final ImageView iv){


                ImageRequest request = new ImageRequest(url,
                        new Response.Listener<Bitmap>() {

                                @Override
                                public void onResponse(Bitmap bitmap) {
                                        //mImageView.setImageBitmap(bitmap);
                                        //iv.setImageBitmap(bitmap);
                                        //byte[] img;

                                        if(bitmap!=null){
                                                Bitmap bit2 = Bitmap.createScaledBitmap(bitmap, 400, 400, true);
                                                //ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                                //bit2.compress(Bitmap.CompressFormat.PNG, 100, bos);
                                                //img = bos.toByteArray();
                                                Drawable db= (Drawable)new BitmapDrawable(con.getResources(),bit2);
                                                iv.setBackground(db);

                                        }else{
                                                Toast.makeText(con, "Image not found", Toast.LENGTH_SHORT).show();
                                        }

                                }
                        }, 0, 0, null,
                        new Response.ErrorListener() {
                                public void onErrorResponse(VolleyError error) {
                                        //Toast.makeText(con,"",Toast.LENGTH_SHORT).show();

                                        //Log.i("rajat", error.getLocalizedMessage());
                                        //mImageView.setImageResource(R.drawable.image_load_error);
                                }
                        }){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> mHeaders=new HashMap<String,String>();//myHeaders;
                                mHeaders.put("x-access-token", con.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("token", ""));
                                return mHeaders;
                        }
                };
                // Access the RequestQueue through your singleton class.
                VolleySingleton.getInstance(con).addToRequestQueue(request);


        }
}