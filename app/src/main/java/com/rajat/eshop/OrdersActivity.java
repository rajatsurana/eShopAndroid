package com.rajat.eshop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.rajat.eshop.Adapter.NotificationView;
import com.rajat.eshop.Pojo.Order;
import com.rajat.eshop.Volley.VolleyClick;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;


public class OrdersActivity extends ActionBarActivity {
    ListView mRecyclerView;
//    String userId="56daf665a32c7f2c2ebda1a8";
    String usertype="Customer";
    ArrayList<Order> orderObjList;
    DrawerLayout Drawer;                                  // Declaring DrawerLayout
//    public static ArrayList<TextView> a;
    ActionBarDrawerToggle mDrawerToggle;                  // Declaring Action Bar Drawer Toggle
   // public static TextView a;
    public ArrayList<String> number;
    public ArrayList<String> address;
    public static ArrayList<TextView> as;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        as=new ArrayList<TextView>();


        ActionBar actionBar;
//        @SuppressWarnings()
        orderObjList=(ArrayList<Order>) new Gson().fromJson(getIntent().getStringExtra("data"),
                new TypeToken<ArrayList<Order>>() {
                }.getType());
        number =(ArrayList<String>) new Gson().fromJson(getIntent().getStringExtra("userPhone"),
                new TypeToken<ArrayList<String>>() {
                }.getType());
        address =(ArrayList<String>) new Gson().fromJson(getIntent().getStringExtra("userAddress"),
                new TypeToken<ArrayList<String>>() {
                }.getType());
        for (int i=0;i<orderObjList.size();i++){
            as.add(new TextView(this));

        }
        Log.d("ffdgdgf", getIntent().getStringExtra("data"));
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#46B419"));
        actionBar.setBackgroundDrawable(colorDrawable);

        ListView listView=(ListView)findViewById(R.id.orders);
        String[] items={"Delievery","Delivery"};
        String[] discounts={"1st Priority","2nd priority"};
        Log.i("rajat","size:-"+orderObjList.size()+" "+number.size()+" "+address.size());
        OrderListAdapter adapter=new OrderListAdapter(orderObjList,number,address,this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //add you code for networking here
            }
        });

        mRecyclerView = (ListView) findViewById(R.id.orders_nav); // Assigning the RecyclerView Object to the xml View
        mRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences pref = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                Log.d("sheck",pref.getString("type",""));
                Log.d("check",""+position);
                if(position==1){
                    if (pref.getString("type","").equals("Shopkeeper")) {
                        VolleyClick.findProductsClick(pref.getString("userId", ""), OrdersActivity.this);
                    } else {
                        VolleyClick.findDiscountsClick(pref.getString("userId",""), OrdersActivity.this);
                    }
                }
                else if(position==2){
                    VolleyClick.findOrdersClick(pref.getString("userId",""), pref.getString("type",""), OrdersActivity.this);
                }
                else if(position==3){
                    if (pref.getString("type", "").equals("Shopkeeper")) {
                        Intent intent = new Intent(OrdersActivity.this, CreateDiscount.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        OrdersActivity.this.startActivity(intent);
                    } else {
                        VolleyClick.getSubscriptionClick(pref.getString("deviceId", ""), OrdersActivity.this);
                    }
                }
                else if(position==4){
                    Intent intent = new Intent(OrdersActivity.this, NotificationView.class);
                    OrdersActivity.this.startActivity(intent);
                }
                else if(position==5){
                    if (pref.getString("type", "").equals("Shopkeeper")) {
                        VolleyClick.logoutClick(pref.getString("deviceId", ""), OrdersActivity.this);
                    }else{
                        VolleyClick.findOffersClick(OrdersActivity.this);
                    }

                }else if(position==6){
                    VolleyClick.logoutClick(pref.getString("deviceId",""),OrdersActivity.this);
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
        getMenuInflater().inflate(R.menu.menu_orders, menu);
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
}
