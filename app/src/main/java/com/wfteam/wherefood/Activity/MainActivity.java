package com.wfteam.wherefood.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.preference.PreferenceActivity;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.mancj.materialsearchbar.MaterialSearchBar;
import com.wfteam.wherefood.Adapter.CustomFoodAdapter;
import com.wfteam.wherefood.Adapter.CustomSuggestionsAdapter;
import com.wfteam.wherefood.Model.Food;
import com.wfteam.wherefood.Model.Product;
import com.wfteam.wherefood.R;
import com.wfteam.wherefood.RestfulComponent.HttpHandler;
import com.wfteam.wherefood.Share.AppConfig;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    List<Food> Result;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final MaterialSearchBar searchBar = findViewById(R.id.searchBar);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        CustomSuggestionsAdapter customSuggestionsAdapter = new CustomSuggestionsAdapter(inflater);

        //Get suggestions
        List<Product> suggestions = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            suggestions.add(new Product("Product " + i, i * 10));
        }

        //Get demo list search result



        lv = findViewById(R.id.lv_Resuilt);
//        customSuggestionsAdapter.setSuggestions(suggestions);
//        searchBar.setCustomSuggestionAdapter(customSuggestionsAdapter);


        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                Result = new ArrayList<>();
                new GetFood(String.valueOf(text)).execute();
                searchBar.clearFocus();

            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });




        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getBaseContext(), FoodInfo.class);
                i.putExtra("FoodID", Result.get(position).getFoodID());
                i.putExtra("FoodName", Result.get(position).getFoodName());
                i.putExtra("AvgSurvey", Result.get(position).getAvgSurvey());
                i.putExtra("LongDescription", Result.get(position).getLongDescriptioon());
                i.putExtra("ShortDescription", Result.get(position).getShortDescription());
                i.putExtra("Prices", Result.get(position).getPrices());
                startActivity(i);
            }
        });

    }


    private class GetFood extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Toast.makeText(MainActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }


        private String API = "public/api/food/getfoodandpicture/";
        private String SEARCH_KEY = "";
        public  GetFood(String text)
        {

            SEARCH_KEY = text;
        }

        String TAG = "JSON_DOWNLOAD";

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            //https://testserver.22domain.com/public/api/food/getfood/ca
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
                        //Create Food Object
                        Food foodObject = new Food(FoodID,FoodName,PictureToken,Prices,ShortDescription,LongDescription,AvgSurvey,"",PicturePermalink);
                        //Add to list Food
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
            CustomFoodAdapter adapter = new CustomFoodAdapter(MainActivity.this,Result);
            lv.setAdapter(adapter);
        }
    }
}
