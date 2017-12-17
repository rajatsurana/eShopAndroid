package com.rajat.e_subzi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import com.google.gson.Gson;
import com.rajat.e_subzi.Objects.OrderObject;
import com.rajat.e_subzi.Volley.VolleyClick;

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
    String[] colors={"#A53ABD","#234E33"};
    String[] dropdown={"OrderReceived", "OrderBeingProcessed", "Delivering", "Delivered"};
    HashMap<String ,Integer> mapping=new HashMap<String ,Integer >();
    ArrayList<OrderObject> orderObjects;
    ArrayList<String> numbers;
    ArrayList<String> address;
    public OrderListAdapter(ArrayList<OrderObject> orderObjects,ArrayList<String>numbers,ArrayList<String>address,Context context){
        super();
        this.orderObjects=orderObjects;
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
        return orderObjects.size();
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
                Log.d("sdfg", new Gson().toJson(orderObjects.get(position)));
                Log.i("rajat", "size2:-" + orderObjects.get(position).getItemObjArr().size());
                intent.putExtra("data", new Gson().toJson(orderObjects.get(position)));
                context.startActivity(intent);
            }
        });
        row.setBackgroundColor(Color.parseColor(colors[position%2]));
//        TextView data = (TextView) row.findViewById(R.id.head);
//        data.setText(headings[position]);
//        data=(TextView)row.findViewById(R.id.descs);
//        data.setText(desc[position]);
        TextView textView=(TextView) row.findViewById(R.id.order_ste);
        if(context.getSharedPreferences("MyPrefs",context.MODE_PRIVATE).getString("type","").equals("Shopkeeper")){
            TextView tVie=(TextView) row.findViewById(R.id.head);
            String format ="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
            String format2="yyyy-MM-dd HH:mm";
            SimpleDateFormat sdf2 = new SimpleDateFormat(format2, Locale.getDefault());
            Date date = null;
            String datetime="";
            try {
                String myDate= orderObjects.get(position).getCreated_at();
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
                tVie.setText(orderObjects.get(position).getCustomerEmail() +"\n"
                        +numbers.get(position)+ "\n"
                        +address.get(position)+"\n"
                        +datetime);
            }else{
                tVie.setText(orderObjects.get(position).getCustomerEmail() +"\n"
                        +datetime);
            }

            tVie.setAllCaps(true);
            textView.setVisibility(View.GONE);
            TextView t=(TextView)row.findViewById(mapping.get(orderObjects.get(position).getCurrentState()));
            t.setTextColor(Color.parseColor("#76ff03"));
            //Orders.a=t;
            Orders.as.set(position,t);
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

                    Orders.as.get(position).setTextColor(Color.WHITE);
                    p.setTextColor(Color.parseColor("#76ff03"));
                    VolleyClick.changeOrderStateClick(orderObjects.get(position).getOrderId().toString(), p.getText().toString(), context);
                    Orders.as.set(position,p);
                   // Orders.a=p;
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

                    Orders.as.get(position).setTextColor(Color.WHITE);
                    p.setTextColor(Color.parseColor("#76ff03"));
                    VolleyClick.changeOrderStateClick(orderObjects.get(position).getOrderId().toString(), p.getText().toString(), context);
                    Orders.as.set(position,p);
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

                    //Orders.a.setTextColor(Color.WHITE);
                    Orders.as.get(position).setTextColor(Color.WHITE);
                    p.setTextColor(Color.parseColor("#76ff03"));
                    VolleyClick.changeOrderStateClick(orderObjects.get(position).getOrderId().toString(), p.getText().toString(), context);
                    //Orders.a=p;
                    Orders.as.set(position,p);
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

                    Orders.as.get(position).setTextColor(Color.WHITE);
                    p.setTextColor(Color.parseColor("#76ff03"));
                    VolleyClick.changeOrderStateClick(orderObjects.get(position).getOrderId().toString(), p.getText().toString(), context);
                    Orders.as.set(position,p);
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
                String myDate= orderObjects.get(position).getCreated_at();
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
            if(orderObjects.get(position).getItemObjArr().size()==0){
                tView.setText("Rajat"+"\n"+numbers.get(position)+ "\n"+address.get(position));
                tView.setAllCaps(true);
            }else{
                tView.setText(orderObjects.get(position).getItemObjArr().get(0).getProductObject().getUserEmail()+"\n"+numbers.get(position)+ "\n"+address.get(position)+"\n"+datetime);
                tView.setAllCaps(true);
            }
            LinearLayout layout=(LinearLayout) row.findViewById(R.id.change_order_state1);
            layout.setVisibility(View.GONE);
            layout=(LinearLayout) row.findViewById(R.id.change_order_state2);
            layout.setVisibility(View.GONE);
            textView.setText(orderObjects.get(position).getCurrentState());
        }
        return row;
    }
}
