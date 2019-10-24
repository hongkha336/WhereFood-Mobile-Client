package com.wfteam.wherefood.Activity;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.wfteam.wherefood.Adapter.CustomFoodAdapter;
import com.wfteam.wherefood.Adapter.SliderAdapter;
import com.wfteam.wherefood.Model.Food;
import com.wfteam.wherefood.R;
import com.wfteam.wherefood.RestfulComponent.HttpHandler;
import com.wfteam.wherefood.Share.AppConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FoodInfo extends AppCompatActivity {

    public List<String> listLinkPicture;
    private  String FoodID;
    private String FoodName;
    private Double AvgSurvey;
    private Integer Prices;
    private String LongDescription;
    private String ShortDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_info);
        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            FoodID = extras.getString("FoodID");
            FoodName = extras.getString("FoodName");
            AvgSurvey = extras.getDouble("AvgSurvey");
            Prices= extras.getInt("Prices");
            LongDescription = extras.getString("LongDescription");
            ShortDescription =extras.getString("ShortDescription");
        }


        TextView txtFoodName = findViewById(R.id.txt_info_name);
        TextView txtCost = findViewById(R.id.txt_info_cost);
        TextView txtDesc = findViewById(R.id.txtinfo_long_des);


        txtFoodName.setText(FoodName);
        txtCost.setText(String.valueOf(Prices) + " VNĐ");
        txtDesc.setText(LongDescription);


        LinearLayout layout = findViewById(R.id.layout_info_sur);
        Double d_star = AvgSurvey;
        int i_start = d_star.intValue();



        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        listLinkPicture = new ArrayList<>();
        new GetPicture(FoodID).execute();

        ImageButton btnDir = findViewById(R.id.btnDir);
        btnDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private class GetPicture extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }


        private String API = "public/api/permalink/getpermalinkbyid/";
        private String SEARCH_KEY = "";
        public  GetPicture(String ID)
        {

            SEARCH_KEY = ID;
        }

        String TAG = "JSON_DOWNLOAD";

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            //https://testserver.22domain.com/public/api/food/getfood/ca
            String url = AppConfig.GLOBAL_URL + API + SEARCH_KEY;
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONArray foodsArray = new JSONArray(jsonStr);
                    // looping through All FoodsArray
                    for (int i = 0; i < foodsArray.length(); i++) {
                        JSONObject c = foodsArray.getJSONObject(i);
                        String PicturePermalink = c.getString("PicturePermalink");
                        //Add to list Food
                        listLinkPicture.add(PicturePermalink);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Check your connection");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get data from server. Check your connection!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            SliderView sliderView = findViewById(R.id.imageSlider);

            SliderAdapter adapter = new SliderAdapter( FoodInfo.this,listLinkPicture);
            adapter.setCount(listLinkPicture.size());
            sliderView.setSliderAdapter(adapter);

            sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
            sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
            sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
            sliderView.setIndicatorSelectedColor(Color.WHITE);
            sliderView.setIndicatorUnselectedColor(Color.GRAY);

        }
    }
}
