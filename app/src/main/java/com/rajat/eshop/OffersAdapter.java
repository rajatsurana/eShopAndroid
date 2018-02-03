package com.rajat.eshop;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rajat on 13-04-2016.
 */
public class OffersAdapter extends BaseAdapter {
    ArrayList<String > offers;
    Context context;


    public OffersAdapter(ArrayList<String> offers,Context context){
        super();
        this.offers=offers;
        this.context=context;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return offers.size();
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
        View row = inflater.inflate(R.layout.offer_list_view, null, true);


//        row.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ArrayList<String> photoUrls = new ArrayList<String>();
//
//                Intent intent=new Intent(context,Discounts.class);
//                intent.putExtra("data", new Gson().toJson(offers.shop_discount_details.get(offers.get(position))));
//
//                context.startActivity(intent);
//            }
//        });
        TextView t=(TextView) row.findViewById(R.id.offr);
        t.setText(offers.get(position));
        t.setAllCaps(true);

        return row;
    }
}

