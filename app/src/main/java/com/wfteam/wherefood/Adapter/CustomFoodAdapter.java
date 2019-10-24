package com.wfteam.wherefood.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.wfteam.wherefood.Model.Food;
import com.wfteam.wherefood.R;
import com.wfteam.wherefood.Share.AppConfig;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class CustomFoodAdapter extends BaseAdapter {
    private List<Food> listData;
    private Context context;



    public CustomFoodAdapter(Context aContext,  List<Food> listData) {
        this.context = aContext;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.food_thumbnail,null);

        TextView txtFoodName = convertView.findViewById(R.id.txtFoodName);
        TextView txtCost = convertView.findViewById(R.id.txtCost);
        TextView txtDiscription = convertView.findViewById(R.id.txtShortDiscription);
        ImageView FirstThumbnail = convertView.findViewById(R.id.firstThumbnail);

        LinearLayout layout = convertView.findViewById(R.id.layout_food_sur);


        txtFoodName.setText(listData.get(position).getFoodName());
        txtCost.setText(String.valueOf(listData.get(position).getPrices()) + " VNĐ");
        txtDiscription.setText(listData.get(position).getShortDescription());


        Double d_star = listData.get(position).getAvgSurvey();
        int i_start = d_star.intValue();




        for(int i = 0; i <i_start; i++) {
            View starView = inflater.inflate(R.layout.star_survey, null);
            layout.addView(starView);
        }



        Double p_star = d_star -i_start;

        if(p_star >= 0.65)
        {
            View starView = inflater.inflate(R.layout.star_survey, null);
            layout.addView(starView);
            i_start ++;
        }
        else {
            if (p_star >= 0.25) {
                View starView = inflater.inflate(R.layout.haft_star_survey, null);
                layout.addView(starView);
                i_start++;
            }
        }

        int grey_star = 5 - i_start;
        for(int i = 0; i <grey_star; i++) {
            View starView = inflater.inflate(R.layout.star_grey_survey, null);
            layout.addView(starView);
        }

       if(listData.get(position).getFirstPermarkLink().length() == 0)
            FirstThumbnail.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.dev_iconfood1));
        else {
           Picasso.get().load(AppConfig.GLOBAL_URL+listData.get(position).getFirstPermarkLink()).into(FirstThumbnail);
       }
       return convertView;

    }








}
