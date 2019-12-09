package com.wtf.wherefood;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.androidnetworking.AndroidNetworking;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wtf.wherefood.Adapter.CustomFoodAdapter;
import com.wtf.wherefood.Handler.HttpHandler;
import com.wtf.wherefood.Model.Food;
import com.wtf.wherefood.Share.AppConfig;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    List<Food> Result;
    ListView lv;
    String keyword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
       // getActionBar().hide();
        getSupportActionBar().hide();
        AndroidNetworking.initialize(getApplicationContext());


        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M

                    } else {
                        // Oups permission denied
                    }
                });


       // RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M

                    } else {
                        // Oups permission denied
                    }
                });



        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            keyword = extras.getString("keyword");
        }


        final MaterialSearchBar searchBar = findViewById(R.id.searchBar);
        //LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        lv = findViewById(R.id.lv_RS);

        if(keyword.length() > 0) {
            searchBar.setText(keyword);
            searchBar.performClick();
           // searchBar.set
            DoSearch(keyword);
//            searchBar.clearFocus();
            lv.performClick();
        }



        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
//                Result = new ArrayList<>();
//                new GetFood(String.valueOf(text)).execute();
                DoSearch(String.valueOf(text));
                searchBar.clearFocus();
               // Toast.makeText(MainActivity.this,"I'm searching....",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(view.findViewById(R.id.txt_no_contents) ==  null) {
                    Intent i = new Intent(getBaseContext(), FoodAcivity.class);
                    i.putExtra("FoodID", Result.get(position).getFoodID());
                    i.putExtra("FoodName", Result.get(position).getFoodName());
                    i.putExtra("AvgSurvey", Result.get(position).getAvgSurvey());
                    i.putExtra("LongDescription", Result.get(position).getLongDescriptioon());
                    i.putExtra("ShortDescription", Result.get(position).getShortDescription());
                    i.putExtra("Prices", Result.get(position).getPrices());
                    i.putExtra("lat", Result.get(position).getLat());
                    i.putExtra("long", Result.get(position).getLong());
                    i.putExtra("link", Result.get(position).getFirstPermarkLink());
                    i.putExtra("RestaurantAddress", Result.get(position).getRestaurantAddress());
                    startActivity(i);
                }
            }
        });

    }


    private void DoSearch(String text)
    {
        Result = new ArrayList<>();
        new GetFood(String.valueOf(text)).execute();
    }

    private class GetFood extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Toast.makeText(MainActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }


        private String API = "public/api/food/getfoodbyname/";
        private String SEARCH_KEY = "";

        public GetFood(String text) {

            SEARCH_KEY = text;
        }

        String TAG = "JSON_DOWNLOAD";

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String url = AppConfig.GLOBAL_URL + API + SEARCH_KEY;
            String jsonStr = sh.makeServiceCall(url);
            if (jsonStr != null) {
                try {
                    JSONArray foodsArray = new JSONArray(jsonStr);
                    // looping through All FoodsArray
                    for (int i = 0; i < foodsArray.length(); i++) {
                        JSONObject c = foodsArray.getJSONObject(i);
                        String FoodID = c.getString("FoodID");
                        String FoodName = c.getString("FoodName");
                        String PictureToken = c.getString("PictureToken");
                        Integer Prices = c.getInt("Prices");
                        String ShortDescription = c.getString("ShortDescription");
                        String LongDescription = c.getString("LongDescription");
                        Double AvgSurvey = c.getDouble("AvgSurvey");
                        String PicturePermalink = c.getString("PicturePermalink");
                        Double Lati = Double.valueOf(c.getString("Latitude"));
                        Double Longti = Double.valueOf(c.getString("Longitude"));
                        String RestaurantAddress = c.getString("RestaurantAddress");
                        //Create Food Object
                        Food foodObject = new Food(FoodID, FoodName, PictureToken, Prices, ShortDescription,
                                LongDescription, AvgSurvey, "", PicturePermalink);
                        //Add to list Food
                        foodObject.setLat(Lati);
                        foodObject.setLong(Longti);
                        foodObject.setRestaurantAddress(RestaurantAddress);
                        Result.add(foodObject);
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
            CustomFoodAdapter adapter = new CustomFoodAdapter(MainActivity.this, Result);
            lv.setAdapter(adapter);
        }
    }
}
