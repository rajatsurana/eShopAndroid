package com.rajat.eshop;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


import com.rajat.eshop.Adapter.NotificationView;
import com.rajat.eshop.Volley.VolleyClick;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class AddProductsActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {
    private String[] dropDown={"Quantity(in kgs):","1","2","3","4","5","6","7","8","9","10","11","12","13","14"};
    Spinner spinner;
    public static SharedPreferences pref;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private ImageView imgPreview ;
    private static final String IMAGE_DIRECTORY_NAME = "ProductsActivity";
    private Uri fileUri;
    ListView mRecyclerView;

    DrawerLayout Drawer;                                  // Declaring DrawerLayout

    ActionBarDrawerToggle mDrawerToggle;                  // Declaring Action Bar Drawer Toggle
    CheckBox chk;
    private static File file;

    private static final int REQUEST_STORAGE = 0;

    private static final int REQUEST_CONTACTS = 1;
    private static String[] PERMISSIONS_File = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
public void reqCamPer(){
    ActivityCompat
            .requestPermissions(AddProductsActivity.this, PERMISSIONS_File,
                    REQUEST_STORAGE);
}

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= 23) {
            // Marshmallow+
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Log.i("rajat","hello hello");
                    // Show an expanation to the user *asynchronously* -rajat- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                } else {
                    reqCamPer();
                }

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);


        ActionBar actionBar;
        pref = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        actionBar = getSupportActionBar();
//        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#46B419"));
        ColorDrawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.splitgreen));
        actionBar.setBackgroundDrawable(colorDrawable);
imgPreview =new ImageView(AddProductsActivity.this);
        spinner=(Spinner)findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, dropDown);
        adapter_state
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter_state);
        spinner.setOnItemSelectedListener(this);
        chk = (CheckBox)findViewById(R.id.deliveryAvailable);
        chk.setChecked(true);
        mRecyclerView = (ListView) findViewById(R.id.add_nav); // Assigning the RecyclerView Object to the xml View
        mRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences pref = getSharedPreferences("MyPrefs", MODE_PRIVATE);

                Log.d("sheck",pref.getString("type",""));
                Log.d("check",""+position);
                if(position==1){
                    if (pref.getString("type","").equals("Shopkeeper")) {
                        VolleyClick.findProductsClick(pref.getString("userId", ""), AddProductsActivity.this);
                    } else {
                        VolleyClick.findDiscountsClick(pref.getString("userId",""), AddProductsActivity.this);
                    }
                }
                else if(position==2){
                    VolleyClick.findOrdersClick(pref.getString("userId",""), pref.getString("type",""), AddProductsActivity.this);
                }
                else if(position==3){
                    if (pref.getString("type", "").equals("Shopkeeper")) {
                        Intent intent = new Intent(AddProductsActivity.this, CreateDiscount.class);
                        AddProductsActivity.this.startActivity(intent);
                    } else {
                        VolleyClick.getSubscriptionClick(pref.getString("deviceId", ""), AddProductsActivity.this);
                    }
                }
                else if(position==4){
                    Intent intent = new Intent(AddProductsActivity.this, NotificationView.class);
                    AddProductsActivity.this.startActivity(intent);
                }
                else if(position==5){
                    if (pref.getString("type", "").equals("Shopkeeper")) {
                        VolleyClick.logoutClick(pref.getString("deviceId", ""), AddProductsActivity.this);
                    }else{
                        VolleyClick.findOffersClick(AddProductsActivity.this);
                    }

                }else if(position==6){
                    VolleyClick.logoutClick(pref.getString("deviceId",""),AddProductsActivity.this);
                }
            }
        });

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
            list.add("Logout");
        }
        else{
            list.add("Preferences");
            list.add("Notifications");
            list.add("OffersActivity");
            list.add("Logout");
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
        getMenuInflater().inflate(R.menu.menu_add_products, menu);
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
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        spinner.setSelection(position);
        String selState = (String) spinner.getSelectedItem();
        Log.d("selected item",selState);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
    public void captureImage(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //fileUri= Uri.parse("http://www.google.com");
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        System.out.println(fileUri);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // successfully captured the image
                // display it in image view

//                fileUri=data.getData();
//Log.d("rajatcam",""+fileUri);
//                previewCapturedImage();
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }
        file=mediaFile;
        if(file!=null){
            Log.i("rajat","not null");
        }
        return mediaFile;
    }


    public void addProduct(View view){

        EditText editText=(EditText)findViewById(R.id.item_name);
        String name=(String)editText.getText().toString();
        editText=(EditText)findViewById(R.id.item_price);
        String price=(String)editText.getText().toString();
        editText=(EditText)findViewById(R.id.item_discount);
        String discount=(String)editText.getText().toString();
        Spinner spinner=(Spinner) findViewById(R.id.spinner1);
        String amount=spinner.getSelectedItem().toString();
        CheckBox chked = (CheckBox)findViewById(R.id.deliveryAvailable);
        Boolean deliveryAvailable =chked.isChecked();
        //String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        if(file!=null){
            Log.i("rajat","not null2");
        }
        if(!name.equals("")&&!price.equals("")&& !amount.equals("Quantity(in kgs):")&& !discount.equals("")){
            VolleyClick.createProductClick(Integer.parseInt(price), Integer.parseInt(amount), name, pref.getString("userId", "0"), pref.getString("email", "0"), Integer.parseInt(discount),deliveryAvailable, this, file);
        }else if(name.equals("")){
            Toast.makeText(this,"Provide the product name",Toast.LENGTH_SHORT).show();
        }else if(price.equals("")) {
            Toast.makeText(this,"Enter the price of product",Toast.LENGTH_SHORT).show();
        }else if(discount.equals("")){
            Toast.makeText(this,"Enter the discount on product",Toast.LENGTH_SHORT).show();
        }else if(amount.equals("Quantity(in kgs):")){
            Toast.makeText(this,"Enter the quantity of product",Toast.LENGTH_SHORT).show();
        }
//        @Multipart
//        @PUT("user/photo")
//        Call<User> updateUser(@Part("photo") RequestBody photo, @Part("description") RequestBody description);
        //add the networking code here
    }

    public void cancel(View view){
        this.finish();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == REQUEST_STORAGE) {
            // BEGIN_INCLUDE(permission_result)
            // Received permission result for camera permission.
            Log.i("rajat", "Received response for Camera permission request.");

            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission has been granted, preview can be displayed
                Log.i("rajat", "READ permission has now been granted. Showing preview.");

            } else {
                Log.i("rajat", "READ permission was NOT granted.");


            }
            // END_INCLUDE(permission_result)

        }  else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
