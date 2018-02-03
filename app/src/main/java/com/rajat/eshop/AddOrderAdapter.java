package com.rajat.eshop;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rajat.eshop.Pojo.Product;

import java.util.ArrayList;

/**
 * Created by Rishab on 02-04-2016.
 */
public class AddOrderAdapter extends BaseAdapter {
    ArrayList<Product> discounts=new ArrayList<Product>();
    Context context;
    ArrayList<Boolean> delivery =new ArrayList<Boolean>();
    public AddOrderAdapter(ArrayList<Product> discounts, ArrayList<Boolean> delivery, Context context){
        super();
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
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View row = inflater.inflate(R.layout.place_order_listview, null, true);
        TextView data = (TextView) row.findViewById(R.id.commodity);
        data.setText(discounts.get(position).getDescription());
        data=(TextView)row.findViewById(R.id.price);
        AddOrderActivity.data_quantity.put(discounts.get(position).getProductId(), (float) discounts.get(position).getQuantity());
        data.setText("Price : "+Integer.toString(discounts.get(position).getPrice()));
        data=(TextView)row.findViewById(R.id.discount);
        data.setText("Discount : " + Integer.toString(discounts.get(position).getDiscount()));
        data=(TextView)row.findViewById(R.id.quantity);
        data.setText("Quantity : "+Integer.toString(discounts.get(position).getQuantity()));
        data=(TextView)row.findViewById(R.id.deliverable);
        if(delivery.get(position)){
            data.setText("Delivery Available");
            data.setTextColor(Color.parseColor("#46b419"));
        }else{
            data.setText("Delivery Not Available");
            data.setTextColor(Color.RED);
        }

        Button button=(Button) row.findViewById(R.id.dec);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView t=(TextView) row.findViewById(R.id.amt);
                if(Float.parseFloat((String) t.getText())>(float)0.0){
                    t.setText(Float.toString(Float.parseFloat((String) t.getText())-(float)1));
                    AddOrderActivity.data.put(discounts.get(position).getProductId(),Float.parseFloat((String) t.getText()));


                }

            }
        });
        button=(Button) row.findViewById(R.id.inc);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView t=(TextView) row.findViewById(R.id.amt);
                if (AddOrderActivity.data_quantity.get(discounts.get(position).getProductId()) >= Float.parseFloat((String) t.getText())+(float)1){
                    t.setText(Float.toString(Float.parseFloat((String) t.getText())+(float)1));
                    AddOrderActivity.data.put(discounts.get(position).getProductId(), Float.parseFloat((String) t.getText()));
                }
                else{
                    Toast.makeText(context,"Quantity limit exceeded",Toast.LENGTH_LONG).show();
                }
            }
        });
        return row;
    }
}
