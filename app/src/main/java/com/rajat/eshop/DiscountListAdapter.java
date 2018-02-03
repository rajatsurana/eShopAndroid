package com.rajat.eshop;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rajat.eshop.Pojo.Product;
import com.rajat.eshop.Volley.CallVolley;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Rishab on 02-03-2016.
 */
public class DiscountListAdapter extends BaseAdapter {

    ArrayList<Product> discounts=new ArrayList<Product>();
//    ArrayList<String>photoUrls=new ArrayList<String>();
    ArrayList<Boolean> delivery =new ArrayList<Boolean>();
    Context context;
    public DiscountListAdapter(ArrayList<Product> discounts, ArrayList<Boolean> delivery, Context context){
        super();
//        this.photoUrls=photoUrls;
        this.discounts=discounts;
        this.delivery=delivery;
        this.context=context;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return discounts.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.discount_list_view, null, true);
        final ImageView iv = (ImageView) row.findViewById(R.id.disc_image);
        CallVolley.getBitmapFromUrlBack(discounts.get(position).getPhotoUrl(), context, iv);
        TextView data = (TextView) row.findViewById(R.id.commodity);
        data.setText(discounts.get(position).getDescription());
        final String commodity =data.getText().toString();
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPhoto(iv,commodity,400,400);
            }
        });
        data=(TextView)row.findViewById(R.id.price);
        data.setText("Price : "+Integer.toString(discounts.get(position).getPrice()));
        data=(TextView)row.findViewById(R.id.discount);
        data.setText("Discount : " + Integer.toString(discounts.get(position).getDiscount()));
        String format ="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        String format2="yyyy-MM-dd HH:mm";
        SimpleDateFormat sdf2 = new SimpleDateFormat(format2, Locale.getDefault());
        Date date = null;
        String datetime="";
        try {
            String myDate= discounts.get(position).getCreated_at();
            //date = new Date(myDate);
            date = sdf.parse(myDate);

            Calendar cal = Calendar.getInstance(); // creates calendar
            cal.setTime(date); // sets calendar time/date=====> you can set your own date here
            cal.add(Calendar.HOUR_OF_DAY, 5); // adds one hour
            cal.add(Calendar.MINUTE, 30); // adds one Minute
            date=cal.getTime();
            datetime = sdf2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            date = new Date();
        }
        data=(TextView)row.findViewById(R.id.created_at);
        data.setText("Created at: "+datetime);
        data=(TextView)row.findViewById(R.id.deliveryAvail);
        if(delivery.get(position)){
            data.setText("Delivery Available");
            data.setTextColor(Color.parseColor("#46b419"));
        }else{
            data.setText("Delivery Not Available");
            data.setTextColor(Color.RED);
        }

        return row;
    }
    private void loadPhoto(ImageView iv,String name, int width, int height) {

        AlertDialog.Builder imageDialog = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.custom_fullimage_dialog,
                (ViewGroup) Discounts.lay);
        ImageView image = (ImageView) layout.findViewById(R.id.fullimage);
        TextView text = (TextView)layout.findViewById(R.id.custom_fullimage_placename);
        text.setText(name);
        image.setImageDrawable(iv.getBackground());
        imageDialog.setView(layout);

        imageDialog.create();
        imageDialog.show();
    }
}
