package com.rajat.eshop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rajat.eshop.Adapter.NotificationView;
import com.rajat.eshop.Pojo.Product;
import com.rajat.eshop.Volley.VolleyClick;
import com.rajat.eshop.gcm.QuickstartPreferences;
//import com.rajat.eshop.gcm.RegistrationIntentService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


public class ShopsActivity extends ActionBarActivity {
    ListView mRecyclerView;
    DrawerLayout Drawer;                                  // Declaring DrawerLayout

    ActionBarDrawerToggle mDrawerToggle;
    public static HashMap<String , ArrayList<Product>> shop_discount_details=new HashMap<String,ArrayList<Product>>();
//    public static HashMap<String , ArrayList<String>> photoUrl=new HashMap<String,ArrayList<String>>();
    public static HashMap<String , ArrayList<Boolean>> deliverable=new HashMap<String,ArrayList<Boolean>>();
    ArrayList<String> shops=new ArrayList<String >();
    //ArrayList<String>photoUrls = new ArrayList<String>();
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static Context context;

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
//        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#46B419"));
        ColorDrawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.splitgreen));
        actionBar.setBackgroundDrawable(colorDrawable);
        context=ShopsActivity.this;
        deliverable =(HashMap<String,ArrayList<Boolean>>) new Gson().fromJson(getIntent().getStringExtra("deliverable"),new TypeToken<HashMap<String ,ArrayList<Boolean>>>() {
        }.getType());
//        photoUrl = (HashMap<String,ArrayList<String>>) new Gson().fromJson(getIntent().getStringExtra("photoUrl"),new TypeToken<HashMap<String ,ArrayList<String>>>() {
//        }.getType());
        shop_discount_details=(HashMap<String,ArrayList<Product>>) new Gson().fromJson(getIntent().getStringExtra("data"),new TypeToken<HashMap<String ,ArrayList<Product>>>() {
        }.getType());
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //mRegistrationProgressBar.setVisibility(ProgressBar.GONE);
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                    //mInformationTextView.setText(getString(R.string.gcm_send_message));
                    Log.i("rajat",getString(R.string.gcm_send_message));
                } else {
                    Log.i("rajat",getString(R.string.token_error_message));
                    //mInformationTextView.setText(getString(R.string.token_error_message));
                }
            }
        };
        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
           // Intent intent = new Intent(this, RegistrationIntentService.class);
           // startService(intent);
        }
        Set<String> keys=shop_discount_details.keySet();
        for(String key: keys){
            shops.add(key);
        }

        ShopsAdapter adapter=new ShopsAdapter(shops,deliverable,this);
        ListView listView=(ListView)findViewById(R.id.shopkeeper_lists);
        listView.setAdapter(adapter);


        mRecyclerView = (ListView) findViewById(R.id.shops_nav); // Assigning the RecyclerView Object to the xml View
        mRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences pref = getSharedPreferences("MyPrefs", MODE_PRIVATE);

                Log.d("sheck", pref.getString("type", ""));
                Log.d("check",""+position);
                if(position==1){
                    if (pref.getString("type","").equals("Shopkeeper")) {
                        VolleyClick.findProductsClick(pref.getString("userId", ""), ShopsActivity.this);
                    } else {
                        VolleyClick.findDiscountsClick(pref.getString("userId",""), ShopsActivity.this);
                    }
                }
                else if(position==2){
                    VolleyClick.findOrdersClick(pref.getString("userId",""), pref.getString("type",""), ShopsActivity.this);
                }
                else if(position==3){
                    if (pref.getString("type", "").equals("Shopkeeper")) {
                        ShopsActivity.this.getSharedPreferences("MyPrefs", 0).edit().clear().commit();
                        Intent intent = new Intent(ShopsActivity.this, CreateDiscount.class);
                        ShopsActivity.this.startActivity(intent);
                    } else {
                        VolleyClick.getSubscriptionClick(pref.getString("deviceId", ""), ShopsActivity.this);
                    }
                }
                else if(position==4){
                    Intent intent = new Intent(ShopsActivity.this, NotificationView.class);
                    ShopsActivity.this.startActivity(intent);
                }
                else if(position==5){
                    if (pref.getString("type", "").equals("Shopkeeper")) {
                        VolleyClick.logoutClick(pref.getString("deviceId", ""), ShopsActivity.this);
                    }else{
                        VolleyClick.findOffersClick(ShopsActivity.this);
                    }

                }else if(position==6){
                    VolleyClick.logoutClick(pref.getString("deviceId",""),ShopsActivity.this);
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
        getMenuInflater().inflate(R.menu.menu_shops, menu);
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
    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i("ShopsActivity", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
    }
}
