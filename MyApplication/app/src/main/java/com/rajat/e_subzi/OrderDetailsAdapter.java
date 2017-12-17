package com.rajat.e_subzi;

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
import com.rajat.e_subzi.Objects.OrderObject;
import com.rajat.e_subzi.Volley.VolleyClick;

import java.util.ArrayList;

/**
 * Created by Rajat on 13-04-2016.
 */
public class OrderDetailsAdapter extends BaseAdapter{
    OrderObject order;
    Context context;
    public OrderDetailsAdapter(OrderObject orderObject,Context context){
        super();
        this.order=orderObject;
        this.context=context;

    }

    public int getCount() {
        // TODO Auto-generated method stub
        return order.getItemObjArr().size();
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
        View row = inflater.inflate(R.layout.order_details_listview, null, true);
        TextView t=(TextView) row.findViewById(R.id.item_desc);
        t.setText("Commodity : "+order.getItemObjArr().get(position).getProductObject().getDescription());
        t=(TextView) row.findViewById(R.id.item_price);
        t.setText("Price : "+ order.getItemObjArr().get(position).getProductObject().getPrice());
        t=(TextView) row.findViewById(R.id.item_discount);
        t.setText("Discount : "+ order.getItemObjArr().get(position).getProductObject().getDiscount());
        t=(TextView) row.findViewById(R.id.item_quantity_ordered);
        t.setText("Ordered quantity : "+order.getItemObjArr().get(position).getQuantity());

        return row;
    }
}
