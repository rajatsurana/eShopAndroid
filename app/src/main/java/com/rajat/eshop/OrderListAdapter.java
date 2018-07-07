package com.rajat.eshop;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.rajat.eshop.Pojo.Order;
import com.rajat.eshop.Volley.VolleyClick;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Rishab on 03-03-2016.
 */
public class OrderListAdapter extends BaseAdapter{
    String[] headings;
    String[] desc;
    Context context;
//    String[] colors={"#A53ABD","#234E33"};
    String[] colors={"#999999","#5bc5a7"};
    String[] dropdown={"OrderReceived", "OrderBeingProcessed", "Delivering", "Delivered"};
    HashMap<String ,Integer> mapping=new HashMap<String ,Integer >();
    ArrayList<Order> orders;
    ArrayList<String> numbers;
    ArrayList<String> address;
    public OrderListAdapter(ArrayList<Order> orders, ArrayList<String>numbers, ArrayList<String>address, Context context){
        super();
        this.orders = orders;
        this.context=context;
        mapping.put("OrderBeingProcessed",R.id.four);
        mapping.put("OrderReceived",R.id.three);
        mapping.put("Delivered",R.id.two);
        mapping.put("Delivering",R.id.one);
        this.address=address;
        this.numbers = numbers;

    }

    public int getCount() {
        // TODO Auto-generated method stub
        return orders.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }
    public View getView(final int position, final View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View row = inflater.inflate(R.layout.order_list_view, null, true);
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,OrderDeatils.class);
                Log.d("sdfg", new Gson().toJson(orders.get(position)));
                Log.i("rajat", "size2: " + orders.get(position).getItemObjArr().size());
                intent.putExtra("data", new Gson().toJson(orders.get(position)));
                context.startActivity(intent);
            }
        });
        row.setBackgroundColor(Color.parseColor(colors[position%2]));
//        TextView data = (TextView) row.findViewById(R.id.head);
//        data.setText(headings[position]);
//        data=(TextView)row.findViewById(R.id.descs);
//        data.setText(desc[position]);
        TextView textView=(TextView) row.findViewById(R.id.order_ste);
        if(context.getSharedPreferences("MyPrefs",Context.MODE_PRIVATE).getString("type","").equals("Shopkeeper")){
            TextView tVie=(TextView) row.findViewById(R.id.head);
            String format ="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
            String format2="yyyy-MM-dd HH:mm";
            SimpleDateFormat sdf2 = new SimpleDateFormat(format2, Locale.getDefault());
            Date date = null;
            String datetime="";
            try {
                String myDate= orders.get(position).getCreated_at();
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
            if(position<numbers.size()){
                tVie.setText(orders.get(position).getCustomerEmail() +"\n"
                        +numbers.get(position)+ "\n"
                        +address.get(position)+"\n"
                        +datetime);
            }else{
                tVie.setText(orders.get(position).getCustomerEmail() +"\n"
                        +datetime);
            }

            tVie.setAllCaps(true);
            textView.setVisibility(View.GONE);
            TextView t=(TextView)row.findViewById(mapping.get(orders.get(position).getCurrentState()));
            t.setTextColor(Color.parseColor("#76ff03"));
            //OrdersActivity.a=t;
            OrdersActivity.as.set(position,t);
            Log.d("rajat",position+":raj");
            t=(TextView)row.findViewById(R.id.one);
            t.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView all =(TextView)row.findViewById(R.id.one);
                    all.setTextColor(Color.WHITE);
                    all =(TextView)row.findViewById(R.id.two);
                    all.setTextColor(Color.WHITE);
                    all =(TextView)row.findViewById(R.id.three);
                    all.setTextColor(Color.WHITE);
                    all =(TextView)row.findViewById(R.id.four);
                    all.setTextColor(Color.WHITE);
                    TextView p=(TextView) v;

                    OrdersActivity.as.get(position).setTextColor(Color.WHITE);
                    p.setTextColor(Color.parseColor("#76ff03"));
                    VolleyClick.changeOrderStateClick(orders.get(position).getOrderId().toString(), p.getText().toString(), context);
                    OrdersActivity.as.set(position,p);
                   // OrdersActivity.a=p;
                }
            });

            t=(TextView)row.findViewById(R.id.two);
            t.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView all =(TextView)row.findViewById(R.id.one);
                    all.setTextColor(Color.WHITE);
                    all =(TextView)row.findViewById(R.id.two);
                    all.setTextColor(Color.WHITE);
                    all =(TextView)row.findViewById(R.id.three);
                    all.setTextColor(Color.WHITE);
                    all =(TextView)row.findViewById(R.id.four);
                    all.setTextColor(Color.WHITE);
                    TextView p=(TextView) v;

                    OrdersActivity.as.get(position).setTextColor(Color.WHITE);
                    p.setTextColor(Color.parseColor("#76ff03"));
                    VolleyClick.changeOrderStateClick(orders.get(position).getOrderId().toString(), p.getText().toString(), context);
                    OrdersActivity.as.set(position,p);
                }
            });
            t=(TextView)row.findViewById(R.id.three);
            t.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView all =(TextView)row.findViewById(R.id.one);
                    all.setTextColor(Color.WHITE);
                    all =(TextView)row.findViewById(R.id.two);
                    all.setTextColor(Color.WHITE);
                    all =(TextView)row.findViewById(R.id.three);
                    all.setTextColor(Color.WHITE);
                    all =(TextView)row.findViewById(R.id.four);
                    all.setTextColor(Color.WHITE);
                    TextView p=(TextView) v;

                    //OrdersActivity.a.setTextColor(Color.WHITE);
                    OrdersActivity.as.get(position).setTextColor(Color.WHITE);
                    p.setTextColor(Color.parseColor("#76ff03"));
                    VolleyClick.changeOrderStateClick(orders.get(position).getOrderId().toString(), p.getText().toString(), context);
                    //OrdersActivity.a=p;
                    OrdersActivity.as.set(position,p);
                }
            });
            t=(TextView)row.findViewById(R.id.four);
            t.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView all =(TextView)row.findViewById(R.id.one);
                    all.setTextColor(Color.WHITE);
                    all =(TextView)row.findViewById(R.id.two);
                    all.setTextColor(Color.WHITE);
                    all =(TextView)row.findViewById(R.id.three);
                    all.setTextColor(Color.WHITE);
                    all =(TextView)row.findViewById(R.id.four);
                    all.setTextColor(Color.WHITE);
                    TextView p=(TextView) v;

                    OrdersActivity.as.get(position).setTextColor(Color.WHITE);
                    p.setTextColor(Color.parseColor("#76ff03"));
                    VolleyClick.changeOrderStateClick(orders.get(position).getOrderId().toString(), p.getText().toString(), context);
                    OrdersActivity.as.set(position,p);
                }
            });
        }
        else {
            TextView tView=(TextView)row.findViewById(R.id.head);
            String format ="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
            String format2="yyyy-MM-dd HH:mm";
            SimpleDateFormat sdf2 = new SimpleDateFormat(format2, Locale.getDefault());
            Date date = null;
            String datetime="";
            try {
                String myDate= orders.get(position).getCreated_at();
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
            if(orders.get(position).getItemObjArr().size()==0){
                tView.setText("Rajat"+"\n"+numbers.get(position)+ "\n"+address.get(position));
                tView.setAllCaps(true);
            }else{
//                tView.setText(orders.get(position).getItemObjArr().get(0).getProduct().getUserEmail()+"\n"+numbers.get(position)+ "\n"+address.get(position)+"\n"+datetime);
                tView.setAllCaps(true);
            }
            LinearLayout layout=(LinearLayout) row.findViewById(R.id.change_order_state1);
            layout.setVisibility(View.GONE);
            layout=(LinearLayout) row.findViewById(R.id.change_order_state2);
            layout.setVisibility(View.GONE);
            textView.setText(orders.get(position).getCurrentState());
        }
        return row;
    }
}
