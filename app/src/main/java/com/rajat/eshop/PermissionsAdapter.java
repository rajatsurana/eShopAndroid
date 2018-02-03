package com.rajat.eshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.rajat.eshop.Pojo.Shop;

import java.util.ArrayList;


public class PermissionsAdapter extends BaseAdapter {

    ArrayList<Shop> shops=new ArrayList<Shop>();
    Context context;
    public PermissionsAdapter(ArrayList<Shop> shops, Context context){
        super();
        this.shops=shops;

        this.context=context;
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
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.permissions_cardview_view, null, true);
        TextView data = (TextView) row.findViewById(R.id.shop);

        data.setText(shops.get(position).getShopName());
        CheckBox checkBox=(CheckBox) row.findViewById(R.id.check_shop);
        if(PermissionForm.app_shops_id.contains(shops.get(position).getShopId())){
            checkBox.setChecked(true);
        }else{
            checkBox.setChecked(false);
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){

                    PermissionForm.app_shops_id.add(shops.get(position).getShopId());
                }
                else {
                    PermissionForm.app_shops_id.remove(PermissionForm.app_shops_id.indexOf(shops.get(position).getShopId()));
                }

            }
        });
        return row;
    }
}
