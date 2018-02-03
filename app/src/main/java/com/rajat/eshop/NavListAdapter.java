package com.rajat.eshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rishab on 04-03-2016.
 */
public class NavListAdapter extends BaseAdapter{
    Context context;
    ArrayList<String> list;
    String userId,type;

    public NavListAdapter(Context context, ArrayList<String> list, String  userId, String type){
        this.context=context;
        this.list=list;
        this.userId=userId;
        this.type=type;
    }
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
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
        View row = inflater.inflate(R.layout.nav_list, null, true);


        TextView data = (TextView) row.findViewById(R.id.nav_list_item);
        data.setText(list.get(position));
        ImageView i=(ImageView) row.findViewById(R.id.txt_lft);
        if(position==0){
            i.setImageResource(R.drawable.ic_add_shopping_cart_black_24dp);
        }
        else if(position==1){
            i.setImageResource(R.drawable.ic_shopping_cart_black_24dp);
        }
        else if(position==2){
                i.setImageResource(R.drawable.ic_subject_black_24dp);
        }
        else  if(position==3){
            i.setImageResource(R.drawable.ic_turned_in_black_24dp);
        }
        else if (position==4){
            if(list.size()==5){
            i.setImageResource(R.drawable.ic_power_settings_new_black_24dp);
            }else{
                i.setImageResource(R.drawable.ic_share_black_24dp);
            }
        }else if (position==5){
            i.setImageResource(R.drawable.ic_power_settings_new_black_24dp);
        }
//        data.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SharedPreferences pref = context.getSharedPreferences("MyPrefs", context.MODE_PRIVATE);
//                Log.d("checkpos", "" + position);
//                Log.d("check",type);
//                Log.d("check",userId);
//
//                switch (position) {
//
//                    case 0:
//                        if (type.equals("Shopkeeper")) {
//                            VolleyClick.findProductsClick(userId, context);
//                        } else {
//                            VolleyClick.findDiscountsClick(userId, context);
//                        }
//                    case 1:
//
//                        VolleyClick.findOrdersClick(userId, type, context);
//                    case 2:
//                        if (pref.getString("type", "").equals("Shopkeeper")) {
//                            context.getSharedPreferences("MyPrefs", 0).edit().clear().commit();
//                            Intent intent = new Intent(context, LoginActivity.class);
//                            context.startActivity(intent);
//                        } else {
//                            VolleyClick.findPreferencesClick(userId, context);
//                        }
//
//                    case 3:
//                        context.getSharedPreferences("MyPrefs", 0).edit().clear().commit();
//                        Intent intent = new Intent(context, LoginActivity.class);
//                        context.startActivity(intent);
//
//
//                }
//
//            }
//        });

        return row;
    }
}
