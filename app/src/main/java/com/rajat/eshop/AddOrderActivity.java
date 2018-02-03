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
import android.widget.Toast;

import com.rajat.eshop.Adapter.NotificationView;
import com.rajat.eshop.Pojo.Product;
import com.rajat.eshop.Volley.VolleyClick;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class AddOrderActivity extends ActionBarActivity  implements AdapterView.OnItemSelectedListener{
    ArrayList<Product> productObjList;
    ArrayList<Boolean>delivery ;
    ArrayList<String> productIds;
    ArrayList<Float> quantities;
    ListView mRecyclerView;

    public static HashMap<String, Float> data_quantity=new HashMap<String,Float>();

    DrawerLayout Drawer;                                  // Declaring DrawerLayout

    ActionBarDrawerToggle mDrawerToggle;                  // Declaring Action Bar Drawer Toggle

    public static HashMap<String,Float> data=new HashMap<String ,Float>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        productIds=new ArrayList<String>();
        quantities=new ArrayList<Float>();
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#46B419"));
        actionBar.setBackgroundDrawable(colorDrawable);
        productObjList=(ArrayList<Product>) new Gson().fromJson(getIntent().getStringExtra("data"),
                new TypeToken<ArrayList<Product>>() {
                }.getType());
        delivery=(ArrayList<Boolean>) new Gson().fromJson(getIntent().getStringExtra("delivery"),
                new TypeToken<ArrayList<Boolean>>() {
                }.getType());
        Log.d("size",""+productObjList.size());

        mRecyclerView = (ListView) findViewById(R.id.order_nav); // Assigning the RecyclerView Object to the xml View
        mRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences pref = getSharedPreferences("MyPrefs", MODE_PRIVATE);

                Log.d("sheck", pref.getString("type", ""));
                Log.d("check",""+position);
                if(position==1){
                    if (pref.getString("type","").equals("Shopkeeper")) {
                        VolleyClick.findProductsClick(pref.getString("userId", ""), AddOrderActivity.this);
                    } else {
                        VolleyClick.findDiscountsClick(pref.getString("userId",""), AddOrderActivity.this);
                    }
                }
                else if(position==2){
                    VolleyClick.findOrdersClick(pref.getString("userId",""), pref.getString("type",""), AddOrderActivity.this);
                }
                else if(position==3){
                    if (pref.getString("type", "").equals("Shopkeeper")) {
                        Intent intent = new Intent(AddOrderActivity.this, CreateDiscount.class);
                        AddOrderActivity.this.startActivity(intent);
                    } else {
                        VolleyClick.getSubscriptionClick(pref.getString("deviceId", ""), AddOrderActivity.this);
                    }
                }
                else if(position==4){
                    Intent intent = new Intent(AddOrderActivity.this, NotificationView.class);
                    AddOrderActivity.this.startActivity(intent);
                }
                else if(position==5){
                    if (pref.getString("type", "").equals("Shopkeeper")) {
                        VolleyClick.logoutClick(pref.getString("deviceId", ""), AddOrderActivity.this);
                    }else{
                        VolleyClick.findOffersClick(AddOrderActivity.this);
                    }

                }else if(position==6){
                    VolleyClick.logoutClick(pref.getString("deviceId",""),AddOrderActivity.this);
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

        ListView listView=(ListView) findViewById(R.id.place_order_list);
        AddOrderAdapter adapter=new AddOrderAdapter(productObjList,delivery,this);
        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                productIds.add(products.get(position).getProductId());
//                TextView textView = (TextView) parent.findViewById(R.id.order_quantity);
//                quantities.add(Float.parseFloat(textView.getText().toString()));
//            }
//        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_order, menu);
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
//        for (int i=0;i<productObjList.size();i++){
//            Log.d("ds","dss");
//            ListView listView=(ListView) findViewById(R.id.place_order_list);
//            Log.d("fdsfsdfs"," "+listView.getCount()+productObjList.size());
//            TextView textView =(TextView) listView.get.findViewById(R.id.amt);
//            Float amt=Float.parseFloat(textView.getText().toString());
//            if(amt>0.0){
//                productIds.add(productObjList.get(i).getProductId());
//                quantities.add(amt);
//            }
//        }
        Log.d("size", "" + data.size());
        Iterator it = data.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if(!pair.getValue().equals(0)){
            productIds.add(pair.getKey().toString());
            quantities.add((Float)pair.getValue());
            }
            System.out.println(pair.getKey() + " = " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
        SharedPreferences pref = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        Log.d("rajar",Integer.toString(productIds.size()));
        if(quantities.size()>0) {
            VolleyClick.placeOrderClick(pref.getString("userId", " "), pref.getString("email", " "), productObjList.get(0).getUserId(), productIds, quantities, this);
        }else{
            Toast.makeText(this,"All quantities are 0",Toast.LENGTH_SHORT).show();
        }
    }
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        int a=2;
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
}
