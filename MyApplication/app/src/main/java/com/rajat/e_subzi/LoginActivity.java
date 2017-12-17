package com.rajat.e_subzi;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rajat.e_subzi.Objects.ProductObject;
import com.rajat.e_subzi.Volley.VolleyClick;

public class LoginActivity extends AppCompatActivity {
    public static Context context;
    Button  signup,login,create_product,find_product,update_price,change_discount,place_order,change_order_state,find_orders;

    EditText email,type,userId,productId;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static SharedPreferences sharedpreferences;
    public static SharedPreferences.Editor editor ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context=LoginActivity.this;
        email=(EditText)findViewById(R.id.email);
        type=(EditText)findViewById(R.id.type);
        userId=(EditText)findViewById(R.id.userId);
        productId=(EditText)findViewById(R.id.productId);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        signup = (Button) findViewById(R.id.signup);
//        signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                VolleyClick.signUp(email.getText().toString(), "Banana@23", type.getText().toString(),Lothis);
//            }
//        });
        login = (Button) findViewById(R.id.login);
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                VolleyClick.loginClick(email.getText().toString(), "Banana@23");
//            }
//        });
//        create_product = (Button) findViewById(R.id.createProduct);
//        create_product.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int price=200 ,quantity=10,  discount=10;
//                String description="Nimisha";
//                VolleyClick.createProductClick(price, quantity, description, userId.getText().toString(), discount);
//            }
//        });
        find_product = (Button) findViewById(R.id.findProducts);
        find_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VolleyClick.findProductsClick(userId.getText().toString(),LoginActivity.this);
            }
        });
//        update_price = (Button) findViewById(R.id.updatePrice);
//        update_price.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int price=2;
//                VolleyClick.updatePriceClick(productId.getText().toString(), price);
//            }
//        });
        change_discount = (Button) findViewById(R.id.changeDiscount);
        change_discount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int discount=5;
//                VolleyClick.changeDiscountClick(productId.getText().toString(), discount);
            }
        });
        //
        place_order = (Button) findViewById(R.id.placeOrder);
        place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] productIds={productId.getText().toString(),productId.getText().toString()};
                int[] quantities={1,3};
//                VolleyClick.placeOrderClick(userId.getText().toString(), productIds, quantities);
            }
        });
//        change_order_state = (Button) findViewById(R.id.changeOrderState);
//        change_order_state.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String orderId="56dc8946981a3544432d8d26";
//                String orderState="Delivering";
//                VolleyClick.changeOrderStateClick(orderId, orderState);
//            }
//        });
//        find_orders = (Button) findViewById(R.id.findOrders);
//        find_orders.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String userId="56daf665a32c7f2c2ebda1a8";
//                String usertype="Customer";
//                VolleyClick.findOrdersClick(userId, usertype);
//            }
//        });
    }
}