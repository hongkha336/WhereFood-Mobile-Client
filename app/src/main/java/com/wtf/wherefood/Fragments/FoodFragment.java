package com.wtf.wherefood.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.wtf.wherefood.Adapter.SliderAdapter;
import com.wtf.wherefood.FoodAcivity;
import com.wtf.wherefood.Model.Food;
import com.wtf.wherefood.R;
import com.wtf.wherefood.Share.AppConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;


public class FoodFragment extends Fragment {

    public List<String> listLinkPicture;
    private  String FoodID;
    private String FoodName;
    private Double AvgSurvey;
    private Integer Prices;
    private String LongDescription;
    private String ShortDescription;
    public  String RestaurantAddress;
    public Context context;


    public FoodFragment(Context context, Food f)
    {
        this.context = context;
        FoodID = f.getFoodID();
        FoodName = f.getFoodName();
        AvgSurvey = f.getAvgSurvey();
        Prices = f.getPrices();
        LongDescription =f.getLongDescriptioon();
        ShortDescription = f.getShortDescription();
        RestaurantAddress =f.getRestaurantAddress();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragmentfood, container, false);
        View convertView = inflater.inflate(R.layout.fragmentfood, null);

        TextView m_RestaurantAddress = convertView.findViewById(R.id.txt_address);
        m_RestaurantAddress.setText(RestaurantAddress);
        TextView txtFoodName = convertView.findViewById(R.id.txt_info_name);
        TextView txtCost = convertView.findViewById(R.id.txt_info_cost);
        TextView txtDesc = convertView.findViewById(R.id.txtinfo_long_des);
        txtFoodName.setText(FoodName);
        txtCost.setText(String.valueOf(Prices) + " VNĐ");
        txtDesc.setText(LongDescription);
        LinearLayout layout = convertView.findViewById(R.id.layout_info_sur);
        Double d_star = AvgSurvey;
        int i_start = d_star.intValue();
        LayoutInflater minflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for(int i = 0; i <i_start; i++) {
            View starView = minflater.inflate(R.layout.star_survey, null);
            layout.addView(starView);
        }
        Double p_star = d_star -i_start;

        if(p_star >= 0.65)
        {
            View starView = minflater.inflate(R.layout.star_survey, null);
            layout.addView(starView);
            i_start ++;
        }
        else {
            if (p_star >= 0.25) {
                View starView = minflater.inflate(R.layout.haft_star_survey, null);
                layout.addView(starView);
                i_start++;
            }
        }

        int grey_star = 5 - i_start;
        for(int i = 0; i <grey_star; i++) {
            View starView = minflater.inflate(R.layout.star_grey_survey, null);
            layout.addView(starView);
        }
        getPictures(convertView);

        return convertView;

    }



    public void getPictures(View convertView){
        listLinkPicture = new ArrayList<>();
        AndroidNetworking.get(AppConfig.GLOBAL_URL +"public/api/permalink/getpermalinkbyid/" +FoodID)
                .addHeaders("content-type","application/json")
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        //String[] items  = new String[response.length()];
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject c = response.getJSONObject(i);
                                String PicturePermalink = c.getString("PicturePermalink");
                                //Add to list Food
                                listLinkPicture.add(PicturePermalink);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        SliderView sliderView = convertView.findViewById(R.id.imageSlider);

                        SliderAdapter adapter = new SliderAdapter( context,listLinkPicture,"");
                        adapter.setCount(listLinkPicture.size());
                        sliderView.setSliderAdapter(adapter);

                        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                        sliderView.setIndicatorSelectedColor(Color.WHITE);
                        sliderView.setIndicatorUnselectedColor(Color.GRAY);



                    }
                    @Override
                    public void onError(ANError error) {
                    }
                });

    }

}
