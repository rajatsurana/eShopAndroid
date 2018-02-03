package com.rajat.eshop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.rajat.eshop.Adapter.NotificationView;
import com.rajat.eshop.Pojo.Product;
import com.rajat.eshop.Volley.VolleyClick;
import com.google.gson.Gson;

import java.util.ArrayList;


public class ProductDetails extends ActionBarActivity {
    Product product;
    ListView mRecyclerView;
    DrawerLayout Drawer;                                  // Declaring DrawerLayout

    ActionBarDrawerToggle mDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Intent intent=getIntent();
        product=(Product) new Gson().fromJson((String) intent.getStringExtra("data"), Product.class);
        EditText edittext=(EditText)findViewById(R.id.p_desc);
        edittext.setText(product.getDescription());
        edittext=(EditText)findViewById(R.id.p_discount);
        edittext.setText(Integer.toString(product.getDiscount()));
        Button update_disc =(Button) findViewById(R.id.update_disc);
        update_disc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = (EditText) findViewById(R.id.p_discount);
                String discount;
                editText = (EditText) findViewById(R.id.p_discount);
                discount = editText.getText().toString();

                EditText price = (EditText) findViewById(R.id.p_price);
                String prices = price.getText().toString();
                if (!prices.equals("") && !discount.equals("")) {
                    int p_discount = Integer.parseInt(discount);
                    int p_price = Integer.parseInt(price.getText().toString());
                    if (p_discount < p_price) {
                        VolleyClick.changeDiscountClick(product.getProductId(), Integer.parseInt(discount), ProductDetails.this);
                    } else {
                        Toast.makeText(ProductDetails.this, "Discount should be less than price", Toast.LENGTH_SHORT).show();
                    }

                } else if (prices.equals("") || discount.equals("")) {
                    Toast.makeText(ProductDetails.this, "Provide values for price and discount", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button update_price =(Button) findViewById(R.id.update_price);
        update_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = (EditText) findViewById(R.id.p_discount);
                String discount;
                editText = (EditText) findViewById(R.id.p_discount);
                discount = editText.getText().toString();

                EditText price =(EditText)findViewById(R.id.p_price);
                String prices=price.getText().toString();

                if(!prices.equals("")  && !discount.equals("")){
                    int p_discount = Integer.parseInt(discount);
                    int p_price =Integer.parseInt(price.getText().toString());
                    if( p_discount < p_price){
                        VolleyClick.updatePriceClick(product.getProductId(), p_price, ProductDetails.this);
                    }else{
                        Toast.makeText(ProductDetails.this,"Discount should be less than price",Toast.LENGTH_SHORT).show();
                    }

                }else if(prices.equals("") || discount.equals("")){
                    Toast.makeText(ProductDetails.this,"Provide values for price and discount",Toast.LENGTH_SHORT).show();
                }

            }
        });
        Button update_quantity =(Button) findViewById(R.id.update_quantity);
        update_quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText ;//= (EditText) findViewById(R.id.p_discount);
                String quantity;
                editText = (EditText) findViewById(R.id.p_quantity);
                quantity = editText.getText().toString();



                if( !quantity.equals("")){
                    int p_quantity = Integer.parseInt(quantity);

                    if( p_quantity>0){
                        VolleyClick.updateQuantityClick(product.getProductId(), p_quantity,ProductDetails.this);
                    }else{
                        Toast.makeText(ProductDetails.this,"Quantity should be greater than zero",Toast.LENGTH_SHORT).show();
                    }

                }else if(quantity.equals("") ){
                    Toast.makeText(ProductDetails.this,"Provide value for quantity",Toast.LENGTH_SHORT).show();
                }

            }
        });
        Button update_name =(Button) findViewById(R.id.update_name);
        update_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText ;//= (EditText) findViewById(R.id.p_discount);
                String name;
                editText = (EditText) findViewById(R.id.p_desc);
                name = editText.getText().toString();
                if( !name.equals("")){
                        VolleyClick.updateNameClick(product.getProductId(), name,ProductDetails.this);
                }else if(name.equals("") ){
                    Toast.makeText(ProductDetails.this,"Name cannot be empty",Toast.LENGTH_SHORT).show();
                }
            }
        });
        edittext=(EditText)findViewById(R.id.p_price);
        edittext.setText(Integer.toString(product.getPrice()));
        edittext=(EditText)findViewById(R.id.p_quantity);
        edittext.setText(Integer.toString(product.getQuantity()));
        mRecyclerView = (ListView) findViewById(R.id.product_details_nav); // Assigning the RecyclerView Object to the xml View
        mRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences pref = getSharedPreferences("MyPrefs", MODE_PRIVATE);

                Log.d("sheck", pref.getString("type", ""));
                Log.d("check",""+position);
                if(position==1){
                    if (pref.getString("type","").equals("Shopkeeper")) {
                        VolleyClick.findProductsClick(pref.getString("userId", ""), ProductDetails.this);
                    } else {
                        VolleyClick.findDiscountsClick(pref.getString("userId",""), ProductDetails.this);
                    }
                }
                else if(position==2){
                    VolleyClick.findOrdersClick(pref.getString("userId",""), pref.getString("type",""), ProductDetails.this);
                }
                else if(position==3){
                    if (pref.getString("type", "").equals("Shopkeeper")) {
                        Intent intent = new Intent(ProductDetails.this, CreateDiscount.class);
                        ProductDetails.this.startActivity(intent);
                    } else {
                        VolleyClick.getSubscriptionClick(pref.getString("deviceId", ""), ProductDetails.this);
                    }
                }
                else if(position==4){
                    Intent intent = new Intent(ProductDetails.this, NotificationView.class);
                    ProductDetails.this.startActivity(intent);
                }
                else if(position==5){

                    if (pref.getString("type", "").equals("Shopkeeper")) {
                        VolleyClick.logoutClick(pref.getString("deviceId", ""), ProductDetails.this);
                    }else{
                        VolleyClick.findOffersClick(ProductDetails.this);
                    }

                }else if(position==6){
                    VolleyClick.logoutClick(pref.getString("deviceId",""),ProductDetails.this);
                }
            }
        });
        SharedPreferences pref = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        ArrayList<String> list=new ArrayList<String >();
        if(pref.getString("type","").equals("Shopkeeper")){
            list.add("ProductsActivity");
        }else{
            list.add("ShopsActivity");
        }
        list.add("OrdersActivity");
        if(pref.getString("type","").equals("Shopkeeper")){
            list.add("Create Discount");
            list.add("Notifications");
            list.add("Log Out");
        }
        else{
            list.add("Preferences");
            list.add("Notifications");
            list.add("OffersActivity");
            list.add("Log Out");
        }

        NavListAdapter mAdapter = new NavListAdapter(this,list,pref.getString("userId",""),pref.getString("type",""));       // Creating the Adapter of MyAdapter class(which we are going to see in a bit)
        // And passing the titles,icons,header view name, header view email,
        // and header view profile picture
        View a= getLayoutInflater().inflate(R.layout.header,null,false);

        mRecyclerView.addHeaderView(a);
        mRecyclerView.setAdapter(mAdapter);                              // Setting the adapter to RecyclerView

//        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager
//
//        mRecyclerView.setLayoutManager(mLayoutManager);                 // Setting the layout Manager


        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);        // Drawer object Assigned to the view
        mDrawerToggle = new ActionBarDrawerToggle(this,Drawer,R.string.openDrawer,R.string.closeDrawer){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
                // Code here will execute once drawer is closed
            }



        }; // Drawer Toggle Object Made
        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
//        Drawer.setOnItemClickListener()
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void done(View view){
        this.finish();
        VolleyClick.findProductsClick(getSharedPreferences("MyPrefs",MODE_PRIVATE).getString("userId",""), this);
    }
}
