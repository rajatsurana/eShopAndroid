package com.rajat.eshop;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Rishab on 02-03-2016.
 */
public class GoodsTile extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parentViewGroup,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.goods_tile, parentViewGroup, false);
        return rootView;
    }
}
