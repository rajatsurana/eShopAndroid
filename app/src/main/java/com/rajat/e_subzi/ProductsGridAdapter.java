package com.rajat.e_subzi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.rajat.e_subzi.Objects.ProductObject;
import com.google.gson.Gson;
import com.rajat.e_subzi.Volley.CallVolley;
import com.rajat.e_subzi.Volley.VolleyClick;

import java.util.ArrayList;

/**
 * Created by Rishab on 03-03-2016.
 */
public class ProductsGridAdapter extends BaseAdapter {
     Context context;
    ArrayList<ProductObject> productObjList = new ArrayList<ProductObject>();
    ArrayList<String> photoUrls =new ArrayList<String>();
    public ProductsGridAdapter(Context context,int count,ArrayList<ProductObject> productObjList,ArrayList<String>photoUrls){

        super();
    this.photoUrls=photoUrls;
        this.productObjList=productObjList;
        this.context=context;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return productObjList.size();
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
        View row = inflater.inflate(R.layout.products_grid_view, null, true);
        final ImageView imageView=(ImageView)row.findViewById(R.id.product_image);

        CallVolley.getBitmapFromUrl(photoUrls.get(position), context, imageView);
//        imageView.setImageResource(product_image.get(position));
        TextView textView=(TextView)row.findViewById(R.id.product_text);
        textView.setText(productObjList.get(position).getDescription());
        final String text_desc = textView.getText().toString();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPhoto(imageView, text_desc, 400, 400);
            }
        });
        ImageButton button=(ImageButton) row.findViewById(R.id.edit_product);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetails.class);
                intent.putExtra("data", new Gson().toJson(productObjList.get(position)));
                context.startActivity(intent);
            }
        });
        ImageButton button2=(ImageButton) row.findViewById(R.id.delete_product);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(v.getContext(),R.style.DialogTheme);
                //final AlertDialog blackalert;//=alert.create();
                alert.setTitle("Do you really want to delete this product?");
                alert.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                //blackalert.dismiss();
                                alert.create().dismiss();
                                VolleyClick.deleteProductClick(productObjList.get(position).getProductId(),productObjList.get(position).getUserId(),context);
                            }
                        });
                alert.create().show();
                //blackalert.show();
            }
        });
        return row;
    }
    private void loadPhoto(ImageView iv,String name, int width, int height) {

        //ImageView tempImageView = imageView;


        AlertDialog.Builder imageDialog = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.custom_fullimage_dialog,
                (ViewGroup) Products.lay);
        ImageView image = (ImageView) layout.findViewById(R.id.fullimage);
        TextView text = (TextView)layout.findViewById(R.id.custom_fullimage_placename);
        text.setText(name);
        image.setImageDrawable(iv.getDrawable());
        imageDialog.setView(layout);

		/*imageDialog.setPositiveButton("Back", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}

		});
*/

        imageDialog.create();
        imageDialog.show();
    }
}
