package com.rajat.eshop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.rajat.eshop.Adapter.NotificationView;
import com.rajat.eshop.Pojo.Product;
import com.rajat.eshop.Volley.VolleyClick;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;


public class Discounts extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    ListView mRecyclerView;
    ArrayList<Product> productObjList;
//    ArrayList<String> photoUrls = new ArrayList<String>();
    ArrayList<Boolean>delivery = new ArrayList<Boolean>();
    DrawerLayout Drawer;                                  // Declaring DrawerLayout

    ActionBarDrawerToggle mDrawerToggle;                  // Declaring Action Bar Drawer Toggle
    private static final String TAG = "Discounts";
    public static Context context;
    public static LinearLayout lay;
    private ProgressBar mRegistrationProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discounts);
        context=Discounts.this;
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#46B419"));
        actionBar.setBackgroundDrawable(colorDrawable);
        lay = (LinearLayout)findViewById(R.id.layout_root);
       // ActionBar actionBar;
        productObjList=(ArrayList<Product>) new Gson().fromJson(getIntent().getStringExtra("data"),
                new TypeToken<ArrayList<Product>>() {
                }.getType());
//        photoUrls = (ArrayList<String>) new Gson().fromJson(getIntent().getStringExtra("photoUrl"),
//                new TypeToken<ArrayList<String>>() {
//                }.getType());
        delivery = (ArrayList<Boolean>) new Gson().fromJson(getIntent().getStringExtra("delivery"),
                new TypeToken<ArrayList<Boolean>>() {
                }.getType());
//        Log.i("rajat",photoUrls.size()+"rajat photo size");
        Log.i("rajat",delivery.size()+"rajat deli size");
       // mRegistrationProgressBar = (ProgressBar) findViewById(R.id.registrationProgressBar);



        //actionBar = getSupportActionBar();
        //ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#46B419"));
        //actionBar.setBackgroundDrawable(colorDrawable);
        ListView listView=(ListView)findViewById(R.id.discount_list);
        String[] items={"Carrots ","Radish","banana","others "};
        String[] discounts={"2% off ","2% off ","20% off ","25% off ","26% off ",};
        if(productObjList.size()>0) {
            DiscountListAdapter adapter = new DiscountListAdapter(productObjList,delivery, this);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //add youR code code for networking here
                }
            });

        }

        mRecyclerView = (ListView) findViewById(R.id.discount_nav); // Assigning the RecyclerView Object to the xml View
        mRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences pref = getSharedPreferences("MyPrefs", MODE_PRIVATE);

                Log.d("sheck",pref.getString("type",""));
                Log.d("check",""+position);
                if(position==1){
                    if (pref.getString("type","").equals("Shopkeeper")) {
                        VolleyClick.findProductsClick(pref.getString("userId", ""), Discounts.this);
                    } else {
                        VolleyClick.findDiscountsClick(pref.getString("userId",""), Discounts.this);
                    }
                }
                else if(position==2){
                    VolleyClick.findOrdersClick(pref.getString("userId",""), pref.getString("type",""), Discounts.this);
                }
                else if(position==3){
                    if (pref.getString("type", "").equals("Shopkeeper")) {
                        Discounts.this.getSharedPreferences("MyPrefs", 0).edit().clear().commit();
                        Intent intent = new Intent(Discounts.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        Discounts.this.startActivity(intent);
                    } else {
                        VolleyClick.getSubscriptionClick(pref.getString("deviceId", ""), Discounts.this);
                    }
                }
                else if(position==4){
                    Intent intent = new Intent(Discounts.this, NotificationView.class);
                    Discounts.this.startActivity(intent);
                }
                else if(position==5){
                    if (pref.getString("type", "").equals("Shopkeeper")) {
                        VolleyClick.logoutClick(pref.getString("deviceId", ""), Discounts.this);
                    }else{
                        VolleyClick.findOffersClick(Discounts.this);
                    }

                }else if(position==6){
                    VolleyClick.logoutClick(pref.getString("deviceId",""),Discounts.this);
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
        getMenuInflater().inflate(R.menu.menu_discounts, menu);
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
    public void placeOrder(View view){
        Intent intent=new Intent(this,AddOrderActivity.class);
        Log.d("ghhjk",new Gson().toJson(productObjList));
        intent.putExtra("data",new Gson().toJson(productObjList));
        intent.putExtra("delivery",new Gson().toJson(delivery));
        startActivity(intent);
    }



    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.products) {

        } else if (id == R.id.order) {


        } else if (id == R.id.preferences) {


        } else if (id == R.id.logout) {


        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
