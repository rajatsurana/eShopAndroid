package com.rajat.e_subzi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.rajat.e_subzi.Objects.OrderObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Rajat on 13-04-2016.
 */
public class ShopsAdapter extends BaseAdapter {
    ArrayList<String > shops;
    Context context;
    HashMap<String ,ArrayList<String>>photoUrl;
    HashMap<String ,ArrayList<Boolean>>deliverable;
    public ShopsAdapter(ArrayList<String> shops,HashMap<String ,ArrayList<String>>photoUrl,HashMap<String ,ArrayList<Boolean>>deliverable,Context context){
        super();
        this.shops=shops;
        this.context=context;
        this.photoUrl=photoUrl;
        this.deliverable=deliverable;

    }

    public int getCount() {
        // TODO Auto-generated method stub
        return shops.size();
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
        View row = inflater.inflate(R.layout.shop_list_view, null, true);


        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> photoUrls = new ArrayList<String>();
                photoUrls=photoUrl.get(shops.get(position));
                ArrayList<Boolean>delivery =new ArrayList<Boolean>();
                delivery=deliverable.get(shops.get(position));
                Intent intent=new Intent(context,Discounts.class);
                intent.putExtra("data", new Gson().toJson(Shops.shop_discount_details.get(shops.get(position))));
                intent.putExtra("delivery", new Gson().toJson(delivery));
                intent.putExtra("photoUrl", new Gson().toJson(photoUrls));
                        context.startActivity(intent);
            }
        });
        TextView t=(TextView) row.findViewById(R.id.shp);
        t.setText(shops.get(position));
        t.setAllCaps(true);

        return row;
    }
}
